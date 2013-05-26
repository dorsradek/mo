package pl.eit.mo.core.others;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.dto.Employee;

public class ScheduleField {
	
	/** lista zawierajaca pracownikow (po id) przydzielonych do pola */
	private List<Integer> employees;
	
	/** suma czasu poswiecony na te prace do tej pory */
	private float workDone;
	
	/** zmienna okreslajaca czy zadanie zostalo juz ukonczone */
	private boolean isTaskFinished;
	
	public ScheduleField() {
		super();
		this.employees = new ArrayList<Integer>();
		workDone = 0;
	}

	/** rekalkuluje prace wykonana w tym polu */
	public void recalculateWorkDone(TaskRow taskRow, List<Employee> employees2) {
		String requiredSkill = taskRow.getRequiredSkill();
		workDone = 0;
		for(Integer emplId : employees){
			workDone += employees2.get(emplId).getSkills().get(requiredSkill);
		}
	}
	
	public List<Integer> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Integer> employees) {
		this.employees = employees;
	}

	public float getWorkDone() {
		return workDone;
	}

	public void setWorkDone(float workDone) {
		this.workDone = workDone;
	}

	public boolean isTaskFinished() {
		return isTaskFinished;
	}

	public void setTaskFinished(boolean isTaskFinished) {
		this.isTaskFinished = isTaskFinished;
	}
}
