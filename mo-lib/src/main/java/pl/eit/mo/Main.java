package pl.eit.mo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.core.HRMatrixGenerator;
import pl.eit.mo.core.impl.repairalgorithms.RandRepairAlgorithm;
import pl.eit.mo.core.impl.validators.EmployeesDuplicationValidator;
import pl.eit.mo.core.impl.validators.PhasesSequenceValidator;
import pl.eit.mo.core.interfaces.IRepairAlgorithm;
import pl.eit.mo.core.interfaces.IValidator;
import pl.eit.mo.dto.Employee;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.Phase;
import pl.eit.mo.dto.Project;
import pl.eit.mo.dto.Task;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// bardzo proste fabryki
		//MovementsFactory	// konfiguruje i moge wyciagac rozne ruchy z ta konfiguracja
		//RepairAlgorithmsFactory // j.w.
		
		// ------- EMPLOYEES ------------------
		
		Employee emp1 = new Employee("emp1");
		Map<String, Double> skills = new HashMap<String, Double>();
		skills.put("Java" , 1.0);
		skills.put("SQL" , 0.8);
		skills.put("HTML" , 0.8);
		skills.put("JavaScript" , 0.2);
		emp1.setSkills(skills);
		
		Employee emp2 = new Employee("emp2");
		skills = new HashMap<String, Double>();
		skills.put("Java" , 0.8);
		skills.put("SQL" , 0.2);
		skills.put("HTML" , 0.5);
		skills.put("JavaScript" , 0.2);
		emp2.setSkills(skills);
		
		Employee emp3 = new Employee("emp3");
		skills = new HashMap<String, Double>();
		skills.put("Java" , 0.6);
		skills.put("SQL" , 0.3);
		skills.put("HTML" , 0.8);
		skills.put("JavaScript" , 0.6);
		emp3.setSkills(skills);
		
		Employee emp4 = new Employee("emp4");
		skills = new HashMap<String, Double>();
		skills.put("Java" , 0.6);
		skills.put("SQL" , 0.8);
		skills.put("HTML" , 0.4);
		skills.put("JavaScript" , 0.4);
		emp4.setSkills(skills);
		
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);
		employees.add(emp4);
		
		// ------- PROJECT 1 ------------------
		
		Task z1 = new Task("z1", "Java", 4);
		Task z2 = new Task("z2", "SQL", 2);
		
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(z1);
		tasks.add(z2);
		
		Phase e1 = new Phase("e1", tasks);
		
		z1 = new Task("z1", "HTML", 3);
		tasks = new ArrayList<Task>();
		tasks.add(z1);
		
		Phase e2 = new Phase("e2", tasks);
		
		List<Phase> phases = new ArrayList<Phase>();
		phases.add(e1);
		phases.add(e2);
		
		Project p1 = new Project("p1");
		p1.setDeadline(8);
		p1.setPenaltyForDelay(4);
		p1.setSalary(20);
		p1.setDayPenaltyForDelay(0.5);
		p1.setPhases(phases);
		
		// ------- PROJECT 2 ------------------
		
		z1 = new Task("z1", "Java", 2);
		z2 = new Task("z2", "SQL", 2);
		Task z3 = new Task("z3", "HTML", 2);
		
		tasks = new ArrayList<Task>();
		tasks.add(z1);
		tasks.add(z2);
		tasks.add(z3);
		
		e1 = new Phase("e1", tasks);
		
		z1 = new Task("z1", "JavaScript", 3);
		z2 = new Task("z2", "HTML", 1);
		tasks = new ArrayList<Task>();
		tasks.add(z1);
		tasks.add(z2);
		
		e2 = new Phase("e2", tasks);
		
		z1 = new Task("z1", "HTML", 2);
		tasks = new ArrayList<Task>();
		tasks.add(z1);
		
		Phase e3 = new Phase("e3", tasks);
		
		phases = new ArrayList<Phase>();
		phases.add(e1);
		phases.add(e2);
		phases.add(e3);
		
		Project p2 = new Project("p2");
		p2.setDeadline(12);
		p2.setPenaltyForDelay(0);
		p2.setSalary(30);
		p2.setDayPenaltyForDelay(2);
		p2.setPhases(phases);
		
		// ------- MAIN ------------------
		
		List<Project> projects = new ArrayList<Project>();
		projects.add(p1);
		projects.add(p2);
		
		InputData inputData = new InputData();
		inputData.setPeriodInDays(15);
		inputData.setEmployees(employees);
		inputData.setProjects(projects);
		
		IValidator validator1 = new EmployeesDuplicationValidator();
		IValidator validator2 = new PhasesSequenceValidator();
		
		IRepairAlgorithm repairAlgorithm = new RandRepairAlgorithm();
		
		List<IValidator> validators = new ArrayList<IValidator>();
		validators.add(validator1);
		validators.add(validator2);
		
		HRMatrixGenerator matrixGenerator = new HRMatrixGenerator(validators, repairAlgorithm);
		HRMatrix startMatrix = matrixGenerator.excecute(inputData);
		
		return;
		/*HRAllocatorFactory haFactory = new HRAllocatorFactory();
		// do kazdej rzeczy fabryki przez ktore konfiguruje wszystko
		haFactory.setMovements(new ArrayList(){ movement1 });
		haFactory.setRepairAlgorithms(new ArrayList(){ repair1 });
		haFactory.setValidators(validators);
		haFactory.setGoalFunction(goalFunction);
		
		
		// sprawdza w srodku czy konfiguracja jest kompletna
		HRAllocator ha = haFactory.getHRAllocator();
		ha.setInputData(inputData);
		ha.setStartMatrix(startMatrix);
		ha.excecute();
		
		OutDataWrapper outData = ha.getOutData();
		outData.getMatrix();
		outData.getGoalFunctionsValues();	// pobieram liste z wszystkim wartosciami funkcji celu
		outData.getBestGoalFunctionData();	// pobieram wszystkie dane najlepszej (czasy trwania etapow, 
											// przydzial pracownikow do zadan itd
		 */
	}

}
