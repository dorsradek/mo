package org.mo.app.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FilesReader {

	private String filename;

	public void readEmployees() throws IOException {

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
	}

	public void readProjects() throws IOException {

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
	}
}
