package pl.eit.mo.core;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.TaskRow;
import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.Phase;
import pl.eit.mo.dto.Project;
import pl.eit.mo.dto.Task;

/** Klasa przechowujaca macierz z harmonogramem przydzialu 
 * pracownikow do projektow oraz dostarczajaca API  do tej macierzy */

public class HRMatrix {
	
	/** harmonogram */
	private List<DaySchedule> schedule;
	
	/** mapa pracownikow */
	private List<Employee> employees;
	
	/** mapa wierszy zawierajacych wszystkie informacje o danym zadaniu */
	private List<TaskRow> rows; 
	
	/** okres rozliczeniowy */
	private int periodInDays;
	
	/** tworze pusta macierz na podstawie danych wejsiowych */
	public HRMatrix(InputData inputData) {
		
		periodInDays = inputData.getPeriodInDays();
		rows = new ArrayList<TaskRow>();
		schedule = new ArrayList<DaySchedule>();
		employees = inputData.getEmployees();
		
		// tworze harmonogram
		for(int day=1; day <= periodInDays; day++){
			List<ScheduleField> scheduleFields = new ArrayList<ScheduleField>();
			for(Project project : inputData.getProjects()){
				for(Phase phase : project.getPhases()){
					for(Task task : phase.getTasks()){
						// tworze kolumne zawierajaca infomacje o kazdym zadaniu
						if(day == 1){
							TaskRow row = new TaskRow(project, phase, task);
							rows.add(row);
						}
						// dodaje pole do dnia w macierzy
						ScheduleField scheduleField = new ScheduleField();
						scheduleFields.add(scheduleField);
					}
				}
			}
			DaySchedule daySchedule = new DaySchedule(scheduleFields);
			schedule.add(daySchedule);
		}
	}
	
	/** robie rekalkulacje calej macierzy */
	public void recalculateAll() {
		DaySchedule prevDay = schedule.get(0);
		prevDay.recalculateWorkDone(rows, employees);
		for(int day=1; day<periodInDays; day++){
			// pobieram fieldy pokolei i dodaje workDone
			// z poprzedniego do aktulanego
			DaySchedule currDay = schedule.get(day);
			currDay.recalculateWorkDone(rows, employees);
			
			for(int field=0; field < currDay.getScheduleFields().size(); field++){
				float prevDayWorkDone = prevDay.getScheduleFields().get(field).getWorkDone();
				float currDayWorkDone = currDay.getScheduleFields().get(field).getWorkDone();
				currDay.getScheduleFields().get(field).setWorkDone(prevDayWorkDone + currDayWorkDone);
			}
			prevDay = currDay;
		}
	}

	/** zwracam dzien o podanym id */
	public DaySchedule getDay(int day) {
		return schedule.get(day);
	}

	/** zwracam kolumne z danymi o zadaniach */
	public List<TaskRow> getTasksData() {
		return rows;
	}
	
}
