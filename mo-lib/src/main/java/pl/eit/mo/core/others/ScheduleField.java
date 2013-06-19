package pl.eit.mo.core.others;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.eit.mo.dto.Employee;

public class ScheduleField implements Serializable{
	
	private static Random randomGenerator;
	
	static{
		randomGenerator = new Random();
	}
	
	/** lista zawierajaca pracownikow (po id) przydzielonych do pola */
	private List<Integer> employees;
	
	/** suma czasu poswiecony na te prace do tej pory */
	private float workDone;
	
	/** zmienna okreslajaca czy zadanie zostalo juz ukonczone */
	private int isTaskFinished;
	
	public ScheduleField() {
		super();
		this.employees = new ArrayList<Integer>();
		workDone = 0;
		setTaskFinished(false);
	}
	
	public ScheduleField getCopy() {
		ScheduleField newScheduleField = new ScheduleField();
		newScheduleField.setWorkDone(workDone);
		List newList = new ArrayList<Integer>(employees);
		newScheduleField.setEmployees(newList);
		newScheduleField.setTaskFinished(isTaskFinished());
		return newScheduleField;
	}

	/** rekalkuluje prace wykonana w tym polu */
	public void recalculateWorkDone(TaskRow taskRow, List<Employee> employees2) {
		String requiredSkill = taskRow.getRequiredSkill();
		workDone = 0;
		for(Integer emplId : employees){
			workDone += employees2.get(emplId).getSkills().get(requiredSkill);
		}
	}
	
	/** usuwam pracownika o podanym id z listy pracownikow */
	public void removeEmployee(int removeEmplId) {
		 List<Integer> newlist = new ArrayList<Integer>();
		 for(Integer emplId : employees){
			 if(emplId != removeEmplId){
				 newlist.add(emplId);
			 }
		 }
		 employees = newlist;
	}
	
	/** jesli liczba pracownikow > 0 zwraca losowego pracownika */
	public int getRandEmployeeId(){
		if(employees.size() == 0){
			return -1;
		}else if(employees.size() == 1){
			return employees.get(0);
		}else{
			int emplIndex = randomGenerator.nextInt(employees.size());
			return employees.get(emplIndex);
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
		if(this.isTaskFinished == 0){
			return false;
		}
		return true;
	}

	public void setTaskFinished(boolean isTaskFinished) {
		if(isTaskFinished){
			this.isTaskFinished = 1;
		}else{
			this.isTaskFinished = 0;
		}
	}

}
