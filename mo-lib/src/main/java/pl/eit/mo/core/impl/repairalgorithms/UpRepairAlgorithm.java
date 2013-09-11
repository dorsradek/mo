package pl.eit.mo.core.impl.repairalgorithms;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;

/** algorytm naprawia dzien przenoszac blednie ustawionych pracownikow 
 * o jedno zadanie wyzej */

public class UpRepairAlgorithm extends IRepairAlgorithm {

	public UpRepairAlgorithm() {
		super();
		this.name = "UpRepairAlgorithm";
	}
	
	@Override
	public void excecute(List<ScheduleField> scheduleFields, List<Integer> faults) {
		
		// przenosze pracownikow ktorzy sa blednie umieszczeni
		for(Integer invalidRowId : faults){
			int maxRowIndex = scheduleFields.size();
			int newPlace = invalidRowId - 1;
			ScheduleField oldEmplsPlace = scheduleFields.get(invalidRowId);
			ScheduleField newEmplsPlace = scheduleFields.get(newPlace);
			newEmplsPlace.getEmployees().addAll(oldEmplsPlace.getEmployees());
			scheduleFields.get(invalidRowId).setEmployees(new ArrayList<Integer>());
		}
		
	}
}
