package pl.eit.mo.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.eit.mo.core.interfaces.IAspirationCritery;
import pl.eit.mo.core.interfaces.IGoalFunction;
import pl.eit.mo.core.interfaces.IMovement;
import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.core.others.Taboo;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.OutputData;

/**
 * Glowna klasa dostarczajaca interfejs do biblioteki rozdzielajacej pracownikow
 * do projektow
 * 
 * 
 * 1. dodac kryteria aspiracji
 * 2. jak zadziala walidator? na wybranym wierszu czy calosci? a moze do wybranego ?
 * 3. najpierw udostepniam rozw wygenerowane jako wejsciowe zeby mozna bylo go wiecej razy uzyc
 * 	  czyli program zapewnia tylko jedna petle dla jednego wejsciowego
 * 4. na koncu przesylam wyniki -> wszystkie rozw (tylko dochod) + najlepsze rozwiazanie
 * 5. zabronienia trzymam w mapie - key = kod ruchu, value - string ktory potrafi sparsowac 
 *    ruch ktorego ono dotyczny
 * ok 6. oczywiscie tworze obiekt tej klasy i na nim to wolam
 * ok 7. lepiej zostaw Radkowi konfigurowanie pobierania ruchow i algorytmow naprawy z fabryk
 *    udostepnij mu tylko fabryki
 * ok 8. wiec przerob configuration data -> wywal fabryki na zewnatrz
 * 9. Kryt aspiracji wtedy gdy nie moze znalezc ruchu -> bierze ruch z tabluicy zabronien 
 *    i go wykonuje 
 * ok 10. dodaj mozliwosc zwrocenia macierzy wyjsciowej tak aby mozna bylo ja ustawic jako wejsciowa
 * 	   dla kolejnych iteracji
 * 	11. przydaloby sie dorobic loggera
 */


public class HRAllocator {
	
	/** lista z algorytmami naprawy */
	private List<IRepairAlgorithm> repairAlgorithms;
	
	/** funkcja celu */
	private IGoalFunction goalFunction;
	
	/** lista ruchow */
	private List<IMovement> movements;
	
	/** 
	 * walidator macierzy. sprawdza czy rozwiazanie 
	 * jest dopuszczalne 
	 * */
	private List<IValidator> validators;
	
	/** kryterium aspiracji */
	private IAspirationCritery aspirationCritery;
	
	/** mapa z zabronieniami (typ, zabronienie) */
	private List<Taboo> taboos;
	
	/** macierz na ktorej optymalizuje */
	private HRMatrix hrMatrix;
	
	
	/** dane wejsiowe */
	private InputData inputData;
	
	/** dane wyjsciowe */
	private OutputData outputData;

	/** liczba powtorzen algorytmu */
	private int numberOfIterations;

	
	public HRAllocator(HRMatrix hrMatrix, InputData inputData) {
		super();
		this.taboos = new ArrayList<Taboo>();
		this.hrMatrix = hrMatrix.getCopy();
		this.inputData = inputData;
	}
	
	/** glowna metoda klasy. realizuje algorytm Taboo Search */
	public void excecute(){
		outputData = new OutputData();
		HRMatrix startMatrix = hrMatrix;
		for(int iter=0; iter < numberOfIterations; iter++){
			hrMatrix = startMatrix.getCopy();
			// wykonuje kazdy ruch na liscie
			for(IMovement movement : movements){
				
				for(int day=0; day < inputData.getPeriodInDays(); day++){
				
					// kazdy z ruchow wykonuje okreslona ilosc razy
					for(int j=0; j< movement.getNumberOfActionsInDay(); j++){
						// ostania poprawna macierz
						HRMatrix lastValidMatrix = hrMatrix.getCopy();
						
						// dekrementuje zabronienia i wywalam przestarzale
						decrementAndRemoveEndedTaboos();
						
						// kazdy ruch probuje wykonac pewna ilosc razy 
						int probe = 0;
						boolean isSuccessful = false;
						Taboo newTaboo = null;
						while(!isSuccessful && movement.getMaxNumberOfMovementProbes() > probe){
							isSuccessful = movement.tryExcecute(hrMatrix, day, 
									hrMatrix.getRows(), taboos);
							if(!isSuccessful){
								outputData.incrementWrongMovementExcecutes();
							}else{
								outputData.incrementSuccessMovementExcecutes();
							}
							probe++;
						}
						if(isSuccessful){
							newTaboo = new Taboo(movement.getMovementTabooValue());
							// musze naprawic macierz w tym celu probuje to zrobic kazdym
							// dostepnym algorytmem naprawy - wybieram najlepszy rezultat
							double goalValue = 0;
							boolean repairSuccessful = false;
							
							HRMatrix modifiedHrMatrix = hrMatrix.getCopy();
							HRMatrix bestRepairedHrMatrix = null;
							for(IRepairAlgorithm repairAlgorithm : repairAlgorithms){
								// naprawiam macierz
								for(int repProbe = 0; repProbe < repairAlgorithm.getNumberOfRepairsProbes(); repProbe++){
									repairSuccessful = HRUtil.repairMatrix(day, inputData.getPeriodInDays(), 
											hrMatrix, repairAlgorithm, validators);
									if(repairSuccessful){
										double tmpValue = goalFunction.getValue(hrMatrix);
										
										// dodaje informacje o przebiegu algorytmu
										outputData.incrementSuccessRapairs();
										outputData.getGoalFunctionValues().add(tmpValue);
										
										if(tmpValue > goalValue){
											// zapamietuje najlepsza naprawe
											goalValue = tmpValue;
											bestRepairedHrMatrix = hrMatrix.getCopy();
										}
									}else{
										// naprawa sie nie udala wiec wracam do zmodyfikowanej 
										// macierzy - bede ja dalej naprawiac
										hrMatrix = modifiedHrMatrix.getCopy();
										outputData.incrementWrongRapairs();
										outputData.getGoalFunctionValues().add(-1.0);
									}
								}								
							}
							
							// zanim zrobie put musze policzyc jakosc zabronienia
							// oraz czas przez ktory nie bede mogl go ruszac
							if(newTaboo != null && repairSuccessful){
								// ustawiam jak dlugo ruch bedzie na liscie tabu
								// w tym celu licze jak dobry byl to ruch
								int numberOfRoundsToRemove = getMovementTabooIterations(movement, 
										goalValue, outputData.getBestGoalFunctionValue());
								newTaboo.setNumberOfRoundsToRemove(numberOfRoundsToRemove);
								taboos.add(newTaboo);
							}
							
							// zachowuje wynik iteracji
							if(repairSuccessful){
								hrMatrix = bestRepairedHrMatrix.getCopy();
								if(goalValue > outputData.getBestGoalFunctionValue()){
									outputData.setBestSchedule(bestRepairedHrMatrix);
									outputData.setBestGoalFunctionValue(goalValue);
								}
							}else{
								// nie udalo sie naprawic macierzy wiec ustawiam
								// ostania poprawna macierz
								hrMatrix = lastValidMatrix;
							}
						}
					}		
				}
			}
		}
	}

	/** pobiera liczbe iteracji na jaka ruch zostanie dodany
	 * do listy tabu */
	private int getMovementTabooIterations(IMovement movement, 
			double currentGoalFunctionValue, double bestGoalFunctionValue) {
		double ratio = currentGoalFunctionValue/bestGoalFunctionValue;
		
		if(ratio < 0.5){
			return movement.getWeakMovementTabooIterations();
		}else if(ratio < 0.8){
			return movement.getMediumMovementTabooIterations();
		}else if(ratio < 1){
			return movement.getGoodMovementTabooIterations();
		}else{
			return movement.getTheBestMovementTabooIterations();
		}
	}

	/** dekrementuje zabronienia i usuwam nieaktywne */
	private void decrementAndRemoveEndedTaboos() {
		Iterator<Taboo> itr = taboos.iterator();
		while(itr.hasNext()){  
			Taboo element = itr.next();  
			int tmp = element.getNumberOfRoundsToRemove();
			tmp--;
			element.setNumberOfRoundsToRemove(tmp);
            if(tmp == 0){  
                itr.remove();  
            }  
        }  
	}

	public OutputData getOutputData() {
		return outputData;
	}

	public List<IRepairAlgorithm> getRepairAlgorithms() {
		return repairAlgorithms;
	}

	public void setRepairAlgorithms(List<IRepairAlgorithm> repairAlgorithms) {
		this.repairAlgorithms = repairAlgorithms;
	}

	public IGoalFunction getGoalFunction() {
		return goalFunction;
	}

	public void setGoalFunction(IGoalFunction goalFunction) {
		this.goalFunction = goalFunction;
	}

	public List<IMovement> getMovements() {
		return movements;
	}

	public void setMovements(List<IMovement> movements) {
		this.movements = movements;
	}

	public List<IValidator> getValidators() {
		return validators;
	}

	public void setValidators(List<IValidator> validators) {
		this.validators = validators;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public int getNumberOfIterations() {
		return numberOfIterations;
	}
	
}
