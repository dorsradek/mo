package org.mo.app.gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.mo.app.Event;

@SuppressWarnings("serial")
public class EventRightPanel extends JPanel {

	public static JList<Event> selectedStatesList;
	public static DefaultListModel model;

	public EventRightPanel() {
		setSelectedStatesList(createList());
		this.add(getSelectedStatesList());

	}

	private JList<Event> createList() {
		model = new DefaultListModel();
		JList<Event> selectedStatesList = new JList<Event>(model);
		ArrayList<Event> selectedStatesArrayList = new ArrayList<Event>();

		selectedStatesList.setPreferredSize(new Dimension(200, 200));
		return selectedStatesList;
	}

	public JList<Event> getSelectedStatesList() {
		return selectedStatesList;
	}

	public void setSelectedStatesList(JList<Event> selectedStatesList) {
		this.selectedStatesList = selectedStatesList;

	}
}
