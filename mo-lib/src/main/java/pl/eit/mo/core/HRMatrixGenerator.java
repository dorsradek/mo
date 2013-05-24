package pl.eit.mo.core;

import java.util.ArrayList;
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
		
		// rekalkuluje cala macierz
		hrMatrix.recalculateAll();
		
		// naprawiam macierz
		for(int day=0; day<inputData.getPeriodInDays(); day++){
			DaySchedule daySchedule = hrMatrix.getDay(day);
			
			// musze zwalidowac i jesli jest zle to uruchomic 
			// algorytm naprawy na tym dniu
			boolean isValid = false;
			while(!isValid){
				List<Integer> faults = new ArrayList<Integer>();
				for(IValidator validator : validators){
					List<Integer> tmpFaults = validator.excecute(daySchedule, hrMatrix.getTasksData());
					faults.addAll(tmpFaults);
				}
				// sprawdzam czy musze cos naprawiac
				if(faults.size() != 0){
					repairAlgorithm.excecute(daySchedule);
				}else{
					isValid = true;
				}
			}
			
		}
		
		return hrMatrix;
	}

}
