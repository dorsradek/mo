package pl.eit.mo.dto;

import java.util.ArrayList;
import java.util.List;

import pl.eit.mo.core.HRMatrix;

public class OutputData {

	/** lista z wartosciami funkcji celu dla wszystkich iteracji */
	private List<Double> goalFunctionValues;
	
	/** najlepszy otrzymany harmonogram */
	private HRMatrix bestSchedule;
	
	/** najwyzsza wartosc funkcji celu */
	private double bestGoalFunctionValue;
	
	public OutputData() {
		goalFunctionValues = new ArrayList<Double>();
		bestGoalFunctionValue = 0;
	}

	public List<Double> getGoalFunctionValues() {
		return goalFunctionValues;
	}

	public void setGoalFunctionValues(List<Double> goalFunctionValues) {
		this.goalFunctionValues = goalFunctionValues;
	}

	public HRMatrix getBestSchedule() {
		return bestSchedule;
	}

	public void setBestSchedule(HRMatrix bestSchedule) {
		this.bestSchedule = bestSchedule;
	}

	public void setBestGoalFunctionValue(double bestGoalFunctionValue) {
		this.bestGoalFunctionValue = bestGoalFunctionValue;
	}

	public double getBestGoalFunctionValue() {
		return bestGoalFunctionValue;
	}
	
	
}
