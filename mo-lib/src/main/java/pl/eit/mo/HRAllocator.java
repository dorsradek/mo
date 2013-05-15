package pl.eit.mo;

import java.util.List;

import pl.eit.mo.dto.complex.ConfigurationData;

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
 * 6. oczywiscie tworze obiekt tej klasy i na nim to wolam
 * 7. lepiej zostaw Radkowi konfigurowanie pobierania ruchow i algorytmow naprawy z fabryk
 *    udostepnij mu tylko fabryki
 * 8. wiec przerob configuration data -> wywal fabryki na zewnatrz 
 * 9. Kryt aspiracji wtedy gdy nie moze znalezc ruchu -> bierze ruch z tabluicy zabronien 
 *    i go wykonuje
 * 10. dodaj mozliwosc zwrocenia macierzy wyjsciowej tak aby mozna bylo ja ustawic jako wejsciowa
 * 	   dla kolejnych iteracji 
 */


public class HRAllocator {
	
	/** lista z algorytmami naprawy */
	private List<IRepairAlgorithm> repairAlgorithms;
	
	/** aktywna funkcja celu */
	private IGoalFunction goalFunction;
	
	/** lista ruchow */
	private List<IMovement> movements;
	
	/** 
	 * walidator macierzy. sprawdza czy rozwiazanie 
	 * jest dopuszczalne 
	 * */
	private IValidator validator;
	
	/** lista z zabronieniami */
	private List<Taboo> tabooList;
	

	private HRAllocator() {
	}

	public static HRAllocator getInstance() {
		HRAllocator obj = new HRAllocator();
		return obj;
	}

	/** przeprowadza konfiguracje obiektu HRAllocator podanymi danymi */
	public void init(ConfigurationData data) {
		// pobieram odpowiednie elementy z fabryk MovementFactory 
		// oraz RepairAlgorithmFactory
	}

	/** dodaje ruch do algorytmu */
	public void addMovement(IMovement movement) {

	}

	/** dodaje algorytm naprawy do algorytmu */
	public void addRepairAlgorithm(IRepairAlgorithm repAlgh) {

	}

	/** ustawia nowa funkcje celu */
	public void setGoalFunction(IGoalFunction goalFunction) {

	}

	/**
	 * ustawia nowy walidator sprawdzajacy poprawnosc danych 
	 * w transitionMatrix
	 */
	public void setValidator(IValidator validator) {

	}
	
	/** glowna metoda klasy. */
	public void run(){
		for(int i=0; i < numberOfDays; i++){
			// usuwam nieaktywne zabronienia
			// petla po ruchach
		}
	}
	
	/** wyciagam liste z najlepszych wynikami */
	public List<OutputData> getBestResults(){
		return null;
	}
}
