package pl.eit.mo;

import java.util.List;

import pl.eit.mo.dto.complex.ConfigurationData;

/**
 * Glowna klasa dostarczajaca interfejs do biblioteki rozdzielajacej pracownikow
 * do projektow
 */

public class HRAllocator {
	
	/** lista z algorytmami naprawy */
	private List<IRepairAlgorithm> repairAlgorithms;
	
	/** aktywna */
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
