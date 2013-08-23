package org.mo.app.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mo.app.Container;
import org.mo.app.State;
import org.mo.app.gui.listeners.StateComboBoxListener;

@SuppressWarnings("serial")
public class StatePanel extends JPanel {

	private final static JLabel stateLabel = new JLabel("Wybierz stan:");

	private JComboBox<State> stateComboBox;
	private List<State> stateList;
	public static State stateSelected;

	public StatePanel() {
		stateList = stateMapToList(Container.getInstance().getStateList());
		stateComboBox = createComboBox();
		stateComboBox.addItemListener(new StateComboBoxListener());
		this.add(stateLabel);
		this.add(stateComboBox);
	}

	private List<State> stateMapToList(Map<Integer, State> map) {

		List<State> stateList = new ArrayList<State>();
		stateList.addAll(map.values());
		return stateList;
	}

	private JComboBox<State> createComboBox() {
		DefaultComboBoxModel model = new DefaultComboBoxModel(
				this.stateList.toArray());
		JComboBox<State> stateComboBox = new JComboBox<State>(model);
		return stateComboBox;
	}
}
