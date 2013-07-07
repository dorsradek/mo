package org.mo.app.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import pl.eit.mo.dto.Employee;

public class EmployeesReaderTest {
	
	public String row = "alias1|php:4.2|java:5.3";
	public List<Employee> employees = new ArrayList<Employee>();

	@Test
	public void getEmployee() {

		String[] splittedRow = this.row.split("\\|");
		String alias = "";
		Employee employee = new Employee();
		Map<String, Double> skills = new HashMap<String, Double>();
		for (int i = 0; i < splittedRow.length; i++) {
			if (i == 0) {
				alias = splittedRow[i];
				employee.setAlias(alias);
			} else {
				String[] skill = splittedRow[i].split("\\:");
				skills.put(skill[0].trim(), Double.valueOf(skill[1]));
			}
		}
		employee.setSkills(skills);
		System.out.println("Alias: " + alias);
		for (Entry<String, Double> entry : skills.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			System.out.println("Skill: " + key + ": " + value);
		}
		employees.add(employee);
	}
}
