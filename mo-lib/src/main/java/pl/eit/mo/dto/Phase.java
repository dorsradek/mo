package pl.eit.mo.dto;

import java.util.List;

public class Phase
{
	/** nazwa etapu */
	private String name;
	
	/** lista zadan */
	private List<Task> tasks;

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

}
