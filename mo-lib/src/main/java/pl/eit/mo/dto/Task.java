package pl.eit.mo.dto;

public class Task
{
	/** nazwa zadania */
	private String name;
	
	/** umiejetnosc potrzeba aby wykonywac dane zadanie */
	private String requiredSkill;

	/** czas jaki trzeba poswiecic aby ukonczyc zadanie */
	private double durationTime;
	
	public Task(String name, String requiredSkill, double durationTime) {
		super();
		this.requiredSkill = requiredSkill;
		this.durationTime = durationTime;
		this.name = name;
	}

	public String getRequiredSkill() {
		return requiredSkill;
	}

	public double getDurationTime() {
		return durationTime;
	}

	public String getName() {
		return name;
	}

}
