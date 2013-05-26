package pl.eit.mo.core.interfaces;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.dto.InputData;
import pl.eit.mo.dto.Project;

public abstract class  IGoalFunction {
	
	protected List<ProjectInfo> projects;
	
	public IGoalFunction(InputData inputData) {
		// wyciagam informacje o projektach do specjalnej listy
		projects = new ArrayList<IGoalFunction.ProjectInfo>();
		for(Project project : inputData.getProjects()){
			ProjectInfo info = new ProjectInfo();
			info.name = project.getName();
			info.deadline = project.getDeadline();
			info.salary = project.getSalary();
			info.penaltyForDelay = project.getPenaltyForDelay();
			info.dayPenaltyForDelay = project.getDayPenaltyForDelay();
			projects.add(info);
		}
		
	}
	
	public abstract double getValue(HRMatrix hrMatrix);
	
	protected class ProjectInfo{
		public String name;
		public int deadline;
		public double salary;
		public double penaltyForDelay;
		public double dayPenaltyForDelay;
	}
	
}
