package pl.eit.mo.core.interfaces;

import java.util.List;

import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.Taboo;
import pl.eit.mo.core.others.TaskRow;

public abstract class IMovement {
	
	/** nazwa ruchu (unikalna) */
	protected String name;
	
	/** liczba podjetych akcji tym ruchem w jednym dniu */
	protected int numberOfActionsInDay;
	
	/**
	 * wpolczynnik wplywa na to jak dlugo dany ruch pozostaje na liscie Taboo.
	 */
	protected float movementTabooRatio;

	/**
	 * maksymalna liczba prob znalezienia ruchu. jesli przekrocze ta liczbe
	 * korzystam z kryteriow aspiracji
	 */
	protected int maxNumberOfMovementProbes;
	
	/** zabronienie dla tego ruchu */
	 protected String movementTabooValue;
	
	
	public abstract boolean tryExcecute(DaySchedule day, List<TaskRow> taskRowsData,
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

	public float getMovementTabooRatio() {
		return movementTabooRatio;
	}

	public void setMovementTabooRatio(float movementTabooRatio) {
		this.movementTabooRatio = movementTabooRatio;
	}

	public String getMovementTabooValue() {
		return movementTabooValue;
	}
	
}
