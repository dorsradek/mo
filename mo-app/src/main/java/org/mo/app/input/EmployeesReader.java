package org.mo.app.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.eit.mo.dto.Employee;

public class EmployeesReader {

	public String row;
	public List<Employee> employees = new ArrayList<Employee>();

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
		employees.add(employee);
	}
}
