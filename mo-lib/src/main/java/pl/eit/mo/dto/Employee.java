package pl.eit.mo.dto;

import java.util.Map;

public class Employee
{
	/** alias pracownika (unikalny) */
	private String alias;
	
	public Employee(String alias) {
		super();
		this.alias = alias;
	}

	/** mapa z umiejetnosciami */
	private Map<String, Double> skills;

	public Map<String, Double> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, Double> skills) {
		this.skills = skills;
	}

	public String getAlias() {
		return alias;
	}
}
