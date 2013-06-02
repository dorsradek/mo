package pl.eit.mo.core.interfaces;

import java.util.List;

import pl.eit.mo.core.others.ScheduleField;


public abstract class IRepairAlgorithm {

	/** nazwa algorytmu naprawy (unikalna) */
	protected String name;

	/** maksymalan liczba prob naprawy kazdego dnia (kolumny macierzy)
	 * jesli zostanie przekroczona uznajemy ze nie da sie naprawic 
	 * harmonogramu */
	protected int maxNumberOfDayRepairsProbes;
	
	/** liczba napraw ptrob naprawy danym algorytmem */
	protected int numberOfRepairsProbes;

	public abstract void excecute(List<ScheduleField> scheduleFields, List<Integer> faults);

	public int getMaxNumberOfDayRepairsProbes() {
		return maxNumberOfDayRepairsProbes;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfRepairsProbes() {
		return numberOfRepairsProbes;
	}

	public void setMaxNumberOfDayRepairsProbes(int maxNumberOfDayRepairsProbes) {
		this.maxNumberOfDayRepairsProbes = maxNumberOfDayRepairsProbes;
	}

	public void setNumberOfRepairsProbes(int numberOfRepairsProbes) {
		this.numberOfRepairsProbes = numberOfRepairsProbes;
	}


}
