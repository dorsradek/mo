package org.mo.app.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.Project;

public class FilesReader {

	private String filename;

	public List<Employee> readEmployees() throws IOException {

		this.filename = this.getClass().getClassLoader()
				.getResource("employees.txt").getPath();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		EmployeesReader e = null;
		try {
			String line = br.readLine();
			e = new EmployeesReader();

			while (line != null) {
				e.row = line;
				e.getEmployee();
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return e.employees;
	}

	public List<Project> readProjects() throws IOException {

		this.filename = this.getClass().getClassLoader()
				.getResource("projects.txt").getPath();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		ProjectsReader e = null;
		try {
			String line = br.readLine();
			e = new ProjectsReader();

			while (line != null) {
				e.row = line;
				e.getProject();
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return e.projects;
	}
}
