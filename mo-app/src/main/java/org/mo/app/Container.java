package org.mo.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class Container {

	private static volatile Container instance = null;

	private List<Rule> ruleList = new ArrayList<Rule>();
	private Map<Integer, Event> eventList = new HashMap<Integer, Event>();
	private Map<Integer, State> stateList = new HashMap<Integer, State>();

	public static Container getInstance() {
		if (instance == null) {
			synchronized (Container.class) {
				if (instance == null) {
					instance = new Container();
					instance.loadFiles();
				}
			}
		}
		return instance;
	}

	public void loadFiles() {
		instance.loadStatesFromFile("states.txt");
		instance.loadEventsFromFile("events.txt");
		instance.loadRulesFromFile("rules.txt");

	}

	public State simulate(State initial, List<Event> controlVector) {
		State s = initial;

		for (Event n : controlVector) {
			s = s.transitionFunction(n);
		}

		return s;

	}

	public int loadRulesFromFile(String name) {

		try {
			Scanner in = new Scanner(new FileReader(name));
			in.useDelimiter(Pattern.compile("(\\r\\n?|\\n)"));

			while (in.hasNext()) {
				int firstId = in.nextInt();
				State firstState = stateList.get(firstId);
				int finalId = in.nextInt();
				State finalState = stateList.get(finalId);
				int eventId = in.nextInt();
				Event event = eventList.get(eventId);
				double probability = in.nextDouble();
				if (in.hasNext())
					in.nextLine();
				ruleList.add(new Rule(firstState, finalState, event,
						probability));
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Bad file directory");
			e.printStackTrace();
			return 1;
		} catch (InputMismatchException e) {
			System.out.println("Bad rule read");
			e.printStackTrace();
		}

		return 0;
	}

	public int loadStatesFromFile(String name) {

		try {
			Scanner in = new Scanner(new FileReader(name));
			in.useDelimiter(Pattern.compile("(\\r\\n?|\\n)"));

			while (in.hasNext()) {
				int id = in.nextInt();
				String desc = new String(in.next());
				int value = in.nextInt();
				if (in.hasNext())
					in.nextLine();
				stateList.put(id, new State(id, value, desc));
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Bad file directory");
			e.printStackTrace();
			return 1;
		} catch (InputMismatchException e) {
			System.out.println("Bad state read");
			e.printStackTrace();
		}

		return 0;
	}

	public int loadEventsFromFile(String name) {

		try {
			Scanner in = new Scanner(new FileReader(name));
			in.useDelimiter(Pattern.compile("(\\r\\n?|\\n)"));

			while (in.hasNext()) {
				int eventId = in.nextInt();
				String desc = new String(in.next());
				if (in.hasNext())
					in.nextLine();
				eventList.put(eventId, new Event(eventId, desc));
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Bad file directory");
			e.printStackTrace();
			return 1;
		} catch (InputMismatchException e) {
			System.out.println("Bad Event read");
			e.printStackTrace();
		}

		return 0;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public Map<Integer, State> getStateList() {
		return stateList;
	}

	/*
	 * public Rule getRule(int initialState, int finalState){ return
	 * findRule(initialState, finalState); }
	 */

	public State getState(int id) {
		return stateList.get(id);
	}

	public Map<Integer, Event> getEventList() {
		return eventList;
	}

	public void setEventList(Map<Integer, Event> eventList) {
		this.eventList = eventList;
	}

	/*
	 * public Rule findRule(int initialState, int finalState, int eventId){
	 * for(Rule e: ruleList){ if(e.getInitialState().getId() == initialState &&
	 * e.getFinalState().getId() == finalState && e.getEvent().getId() ==
	 * eventId) return e; } return null; }
	 */

	public State findRule(State initialState, Event event) {
		for (Rule e : ruleList) {
			if (e.getInitialState().equals(initialState)
					&& e.getEvent().equals(event))
				return e.getFinalState();
		}
		return null;
	}

	public void printData() {

		for (Rule e : ruleList) {
			System.out.println(e);
		}

		for (State e : stateList.values()) {
			System.out.println(e);
		}

		for (Event e : eventList.values()) {
			System.out.println(e);
		}

	}

}
