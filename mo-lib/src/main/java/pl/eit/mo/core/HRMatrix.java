package pl.eit.mo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.others.DaySchedule;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.TaskRow;
import pl.eit.mo.dto.Dupa;
import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.Phase;
import pl.eit.mo.dto.Project;
import pl.eit.mo.dto.Task;

/** Klasa przechowujaca macierz z harmonogramem przydzialu 
 * pracownikow do projektow oraz dostarczajaca API  do tej macierzy */

public class HRMatrix implements Serializable{
	
	private static int NUM_OF_EXCEC_COPIES = 0;
	private static double TIME_OF_COPIES_IN_MILIS = 0;
	
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
		//long copyStartTime = System.currentTimeMillis();
		
		HRMatrix newHRMatrix = new HRMatrix();
		newHRMatrix.setPeriodInDays(this.periodInDays);
		newHRMatrix.setEmployees(this.employees);
		for(TaskRow row : rows){
			newHRMatrix.getRows().add(row.getCopy());
		}
		
		for(DaySchedule daySchedule : schedule){
			newHRMatrix.getSchedule().add(daySchedule.getCopy());
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
        }
		
		long copyTime = System.currentTimeMillis() - copyStartTime;
        NUM_OF_EXCEC_COPIES++;
        TIME_OF_COPIES_IN_MILIS += copyTime;*/
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
				}else{
					currDay.getScheduleFields().get(field).setTaskFinished(false);
				}
					
			}
		}else{
			// nie uwzgledniam przypadku ze cos zostanie skonczone pierwszego dnia
			// nie jest to potrzebne
			DaySchedule currDay = schedule.get(0);
			currDay.recalculateWorkDone(rows, employees);
			for(int field=0; field < currDay.getScheduleFields().size(); field++){
				if(currDay.getScheduleFields().get(field).getWorkDone() >=  rows.get(field).getDurationTime()){
					currDay.getScheduleFields().get(field).setTaskFinished(true);
				}else{
					currDay.getScheduleFields().get(field).setTaskFinished(false);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		String word = "";
		ScheduleField prevTaskField = null;
		for(int taskIndex=0;taskIndex<rows.size();taskIndex++){
			for(int day=0;day<schedule.size();day++){
				ScheduleField taskField = schedule.get(day).getScheduleFields().get(taskIndex);
				if(day>0){
					prevTaskField = schedule.get(day-1).getScheduleFields().get(taskIndex);
				}
				word="";
				word = getEmployeesToString(taskField);
				result += word;
				result += "\t";
			}
			result += "\n";
		}
		result += "\n";
		prevTaskField = null;
		for(int taskIndex=0;taskIndex<rows.size();taskIndex++){
			for(int day=0;day<schedule.size();day++){
				ScheduleField taskField = schedule.get(day).getScheduleFields().get(taskIndex);
				if(day>0){
					prevTaskField = schedule.get(day-1).getScheduleFields().get(taskIndex);
				}
				word="";
				if(taskField.isTaskFinished() == false 
						&& taskField.getEmployees().size() > 0
						&& day == 0){
					word = "+";
				}else if(day != 0
						&& prevTaskField.isTaskFinished() == false 
						&& taskField.getEmployees().size() > 0){
					word = "+";	
				}else if(taskField.isTaskFinished() == false 
						&& taskField.getWorkDone() > 0 ){
					word = "-";
				}else{
					word = " ";
				}
				result += word;
			}
			result += "\n";
		}
		
		result += "\n";
		prevTaskField = null;
		for(int taskIndex=0;taskIndex<rows.size();taskIndex++){
			for(int day=0;day<schedule.size();day++){
				ScheduleField taskField = schedule.get(day).getScheduleFields().get(taskIndex);
				if(day>0){
					prevTaskField = schedule.get(day-1).getScheduleFields().get(taskIndex);
				}
				result += String.format("%.1f", taskField.getWorkDone());
				result += "\t";
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
		while(word.length() < (rows.size())){
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
	public static List<Dupa> dupas = new ArrayList<Dupa>();
	
	public void asd() {
		dupas = new ArrayList<Dupa>();
		String result = "";
		String word = "";
		ScheduleField prevTaskField = null;
		
		for(int taskIndex=0;taskIndex<rows.size();taskIndex++){
			String projectName = rows.get(taskIndex).getProjectName();
			String phaseName = rows.get(taskIndex).getPhaseName();
			String taskName = rows.get(taskIndex).getTaskName();
			String taskFullName = "P-" + projectName + " F-" + phaseName + " T-" + taskName;
			int start = 0;
			int end = 0;
			boolean can = true;
			boolean endcan = true;
			for(int day=0;day<schedule.size();day++){
				ScheduleField taskField = schedule.get(day).getScheduleFields().get(taskIndex);
				if(day>0){
					prevTaskField = schedule.get(day-1).getScheduleFields().get(taskIndex);
				}
				word="";
				if(taskField.isTaskFinished() == false 
						&& taskField.getEmployees().size() > 0
						&& day == 0){
					if (can) {
						start = day;
						can = false;
					}
				}else if(day != 0
						&& prevTaskField.isTaskFinished() == false 
						&& taskField.getEmployees().size() > 0){
					word = "+";
					if (can) {
						start = day;
						can = false;
					}
				}else if(taskField.isTaskFinished() == true){
					if (endcan) {
						end = day;
						endcan = false;
					}
				}else{
					word = " ";
				}
				result += word;
			}
			System.out.println(taskIndex + " start: " + start + " end: " + end);
			Dupa dupa = new Dupa();
			dupa.name = taskFullName;
			dupa.start = start;
			dupa.end = end;
			dupas.add(dupa);
			result += "\n";
		}
	}
}
