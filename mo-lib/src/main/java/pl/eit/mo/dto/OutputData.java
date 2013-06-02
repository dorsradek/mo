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
	
	/** poprawne wywolania ruchow */
	private int successMovementExcecutes;
	
	/** bledne wywolania ruchow */
	private int wrongMovementExcecutes;
	
	/** poprawne naprawy */
	private int successRapairs;
	
	/** bledne naprawy*/
	private int wrongRapairs;
	
	public OutputData() {
		goalFunctionValues = new ArrayList<Double>();
		bestGoalFunctionValue = 0;
		successMovementExcecutes = 0;
		wrongMovementExcecutes = 0;
		successRapairs = 0;
		wrongRapairs = 0;
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

	public int getSuccessMovementExcecutes() {
		return successMovementExcecutes;
	}

	public void incrementSuccessMovementExcecutes() {
		this.successMovementExcecutes++;
	}

	public int getWrongMovementExcecutes() {
		return wrongMovementExcecutes;
	}

	public void incrementWrongMovementExcecutes() {
		this.wrongMovementExcecutes++;
	}

	public int getSuccessRapairs() {
		return successRapairs;
	}

	public void incrementSuccessRapairs() {
		this.successRapairs++;
	}

	public int getWrongRapairs() {
		return wrongRapairs;
	}

	public void	incrementWrongRapairs() {
		this.wrongRapairs++;
	}
	
	
}
