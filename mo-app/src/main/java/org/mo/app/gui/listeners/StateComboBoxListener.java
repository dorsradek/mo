package org.mo.app.gui.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.mo.app.State;
import org.mo.app.gui.StatePanel;

public class StateComboBoxListener implements ItemListener {

	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			State state = (State) event.getItem();
			handleItemSelected(state);
		}
	}

	private void handleItemSelected(State state) {
		StatePanel.stateSelected = state;
	}

}
