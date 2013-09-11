package org.mo.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

import org.mo.app.gui.InputDataPanel;
import org.mo.app.gui.MainPanel;
import org.mo.app.gui.TabbedMainPanel;
import org.mo.app.input.FilesReader;

import pl.eit.mo.core.HRAllocator;
import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.core.HRMatrixGenerator;
import pl.eit.mo.core.impl.others.SalaryGoalFunction;
import pl.eit.mo.core.impl.repairalgorithms.RandRepairAlgorithm;
import pl.eit.mo.core.impl.validators.EmployeesDuplicationValidator;
import pl.eit.mo.core.impl.validators.PhasesSequenceValidator;
import pl.eit.mo.core.interfaces.IGoalFunction;
import pl.eit.mo.core.interfaces.IMovement;
import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.OutputData;
import pl.eit.mo.dto.Project;

public class App implements Runnable {

	public static OutputData outData = new OutputData();

	public static JFrame frame;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {

		// JFrame.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame("Human Resources Allocator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new MainPanel());

		frame.pack();
		frame.setVisible(true);
		frame.setSize(800, 700);
		frame.setResizable(false);

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		frame.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);

		frame.setVisible(true);
	}

	public static JLabel statusLabel = new JLabel("Gotowy...");
	public static List<IRepairAlgorithm> repairAlgorithms = new ArrayList<IRepairAlgorithm>();
	public static List<IMovement> movements = new ArrayList<IMovement>();

	public static void doSth() throws Exception {
		statusLabel.setText("Proszę czekać...");
		FilesReader filesReader = new FilesReader();
		List<Employee> employees = filesReader.readEmployees();
		List<Project> projects = filesReader.readProjects();

		InputData inputData = new InputData();
		inputData.setPeriodInDays(InputDataPanel.periosInDays);
		inputData.setEmployees(employees);
		inputData.setProjects(projects);

		IValidator validator1 = new EmployeesDuplicationValidator();
		IValidator validator2 = new PhasesSequenceValidator();

		IRepairAlgorithm repairAlgorithm = new RandRepairAlgorithm();
		repairAlgorithm.setMaxNumberOfDayRepairsProbes(20);
		repairAlgorithm.setNumberOfRepairsProbes(5);

		List<IValidator> validators = new ArrayList<IValidator>();
		validators.add(validator1);
		validators.add(validator2);

		HRMatrixGenerator matrixGenerator = new HRMatrixGenerator(validators, repairAlgorithm);
		IGoalFunction goalFunction = new SalaryGoalFunction(inputData);

		/*
		 * double maxSalary = 0; for(int i=0; i<100;i++){ HRMatrix startMatrix =
		 * matrixGenerator.excecute(inputData); double salary = 0;
		 * if(startMatrix != null){ salary = goalFunction.getValue(startMatrix);
		 * if(salary>maxSalary){ maxSalary = salary; } } }
		 * System.out.println(maxSalary);
		 */

		// generuje macierz startowa
		HRMatrix startMatrix = matrixGenerator.excecute(inputData);
		int i = 0;
		while (startMatrix == null && i < 1000) {
			++i;
			startMatrix = matrixGenerator.excecute(inputData);
		}

		PrintWriter fileStartOut = new PrintWriter("startMatrix.txt");
		fileStartOut.println(startMatrix.toString());
		fileStartOut.close();

		System.out.println(goalFunction.getValue(startMatrix));

		// sprawdza w srodku czy konfiguracja jest kompletna
		HRAllocator ha = new HRAllocator(startMatrix, inputData);
		ha.setGoalFunction(goalFunction);
		ha.setMovements(movements);
		ha.setRepairAlgorithms(repairAlgorithms);
		ha.setValidators(validators);
		ha.setNumberOfIterations(InputDataPanel.numberOfIterations);
		ha.excecute();

		outData = ha.getOutputData();
		outData.getBestGoalFunctionValue();

		System.out.println(goalFunction.getValue(outData.getBestSchedule()));

		PrintWriter fileFinishOut = new PrintWriter("outMatrix.txt");
		fileFinishOut.println(outData.getBestSchedule().toString());
		fileFinishOut.close();

		// System.out.println(outData.getBestGoalFunctionValue());

		PrintWriter fileOut = new PrintWriter("goal_values.txt");
		int index = 0;
		for (Double value : outData.getGoalFunctionValues()) {
			fileOut.println(index + ";" + value);
			index++;
		}
		fileOut.close();
		outData.getBestSchedule().asd();

		TabbedMainPanel.ganttChart.reCreate();
		TabbedMainPanel.otherChart.reCreate();
		InputDataPanel.scoreLabel.setText(String.valueOf(App.outData.getBestGoalFunctionValue()));
		InputDataPanel.scoreLabel.revalidate();
		statusLabel.setText("Gotowy...");
	}

	public void run() {
		try {
			doSth();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(App.frame, "Wprowadź poprawne dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
		}
	}
}
