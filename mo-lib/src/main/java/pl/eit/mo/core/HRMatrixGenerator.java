package pl.eit.mo.core;

import java.util.List;

import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
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
		
		// przydzielam losowo pracownikow
		for(int day=0; day<inputData.getPeriodInDays(); day++){
			DaySchedule daySchedule = hrMatrix.getDay(day);
			
			/** wstawiam pokolei pracownikow do losowych pol */
			for(int emplId=0; emplId<inputData.getEmployees().size(); emplId++){
				ScheduleField scheduleField = daySchedule.getRandField();
				scheduleField.getEmployees().add(emplId);
			}
		}
		
		boolean isSuccessful = HRUtil.repairMatrix(0, inputData.getPeriodInDays(), 
				hrMatrix, repairAlgorithm, validators);
		
		if(isSuccessful){
			return hrMatrix;
		}
		
		return null;
	}

}
