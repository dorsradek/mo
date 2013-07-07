package org.mo.app;

import java.io.IOException;
import java.util.List;

import org.mo.app.input.FilesReader;

import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.Project;

public class App {

	public static void main(String[] args) throws IOException {

		FilesReader filesReader = new FilesReader();
		List<Employee> employees = filesReader.readEmployees();
		List<Project> projects = filesReader.readProjects();

		InputData inputData = new InputData();
		inputData.setPeriodInDays(20);
		inputData.setEmployees(employees);
		inputData.setProjects(projects);

	}
}
