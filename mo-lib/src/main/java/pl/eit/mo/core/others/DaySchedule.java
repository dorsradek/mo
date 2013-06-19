package pl.eit.mo.core.others;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.eit.mo.dto.Employee;

/** Klasa magazynuje harmonogram przydzialu pracownikow 
 * do projektow, etapow i zadan na konkretny dzien*/

public class DaySchedule  implements Serializable{
	
	private Random randomGenerator;
	
	/** mapa z polami charakterystycznymi dla kazdego projektu, etapu i zadania */
	private List<ScheduleField> scheduleFields;
	
	public DaySchedule() {
		scheduleFields = new ArrayList<ScheduleField>();
		randomGenerator = new Random();
	}

	public DaySchedule(List<ScheduleField> scheduleFields) {
		this();
		this.scheduleFields = scheduleFields;
	}
	
	public DaySchedule getCopy() {
		DaySchedule newDaySchedule = new DaySchedule();
		for(int i=0; i<scheduleFields.size(); i++){
			newDaySchedule.getScheduleFields().add(scheduleFields.get(i).getCopy());
		}
		return newDaySchedule;
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
