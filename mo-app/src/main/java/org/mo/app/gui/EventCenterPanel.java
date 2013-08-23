package org.mo.app.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.mo.app.gui.listeners.OkButtonListener;

@SuppressWarnings("serial")
public class EventCenterPanel extends JPanel {

	private JButton acceptButton;

	public EventCenterPanel() {
		JButton acceptButton = new JButton("->Dodaj->");
		acceptButton.addActionListener(new OkButtonListener());
		this.add(acceptButton);
	}

}
