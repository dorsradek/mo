package org.mo.app.gui.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.mo.app.Event;
import org.mo.app.gui.EventLeftPanel;

public class EventComboBoxListener implements ItemListener {

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Event event = (Event) e.getItem();
			handleItemSelected(event);
		}
	}

	private void handleItemSelected(Event event) {
		EventLeftPanel.eventSelected = event;
	}

}
