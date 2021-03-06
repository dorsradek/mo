package pl.eit.mo.core.impl.others;

import javax.swing.JOptionPane;

import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.core.interfaces.IGoalFunction;
import pl.eit.mo.core.others.ScheduleField;
import pl.eit.mo.core.others.TaskRow;
import pl.eit.mo.dto.InputData;

public class SalaryGoalFunction extends IGoalFunction {

	public SalaryGoalFunction(InputData inputData) {
		super(inputData);
	}

	public double getValue(HRMatrix hrMatrix) throws Exception {

		double salary = 0;
		for (ProjectInfo project : projects) {
			int projectDurationInDays = 0;
			// szukam najdalej wysunietej jedynki sposrod wszystkich
			// zadan kazdego projektu
			try {
				for (int rowId = 0; rowId < hrMatrix.getRows().size(); rowId++) {
					TaskRow row = hrMatrix.getRows().get(rowId);
					if (row.getProjectName().equals(project.name)) {
						int day = 0;
						ScheduleField scheduleField = hrMatrix.getDay(day)
								.getScheduleFields().get(rowId);
						while (!scheduleField.isTaskFinished()
								&& day < hrMatrix.getPeriodInDays() - 1) {
							day++;
							scheduleField = hrMatrix.getDay(day)
									.getScheduleFields().get(rowId);
						}
						day++;
						// dostaje liczbe po ilu dniach skonczono prace
						if (day > projectDurationInDays) {
							projectDurationInDays = day;
						}
					}
				}
			} catch (Exception e) {
				throw new Exception();
			}

			// obliczam wynagrodzenie dla tego projektu
			double oneTimePenalty = 0;
			double daysPenalty = 0;
			if (project.deadline < projectDurationInDays) {
				oneTimePenalty = project.penaltyForDelay;
				daysPenalty = (projectDurationInDays - project.deadline)
						* project.dayPenaltyForDelay;
			}
			salary += project.salary - oneTimePenalty - daysPenalty;
		}

		return salary;
	}

}
