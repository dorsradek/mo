package pl.eit.mo.core.impl.repairalgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.others.ScheduleField;

/** algorytm naprawia dzien w sposob losowy. przenosi pracownikow z pol
 * w ktorych wystapily bledy do losowo wybranego (poprawnego) pola */

public class RandRepairAlgorithm extends IRepairAlgorithm {
	
	private Random randomGenerator;
	
	@Override
	public void excecute(List<ScheduleField> scheduleFields, List<Integer> faults) {
		randomGenerator = new Random();
		
		// przenosze pracownikow ktorzy sa blednie umieszczeni
		for(Integer invalidRowId : faults){
			int maxRowIndex = scheduleFields.size();
			int newPlace = randOtherThan(faults, maxRowIndex);
			ScheduleField oldEmplsPlace = scheduleFields.get(invalidRowId);
			ScheduleField newEmplsPlace = scheduleFields.get(newPlace);
			newEmplsPlace.getEmployees().addAll(oldEmplsPlace.getEmployees());
			scheduleFields.get(invalidRowId).setEmployees(new ArrayList<Integer>());
		}
	}

	/** losuje liczbe od 0 do maxIndex nie zawierajaca sie w podanej
	 * liscie */
	private int randOtherThan(List<Integer> faults, 
			int maxIndex) {
		// zawsze wejde min jeden raz do while'a
		int index = faults.get(0);
		while(faults.contains(index)){
			index = randomGenerator.nextInt(maxIndex);
		}
		return index;
	}
}
