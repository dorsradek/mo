package pl.eit.mo.core.others;

import java.io.Serializable;

import pl.eit.mo.dto.Phase;
import pl.eit.mo.dto.Project;
import pl.eit.mo.dto.Task;

public class TaskRow implements Serializable{
	
	/** nazwa projektu */
	private String projectName;
	
	/** nazwa etapu */
	private String phaseName;
	
	/** nazwa zadania */
	private String taskName;
	
	/** umiejetnosc potrzeba aby wykonywac dane zadanie */
	private String requiredSkill;

	/** czas jaki trzeba poswiecic aby ukonczyc zadanie */
	private double durationTime;
	
	public TaskRow() {
	}
	
	public TaskRow(Project project, Phase phase, Task task) {
		projectName = project.getName();
		phaseName = phase.getName();
		taskName = task.getName();
		requiredSkill = task.getRequiredSkill();
		durationTime = task.getDurationTime();
	}
	
	public TaskRow getCopy() {
		TaskRow newTaskRow = new TaskRow();
		newTaskRow.setDurationTime(durationTime);
		newTaskRow.setPhaseName(phaseName);
		newTaskRow.setProjectName(projectName);
		newTaskRow.setRequiredSkill(requiredSkill);
		newTaskRow.setTaskName(taskName);
		return newTaskRow;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getRequiredSkill() {
		return requiredSkill;
	}

	public double getDurationTime() {
		return durationTime;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setRequiredSkill(String requiredSkill) {
		this.requiredSkill = requiredSkill;
	}

	public void setDurationTime(double durationTime) {
		this.durationTime = durationTime;
	}

}
