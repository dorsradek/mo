package org.mo.app.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.mo.app.gui.EventLeftPanel;
import org.mo.app.gui.EventRightPanel;
import org.mo.app.gui.SimulatePanel;

public class OkButtonListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		if (EventLeftPanel.eventSelected != null) {
			EventRightPanel.model.addElement(EventLeftPanel.eventSelected);

			SimulatePanel.controlVector.add(EventLeftPanel.eventSelected);

		}
	}

}
