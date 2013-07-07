package pl.eit.mo.dto;

import java.util.List;

public class Phase
{
	/** nazwa etapu */
	private String name;
	
	/** lista zadan */
	private List<Task> tasks;
	
	public Phase() {
		super();
	}
	
	public Phase(String name) {
		super();
		this.name = name;
	}

	public Phase(String name, List<Task> tasks) {
		super();
		this.tasks = tasks;
		this.name = name;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
