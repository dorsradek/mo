package pl.eit.mo.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	private Map<Integer, Taboo> taboos;
	
	/** macierz na ktorej optymalizuje */
	private HRMatrix hrMatrix;
	
	
	/** dane wejsiowe */
	private InputData inputData;
	
	/** dane wyjsciowe */
	private OutputData outputData;

	
	public HRAllocator(HRMatrix hrMatrix, InputData inputData) {
		super();
		this.hrMatrix = hrMatrix.getCopy();
		this.inputData = inputData;
	}
	
	/** glowna metoda klasy. */
	public void excecute(){
		/*outputData = new OutputData();
		
		for(int day=0; day < inputData.getPeriodInDays(); day++){
			// dekrementuje zabronienia i wywalam przestarzale
			decrementAndRemoveEndedTaboos();
			
			// wykonuje kazdy ruch na liscie
			for(IMovement movement : movements){
				
				// kazdy z ruchow wykonuje okreslona ilosc razy
				for(int j=0; j< movement.getNumberOfActionsInDay(); j++){
					// kazdy ruch probuje wykonac pewna ilosc razy 
					int probe = 0;
					boolean isSuccessful = false;
					Taboo newTaboo = null;
					while(movement.getMaxNumberOfMovementProbes() < probe && !isSuccessful){
						isSuccessful = movement.tryExcecute(day, taboos);
						probe++;
					}
					if(isSuccessful){
						newTaboo = movement.getMovementTaboo();
	
						// musze naprawic macierz w tym celu probuje to zrobic kazdym
						// dostepnym algorytmem naprawy - wybieram najlepszy rezultat
						double goalValue = 0;
						boolean repairSuccessful;
						HRMatrix outHrMatrix;
						for(IRepairAlgorithm repairAlgorithm : repairAlgorithms){
							HRMatrix tmpHrMatrix = hrMatrix.getCopy();
							// probuje naprawic macierz tmpHrMatrix
							repairSuccessful = HRUtil.repairMatrix(day, inputData.getPeriodInDays(), 
									tmpHrMatrix, repairAlgorithm, validators);
							
							if(repairSuccessful){
								double tmpValue = goalFunction.getValue(tmpHrMatrix);
								// jesli naprawilem lepiej to ustawiam ten rezultat
								if(tmpValue > goalValue){
									repairSuccessful = true;
									goalValue = tmpValue;
									outHrMatrix = tmpHrMatrix.getCopy();
								}
							}
						}
						
						// zanim zrobie put musze policzyc jakosc zabronienia
						// oraz czas przez ktory nie bede mogl go ruszac
						if(newTaboo != null && repairSuccessful){
							newTaboo.setQuality(goalValue);
							taboos.put(movement.getId(), newTaboo);
						}
						
						// zachowuje wynik iteracji
						if(repairSuccessful){
							double salary = goalFunction.getValue(hrMatrix);
							outputData.getGoalFunctionValues().add(salary);
							if(salary > outputData.getBestGoalFunctionValue()){
								HRMatrix bestSchedule = hrMatrix.getCopy();
								outputData.setBestSchedule(bestSchedule);
								outputData.setBestGoalFunctionValue(salary);
							}
						}
					}
				}		
			}
		}*/
	}

	/** dekrementuje zabronienia i usuwam nieaktywne */
	private void decrementAndRemoveEndedTaboos() {
		for(Iterator<Map.Entry<Integer, Taboo>> it = 
				taboos.entrySet().iterator(); it.hasNext(); ) {
     
			Map.Entry<Integer, Taboo> entry = it.next();
			int tmp = entry.getValue().getNumberOfRoundsToRemove();
			tmp--;
		    if(tmp <= 0) {
		    	it.remove();
		    }else{
		    	entry.getValue().setNumberOfRoundsToRemove(tmp);
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
	
}
