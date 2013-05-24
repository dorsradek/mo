package pl.eit.mo.core.interfaces;

import java.util.List;

import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.TaskRow;

public interface IValidator {

	List<Integer> excecute(DaySchedule daySchedule, List<TaskRow> tasksData);

}
