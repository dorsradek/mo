package pl.eit.mo.core.interfaces;

import java.util.List;

import pl.eit.mo.core.others.ScheduleField;


public abstract class IRepairAlgorithm {

	/** nazwa algorytmu naprawy (unikalna) */
	private String name;

	/** liczba prob naprawy kazdej kolumny */
	private int numberOfRepairsProbes;

	public abstract void excecute(List<ScheduleField> scheduleFields, List<Integer> faults);

	public int getNumberOfRepairsProbes() {
		return numberOfRepairsProbes;
	}

	public void setNumberOfRepairsProbes(int numberOfRepairsProbes) {
		this.numberOfRepairsProbes = numberOfRepairsProbes;
	}

	public String getName() {
		return name;
	}

}
