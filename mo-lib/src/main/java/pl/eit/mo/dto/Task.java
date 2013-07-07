package pl.eit.mo.dto;

public class Task {
	/** nazwa zadania */
	private String name;

	/** umiejetnosc potrzeba aby wykonywac dane zadanie */
	private String requiredSkill;

	/** czas jaki trzeba poswiecic aby ukonczyc zadanie */
	private double durationTime;

	public Task() {
		super();
	}

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

	public void setName(String name) {
		this.name = name;
	}

	public void setRequiredSkill(String requiredSkill) {
		this.requiredSkill = requiredSkill;
	}

	public void setDurationTime(double durationTime) {
		this.durationTime = durationTime;
	}

}
