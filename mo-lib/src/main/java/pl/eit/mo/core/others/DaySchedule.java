package pl.eit.mo.core.others;

import java.util.List;
import java.util.Random;

import pl.eit.mo.dto.Employee;

/** Klasa magazynuje harmonogram przydzialu pracownikow 
 * do projektow, etapow i zadan na konkretny dzien*/

public class DaySchedule {
	
	private Random randomGenerator;
	
	/** mapa z polami charakterystycznymi dla kazdego projektu, etapu i zadania */
	private List<ScheduleField> scheduleFields;

	public DaySchedule(List<ScheduleField> scheduleFields) {
		super();
		this.scheduleFields = scheduleFields;
		randomGenerator = new Random();
	}

	/** zwracam losowe pole z tego dnia */
	public ScheduleField getRandField() {
		int index = randomGenerator.nextInt(scheduleFields.size());
		return scheduleFields.get(index);
	}

	/** rekalkuluje wykonana prace w kazdym polu */
	public void recalculateWorkDone(List<TaskRow> rows, List<Employee> employees) {
		for(int i=0; i<rows.size(); i++){
			scheduleFields.get(i).recalculateWorkDone(rows.get(i), employees);
		}
	}

	public List<ScheduleField> getScheduleFields() {
		return scheduleFields;
	}
	
}
