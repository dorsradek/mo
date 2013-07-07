package org.mo.app.input;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.dto.Phase;
import pl.eit.mo.dto.Project;
import pl.eit.mo.dto.Task;

public class ProjectsReader {

	public String row;
	private String[] splittedRow;

	private Project project;
	private Phase phase;
	private Task task;

	public List<Project> projects = new ArrayList<Project>();
	private List<Phase> phases;
	private List<Task> tasks;

	public void getProject() {

		splitRow();

		if (isProject()) {
			handleProject();
		} else if (isPhase()) {
			handlePhase();
		} else if (isTask()) {
			handleTask();
		}
	}

	private void splitRow() {
		this.splittedRow = this.row.split("\\|");
	}

	private boolean isProject() {

		if (this.splittedRow[0].equals("P")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isPhase() {

		if (this.splittedRow[0].equals("F")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isTask() {

		if (this.splittedRow[0].equals("T")) {
			return true;
		} else {
			return false;
		}
	}

	private void handleProject() {
		project = new Project();
		project.setName(splittedRow[1]);
		project.setSalary(Double.valueOf(splittedRow[2]));
		project.setDeadline(Integer.valueOf(splittedRow[3]));
		project.setPenaltyForDelay(Double.valueOf(splittedRow[4]));
		project.setDayPenaltyForDelay(Double.valueOf(splittedRow[5]));
		projects.add(project);

		phases = new ArrayList<Phase>();
		project.setPhases(phases);
	}

	private void handlePhase() {
		phase = new Phase();
		phase.setName(splittedRow[1]);
		phases.add(phase);

		tasks = new ArrayList<Task>();
		phase.setTasks(tasks);
	}

	private void handleTask() {
		task = new Task();
		task.setName(splittedRow[1]);
		task.setRequiredSkill(splittedRow[2]);
		task.setDurationTime(Double.valueOf(splittedRow[3]));

		tasks.add(task);
	}
}
