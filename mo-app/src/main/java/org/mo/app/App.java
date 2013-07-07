package org.mo.app;

import java.io.IOException;

import org.mo.app.input.FilesReader;

public class App {
	public static void main(String[] args) throws IOException {
		FilesReader filesReader = new FilesReader();
		filesReader.readEmployees();
		filesReader.readProjects();
	}
}
