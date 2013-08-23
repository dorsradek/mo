package org.mo.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.mo.app.input.FilesReader;

import pl.eit.mo.core.HRAllocator;
import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.core.HRMatrixGenerator;
import pl.eit.mo.core.impl.movements.InteligentRandEmployeeMovement;
import pl.eit.mo.core.impl.others.SalaryGoalFunction;
import pl.eit.mo.core.impl.repairalgorithms.RandRepairAlgorithm;
import pl.eit.mo.core.impl.repairalgorithms.UpRepairAlgorithm;
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

public class App {

	public static void main(String[] args) throws IOException {

		FilesReader filesReader = new FilesReader();
		List<Employee> employees = filesReader.readEmployees();
		List<Project> projects = filesReader.readProjects();

		InputData inputData = new InputData();
		inputData.setPeriodInDays(20);
		inputData.setEmployees(employees);
		inputData.setProjects(projects);

		IValidator validator1 = new EmployeesDuplicationValidator();
		IValidator validator2 = new PhasesSequenceValidator();

		IRepairAlgorithm repairAlgorithm = new RandRepairAlgorithm();
		repairAlgorithm.setMaxNumberOfDayRepairsProbes(10);
		repairAlgorithm.setNumberOfRepairsProbes(5);

		List<IValidator> validators = new ArrayList<IValidator>();
		validators.add(validator1);
		validators.add(validator2);

		HRMatrixGenerator matrixGenerator = new HRMatrixGenerator(validators,
				repairAlgorithm);
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
		while (startMatrix == null) {
			startMatrix = matrixGenerator.excecute(inputData);
		}

		PrintWriter fileStartOut = new PrintWriter("startMatrix.txt");
		fileStartOut.println(startMatrix.toString());
		fileStartOut.close();

		System.out.println(goalFunction.getValue(startMatrix));

		IRepairAlgorithm repairAlgorithm1 = new UpRepairAlgorithm();
		repairAlgorithm1.setMaxNumberOfDayRepairsProbes(1);
		repairAlgorithm1.setNumberOfRepairsProbes(1);

		List<IMovement> movements = new ArrayList<IMovement>();
		IMovement movement1 = new InteligentRandEmployeeMovement(10);
		movement1.setMaxNumberOfMovementProbes(5);
		movement1.setNumberOfActionsInDay(10);
		movement1.setWeakMovementTabooIterations(1);
		movement1.setMediumMovementTabooIterations(1);
		movement1.setGoodMovementTabooIterations(2);
		movement1.setTheBestMovementTabooIterations(3);
		movements.add(movement1);

		List<IRepairAlgorithm> repairAlgorithms = new ArrayList<IRepairAlgorithm>();
		// repairAlgorithms.add(repairAlgorithm);
		repairAlgorithms.add(repairAlgorithm1);

		// sprawdza w srodku czy konfiguracja jest kompletna
		HRAllocator ha = new HRAllocator(startMatrix, inputData);
		ha.setGoalFunction(goalFunction);
		ha.setMovements(movements);
		ha.setRepairAlgorithms(repairAlgorithms);
		ha.setValidators(validators);
		ha.setNumberOfIterations(1);
		ha.excecute();

		OutputData outData = ha.getOutputData();
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
	}
}
