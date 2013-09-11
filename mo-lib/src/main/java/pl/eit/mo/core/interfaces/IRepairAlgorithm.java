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

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IRepairAlgorithm other = (IRepairAlgorithm) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
