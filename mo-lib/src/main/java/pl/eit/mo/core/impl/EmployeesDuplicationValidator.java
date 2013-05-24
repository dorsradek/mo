package pl.eit.mo.core.impl;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.TaskRow;

public class EmployeesDuplicationValidator implements IValidator {

	public List<Integer> excecute(DaySchedule daySchedule,
			List<TaskRow> tasksData) {
		
		List<Integer> faults = new ArrayList<Integer>();
		
		
		return faults;
	}

}
