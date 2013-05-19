package pl.eit.mo.dto;

import java.util.List;

public class Phase
{
	/** lista zadan */
	private List<Task> tasks;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
