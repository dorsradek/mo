package pl.eit.mo.core.interfaces;

import pl.eit.mo.core.others.DaySchedule;


public abstract class IRepairAlgorithm {

	/** nazwa algorytmu naprawy (unikalna) */
	private String name;

	/** czy algorytm naprawy korzysta z mechanizmu zabronien */
	private boolean isUseTaboos;

	/** czy jesli nie potrafi naprawic to ma pominac zabronienia */
	private boolean skipTaboosIfCantRepair;

	/** liczba prob naprawy kazdej kolumny */
	private int numberOfRepairsProbes;

	public abstract void excecute(DaySchedule daySchedule);
	
	public boolean isUseTaboos() {
		return isUseTaboos;
	}

	public void setUseTaboos(boolean isUseTaboos) {
		this.isUseTaboos = isUseTaboos;
	}

	public boolean isSkipTaboosIfCantRepair() {
		return skipTaboosIfCantRepair;
	}

	public void setSkipTaboosIfCantRepair(boolean skipTaboosIfCantRepair) {
		this.skipTaboosIfCantRepair = skipTaboosIfCantRepair;
	}

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
