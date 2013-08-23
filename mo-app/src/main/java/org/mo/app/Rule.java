package org.mo.app;

public class Rule {

	private State initialState;
	private State finalState;
	private Event event;
	private double probability;

	Rule(State initialSt, State finalSt, Event newEvent, double prob) {
		initialState = initialSt;
		finalState = finalSt;
		probability = prob;
		event = newEvent;

	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public State getFinalState() {
		return finalState;
	}

	public void setFinalState(State finalState) {
		this.finalState = finalState;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String toString() {

		return new String(" " + initialState + " " + finalState + " "
				+ probability);
	}

}