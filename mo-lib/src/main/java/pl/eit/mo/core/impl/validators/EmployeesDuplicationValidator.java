package pl.eit.mo.core.impl.validators;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.TaskRow;

/** walidator sprawdza czy nie wystepuja duplikacje pracownikow*/

public class EmployeesDuplicationValidator implements IValidator {

	public List<Integer> excecute(DaySchedule daySchedule,
			List<TaskRow> tasksData) {
		
		List<Integer> faults = new ArrayList<Integer>();
		List<Integer> tmpEmployees = new ArrayList<Integer>();
		
		for(int fieldIndex=0; fieldIndex < daySchedule.getScheduleFields().size(); fieldIndex++){
			ScheduleField field = daySchedule.getScheduleFields().get(fieldIndex);
			for(int emplId : field.getEmployees()){
				if(tmpEmployees.contains(emplId)){
					// dodaje do listy bledow to pole poniewaz pracownik
					// o danym id pracuje nad innym zadaniem
					faults.add(fieldIndex);
				}else{
					tmpEmployees.add(emplId);
				}
			}
		}
		
		return faults;
	}

}
