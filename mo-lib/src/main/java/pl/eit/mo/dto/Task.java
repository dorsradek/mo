package pl.eit.mo.dto;

public class Task
{
	/** umiejetnosc potrzeba aby wykonywac dane zadanie */
	private String requiredSkill;

	/** czas jaki trzeba poswiecic aby ukonczyc zadanie */
	private double durationTime;
	
	public Task(String requiredSkill, double durationTime) {
		super();
		this.requiredSkill = requiredSkill;
		this.durationTime = durationTime;
	}

	public String getRequiredSkill() {
		return requiredSkill;
	}

	public double getDurationTime() {
		return durationTime;
	}

}
