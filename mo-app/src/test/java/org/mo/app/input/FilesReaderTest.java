package org.mo.app.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.Project;

public class FilesReaderTest {

	private String filename;

	@Test
	public void readEmployees() throws IOException {
		this.filename = "D:\\Radek\\Nauka\\mo\\mo-lib\\employees.txt";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		EmployeesReaderTest e = null;
		try {
			String line = br.readLine();
			e = new EmployeesReaderTest();

			while (line != null) {
				e.row = line;
				e.getEmployee();
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		for (Employee employee : e.employees) {
			System.out.println(employee);
		}
	}

	@Test
	public void readProjects() throws IOException {
		this.filename = "D:\\Radek\\Nauka\\mo\\mo-lib\\projects.txt";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		ProjectsReaderTest e = null;
		try {
			String line = br.readLine();
			e = new ProjectsReaderTest();

			while (line != null) {
				e.row = line;
				e.getProject();
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		for (Project project : e.projects) {
			System.out.println(project);
		}
	}
}
