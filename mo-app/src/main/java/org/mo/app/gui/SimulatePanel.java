package org.mo.app.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mo.app.Event;
import org.mo.app.State;
import org.mo.app.gui.listeners.SimulateButtonListener;

@SuppressWarnings("serial")
public class SimulatePanel extends JPanel {

	public static List<Event> controlVector = new ArrayList<Event>();

	public static State initialState = StatePanel.stateSelected;
	public static JLabel finalStateLabel;

	public SimulatePanel() {

		finalStateLabel = new JLabel("             ");
		this.add(finalStateLabel);
		finalStateLabel.setBorder(BorderFactory.createLineBorder(Color.blue));

		JButton simulateButton = new JButton("Symuluj");
		simulateButton.addActionListener(new SimulateButtonListener());
		this.add(simulateButton);

	}

}
