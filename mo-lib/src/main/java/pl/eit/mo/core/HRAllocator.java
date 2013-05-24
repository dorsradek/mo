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

	private HRAllocator() {
	}

	public static HRAllocator getInstance() {
		HRAllocator obj = new HRAllocator();
		return obj;
	}
	
	/** glowna metoda klasy. */
	public void excecute(){
		/*for(int day=0; day < inputData.getPeriodInDays(); day++){
			// dekrementuje zabronienia i wywalam przestarzale
			decrementAndRemoveEndedTaboos();
			
			// dla kazdej malej macierzy wykonuje kazdy ruch na liscie
			for(IMovement movement : movements){
				
				// pobieram mala macierz
				int daysRange = movement.getMovementRangeInDays();
				HRMatrix smallMatrix = hrMatrix.getPartOfMatrix(day, daysRange);
				
				// kazdy z ruchow wykonuje okreslona ilosc razy
				for(int j=0; j< movement.getNumberOfActionsInDay(); j++){
					// kazdy ruch probuje wykonac pewna ilosc razy 
					int probe = 0;
					boolean isSuccessful = false;
					Taboo newTaboo = null;
					while(movement.getMaxNumberOfMovementProbes() < probe && !isSuccessful){
						isSuccessful = movement.tryExcecute(smallMatrix, taboos);
						probe++;
					}
					// jesli nie udalo sie wykonac ruchu korzystam  
					// z kryterium aspiracji - nie dodaje wtedy zadnego ruchu
					// do tablicy zabronien
					if(!isSuccessful){
						// co z dayRange ? wybieraj o takim samym dayRange 
						Taboo tabooToExcecute = aspirationCritery.getTaboo();
						IMovement aspirationMovement = findMovement(tabooToExcecute);
						aspirationMovement.excecute(smallMatrix);
					}else{
						newTaboo = movement.getMovementTaboo();
					}
					
					// scalam macierze
					hrMatrix.merge(smallMatrix, day ,daysRange);
					// rekalkuluje postep pracy w kazdym zadaniu do okreslonego dnia
					hrMatrix.recalculate(day);
					
					// musze naprawic macierz w tym celu probuje to zrobic kazdym
					// dostepnym algorytmem naprawy - wybieram najlepszy rezultat
					float goalValue = 0;
					boolean repairSuccessful = false;
					HRMatrix outHrMatrix;
					for(IRepairAlgorithm repairAlgorithm : repairAlgorithms){
						HRMatrix tmpHrMatrix = new HRMatrix(hrMatrix);
						// zle napierw lec walidatorem a potem algorytmem naprawy a potem rekalkuluj
						boolean tmpSuccessful = repairAlgorithm.repair(day, tmpHrMatrix, validators);
						if(tmpSuccessful){
							float tmpValue = goalFunction.getValue(tmpHrMatrix);
							// jesli naprawilem lepiej to ustawiam ten rezultat
							if(tmpValue > goalValue){
								repairSuccessful = true;
								goalValue = tmpValue;
								outHrMatrix = new HRMatrix(tmpHrMatrix);
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
	
}
