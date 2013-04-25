package pl.eit.mo.dto.core;

import java.util.List;

public class Project
{
	/** lista faz(etapow) projektu */
	private List<Phase> phases;

	/**
	 * termin ostateczny oddania projektu. po uplynieciu tego dnia naliczane sa
	 * kary projektowe
	 */
	private int deadline;

	/** kara za jeden dzien zwloki */
	private float dayPenaltyForDelay;

	/** wynagrodzenie za ukonczony projekt */
	private float salary;

}
