package pl.eit.mo.dto.core;

public class MovementConfiguration
{
	/** nazwa ruchu (unikalna) */
	private String name;

	/** liczba podjetych akcji tym ruchem w jednym dniu */
	private int numberOfActionsInDay;

	/**
	 * maksymalna liczba prob znalezienia ruchu. jesli przekrocze ta liczbe
	 * korzystam z kryteriow aspiracji
	 */
	private int maxNumberOfMovementChoice;

}
