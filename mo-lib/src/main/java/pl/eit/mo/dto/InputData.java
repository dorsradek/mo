package pl.eit.mo.dto;

import java.util.List;

public class InputData
{
	/** okres rozliczeniowy */
	private int periodInDays;
	
	/** lista projektow */
	private List<Project> projects;

	/** lista pracownikow */
	private List<Employee> employees;

	public int getPeriodInDays() {
		return periodInDays;
	}

	public void setPeriodInDays(int periodInDays) {
		this.periodInDays = periodInDays;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
