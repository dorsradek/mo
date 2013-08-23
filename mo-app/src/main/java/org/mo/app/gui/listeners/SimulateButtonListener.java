package org.mo.app.gui.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.mo.app.Container;
import org.mo.app.State;
import org.mo.app.gui.SimulatePanel;
import org.mo.app.gui.StatePanel;

public class SimulateButtonListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		try {
			Container container = Container.getInstance();
			State finalState = container.simulate(StatePanel.stateSelected,
					SimulatePanel.controlVector);

			SimulatePanel.finalStateLabel.setText("Stan koncowy: "
					+ finalState.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog((Component) e.getSource(),
					"Wybierz stan pocz�tkowy", "B��d",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
