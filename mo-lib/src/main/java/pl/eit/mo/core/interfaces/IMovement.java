package pl.eit.mo.core.interfaces;

public abstract class IMovement {

	/** id ruchu */
	private int id;
	
	/** nazwa ruchu (unikalna) */
	private String name;
	
	/** liczba podjetych akcji tym ruchem w jednym dniu */
	private int numberOfActionsInDay;
	
	/** zakres dni na ktorych dziala kazdy z ruchow w jednej iteracji */
	private int movementRangeInDays;
	
	/**
	 * wpolczynnik wplywa na to jak dlugo dany ruch pozostaje na liscie Taboo.
	 */
	private float movementTabooRatio;

	/**
	 * maksymalna liczba prob znalezienia ruchu. jesli przekrocze ta liczbe
	 * korzystam z kryteriow aspiracji
	 */
	private int maxNumberOfMovementProbes;
	
	
	public int getNumberOfActionsInDay() {
		return numberOfActionsInDay;
	}

	public void setNumberOfActionsInDay(int numberOfActionsInDay) {
		this.numberOfActionsInDay = numberOfActionsInDay;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getMaxNumberOfMovementProbes() {
		return maxNumberOfMovementProbes;
	}

	public void setMaxNumberOfMovementProbes(int maxNumberOfMovementProbes) {
		this.maxNumberOfMovementProbes = maxNumberOfMovementProbes;
	}

	public int getMovementRangeInDays() {
		return movementRangeInDays;
	}

	public void setMovementRangeInDays(int movementRangeInDays) {
		this.movementRangeInDays = movementRangeInDays;
	}

	public float getMovementTabooRatio() {
		return movementTabooRatio;
	}

	public void setMovementTabooRatio(float movementTabooRatio) {
		this.movementTabooRatio = movementTabooRatio;
	}
	
}
