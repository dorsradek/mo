package pl.eit.mo.dto;

import java.util.List;

public class Project
{
	/** nazwa projektu*/
	private String name;
	
	/** lista faz(etapow) projektu */
	private List<Phase> phases;

	/**
	 * termin ostateczny oddania projektu. po uplynieciu tego dnia naliczane sa
	 * kary projektowe
	 */
	private int deadline;

	/** kara za przekroczenie czasu 
	 * projektu (jednorazowa) */
	private double penaltyForDelay;
	
	/** kara za jeden dzien zwloki */
	private double dayPenaltyForDelay;

	/** wynagrodzenie za ukonczony projekt */
	private double salary;
	
	public Project(String name) {
		this.name = name;
	}
	
	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public double getPenaltyForDelay() {
		return penaltyForDelay;
	}

	public void setPenaltyForDelay(double penaltyForDelay) {
		this.penaltyForDelay = penaltyForDelay;
	}

	public double getDayPenaltyForDelay() {
		return dayPenaltyForDelay;
	}

	public void setDayPenaltyForDelay(double dayPenaltyForDelay) {
		this.dayPenaltyForDelay = dayPenaltyForDelay;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

}
