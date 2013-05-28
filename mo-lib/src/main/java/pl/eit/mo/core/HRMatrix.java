package pl.eit.mo.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.FastByteArrayOutputStream;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.TaskRow;
import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.Phase;
import pl.eit.mo.dto.Project;
import pl.eit.mo.dto.Task;

/** Klasa przechowujaca macierz z harmonogramem przydzialu 
 * pracownikow do projektow oraz dostarczajaca API  do tej macierzy */

public class HRMatrix implements Serializable{
	
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
	
	/** zwraca kopie samego siebie */
	public HRMatrix getCopy() {
		HRMatrix newHRMatrix = null;
        try {
            FastByteArrayOutputStream fbos =
                    new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(this);
            out.flush();
            out.close();

            ObjectInputStream in =
                new ObjectInputStream(fbos.getInputStream());
            newHRMatrix = (HRMatrix) in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
		return newHRMatrix;
	}
	
	/** robie rekalkulacje jednego dnia macierzy */
	public void recalculateDay(int day) {
		if(day > 0){
			// pobieram fieldy pokolei i dodaje workDone
			// z poprzedniego do aktulanego
			DaySchedule prevDay = schedule.get(day-1);
			DaySchedule currDay = schedule.get(day);
			currDay.recalculateWorkDone(rows, employees);
			
			for(int field=0; field < currDay.getScheduleFields().size(); field++){
				float prevDayWorkDone = prevDay.getScheduleFields().get(field).getWorkDone();
				float currDayWorkDone = currDay.getScheduleFields().get(field).getWorkDone();
				float allWorkDoneInCurrDay = prevDayWorkDone + currDayWorkDone;
				currDay.getScheduleFields().get(field).setWorkDone(allWorkDoneInCurrDay);
				
				// ustawiam ze skonczono prace nad zadaniem
				if(allWorkDoneInCurrDay >=  rows.get(field).getDurationTime()){
					currDay.getScheduleFields().get(field).setTaskFinished(true);
				}
					
			}
		}else{
			// nie uwzgledniam przypadku ze cos zostanie skonczone pierwszego dnia
			// nie jest to potrzebne
			DaySchedule currDay = schedule.get(0);
			currDay.recalculateWorkDone(rows, employees);
		}
	}

	/** zwracam dzien o podanym id */
	public DaySchedule getDay(int day) {
		return schedule.get(day);
	}

	/** zwracam kolumne z danymi o zadaniach */
	public List<TaskRow> getTaskRowsData() {
		return rows;
	}

	public int getPeriodInDays() {
		return periodInDays;
	}
	
}
