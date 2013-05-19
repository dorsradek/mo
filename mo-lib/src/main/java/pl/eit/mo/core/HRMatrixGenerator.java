package pl.eit.mo.core;

import java.util.List;

import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.dto.InputData;

public class HRMatrixGenerator {
	
	/** walidatory wykorzystywane przy tworzeniu macierzy */
	List<IValidator> validators;
	
	/** algorytm wykorzystywany przy naprawianiu 
	 * wylosowanej macierzy */
	IRepairAlgorithm repairAlgorithm;

	public HRMatrixGenerator(List<IValidator> validators,
			IRepairAlgorithm repairAlgorithm) {
		this.validators = validators;
		this.repairAlgorithm = repairAlgorithm;
	}

	/** metoda zwraca macierz z harmonogramem przedstawiajacym
	 * przydzial pracownikow do zadan */
	public HRMatrix excecute(InputData inputData) {
		HRMatrix hrMatrix = new HRMatrix(inputData);
		// losuje przydzial pracownikow dla kazdego dnia
		// dokonuje rekalkulacji macierzy ? a moze lepiej przy walidacji to sprawdzac
		// sprawdzam walidatorami pokolei dni -> jesli
		// jest cos zle uruchamiam algorytm naprawy na tym dniu
		// zwracam macierz
		
		return null;
	}

}
