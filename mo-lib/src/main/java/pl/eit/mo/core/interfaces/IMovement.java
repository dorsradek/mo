package pl.eit.mo.core.interfaces;

import java.util.List;

import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.Taboo;
import pl.eit.mo.core.others.TaskRow;

public abstract class IMovement {
	
	/** nazwa ruchu (unikalna) */
	protected String name;
	
	/** liczba podjetych akcji tym ruchem w jednym dniu */
	protected int numberOfActionsInDay;
	
	/** liczba iteracji na liscie tabu jesli po zrobieniu ruchu 
	 * i naprawie wartosc funkcji celu jest mniejsza niz 50% */
	protected int weakMovementTabooIterations = 0;
	
	/** liczba iteracji na liscie tabu jesli po zrobieniu ruchu 
	 * i naprawie wartosc funkcji celu jest od 50 do 80% */
	protected int mediumMovementTabooIterations = 0;
	
	/** liczba iteracji na liscie tabu jesli po zrobieniu ruchu 
	 * i naprawie wartosc funkcji celu jest od 80 do 100% */
	protected int goodMovementTabooIterations = 0;
	
	/** liczba iteracji na liscie tabu jesli po zrobieniu ruchu 
	 * i naprawie wartosc funkcji celu jest wieksza od 100%  */
	protected int theBestMovementTabooIterations = 0;

	/**
	 * maksymalna liczba prob znalezienia ruchu. jesli przekrocze ta liczbe
	 * korzystam z kryteriow aspiracji
	 */
	protected int maxNumberOfMovementProbes;
	
	/** zabronienie dla tego ruchu */
	 protected String movementTabooValue;
	
	
	public abstract boolean tryExcecute(HRMatrix hrMatrix,int day, List<TaskRow> taskRowsData,
			List<Taboo> taboos);
	
	
	public int getNumberOfActionsInDay() {
		return numberOfActionsInDay;
	}

	public void setNumberOfActionsInDay(int numberOfActionsInDay) {
		this.numberOfActionsInDay = numberOfActionsInDay;
	}

	public String getName() {
		return name;
	}

	public int getMaxNumberOfMovementProbes() {
		return maxNumberOfMovementProbes;
	}

	public void setMaxNumberOfMovementProbes(int maxNumberOfMovementProbes) {
		this.maxNumberOfMovementProbes = maxNumberOfMovementProbes;
	}

	public String getMovementTabooValue() {
		return movementTabooValue;
	}


	public int getWeakMovementTabooIterations() {
		return weakMovementTabooIterations;
	}


	public void setWeakMovementTabooIterations(int weakMovementTabooIterations) {
		this.weakMovementTabooIterations = weakMovementTabooIterations;
	}


	public int getMediumMovementTabooIterations() {
		return mediumMovementTabooIterations;
	}


	public void setMediumMovementTabooIterations(int mediumMovementTabooIterations) {
		this.mediumMovementTabooIterations = mediumMovementTabooIterations;
	}


	public int getGoodMovementTabooIterations() {
		return goodMovementTabooIterations;
	}


	public void setGoodMovementTabooIterations(int goodMovementTabooIterations) {
		this.goodMovementTabooIterations = goodMovementTabooIterations;
	}


	public int getTheBestMovementTabooIterations() {
		return theBestMovementTabooIterations;
	}


	public void setTheBestMovementTabooIterations(int theBestMovementTabooIterations) {
		this.theBestMovementTabooIterations = theBestMovementTabooIterations;
	}
	
}
