package org.mo.app.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mo.app.Container;
import org.mo.app.Event;
import org.mo.app.gui.listeners.EventComboBoxListener;

@SuppressWarnings("serial")
public class EventLeftPanel extends JPanel {

	private final static JLabel eventLabel = new JLabel("Wybierz zdarzenia:");

	private JComboBox<Event> eventComboBox;
	private List<Event> eventList;
	public static Event eventSelected;

	public EventLeftPanel() {
		eventLabel.setSize(new Dimension(100, 100));
		eventList = eventMapToList(Container.getInstance().getEventList());
		eventComboBox = createComboBox();
		eventComboBox.addItemListener(new EventComboBoxListener());
		this.add(eventLabel);
		this.add(eventComboBox);
	}

	private List<Event> eventMapToList(Map<Integer, Event> map) {

		List<Event> eventList = new ArrayList<Event>();
		eventList.addAll(map.values());
		return eventList;
	}

	private JComboBox<Event> createComboBox() {
		DefaultComboBoxModel model = new DefaultComboBoxModel(
				this.eventList.toArray());
		JComboBox<Event> eventComboBox = new JComboBox<Event>(model);
		eventComboBox.setPrototypeDisplayValue(new Event(1,
				"text 12345678901234567890123"));
		return eventComboBox;
	}
}
