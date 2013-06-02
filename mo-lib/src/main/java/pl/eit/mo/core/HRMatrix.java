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
	
	private static int SHOW_GANTT_CHART = 0;
	private static int SHOW_SCHEDULE = 1;
	
	private static int NUM_OF_EXCEC_COPIES = 0;
	private static double TIME_OF_COPIES_IN_MILIS = 0;
	
	private static int CHOOSE_ACTION = SHOW_SCHEDULE;
	
	/** harmonogram */
	private List<DaySchedule> schedule;
	
	/** mapa pracownikow */
	private List<Employee> employees;
	
	/** mapa wierszy zawierajacych wszystkie informacje o danym zadaniu */
	private List<TaskRow> rows; 
	
	/** okres rozliczeniowy */
	private int periodInDays;
	
	public HRMatrix() {
		periodInDays = 0;
		schedule = new ArrayList<DaySchedule>();
		employees = new ArrayList<Employee>();
		rows = new ArrayList<TaskRow>();
	}
	
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
		long copyStartTime = System.currentTimeMillis();
		HRMatrix newHRMatrix = new HRMatrix();
		newHRMatrix.setPeriodInDays(this.periodInDays);
		newHRMatrix.setEmployees(this.employees);
		for(TaskRow row : rows){
			newHRMatrix.getRows().add(row.getCopy());
		}
		
        /*try {
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
        }*/
		
		long copyTime = System.currentTimeMillis() - copyStartTime;
        NUM_OF_EXCEC_COPIES++;
        TIME_OF_COPIES_IN_MILIS += copyTime;
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
	
	@Override
	public String toString() {
		String result = "";
		for(int j=0;j<rows.size();j++){
			for(int i=0;i<schedule.size();i++){
				ScheduleField field = schedule.get(i).getScheduleFields().get(j);
				String word="";
				if(CHOOSE_ACTION == SHOW_SCHEDULE){
					word = getEmployeesToString(field);
					result += word;
					result += "\t";
				}else if(CHOOSE_ACTION == SHOW_GANTT_CHART){
					if(field.isTaskFinished() == false 
							&& field.getWorkDone() > 0){
						word = "--";
					}else{
						word = "  ";
					}
					result += word;
				}
			}
			result += "\n";
		}
		return result;
	}

	/** pobieram liste pracownikow do stringa */
	private String getEmployeesToString(ScheduleField field) {
		String word = "";
		if(field.getEmployees().size() == 0){
			word += "-";
		}else{
			for(Integer emplId : field.getEmployees()){
				word += emplId+",";
			}
		}
		while(word.length() < (rows.size()-2)){
			word += " ";
		}
		return word;
	}

	/** zwracam dzien o podanym id */
	public DaySchedule getDay(int day) {
		return schedule.get(day);
	}

	public int getPeriodInDays() {
		return periodInDays;
	}

	public List<DaySchedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<DaySchedule> schedule) {
		this.schedule = schedule;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<TaskRow> getRows() {
		return rows;
	}

	public void setRows(List<TaskRow> rows) {
		this.rows = rows;
	}

	public void setPeriodInDays(int periodInDays) {
		this.periodInDays = periodInDays;
	}
	
}
