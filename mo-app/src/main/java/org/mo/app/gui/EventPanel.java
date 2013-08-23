package org.mo.app.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EventPanel extends JPanel {

	public EventPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		EventLeftPanel eventLeftPanel = new EventLeftPanel();
		EventCenterPanel eventCenterPanel = new EventCenterPanel();
		EventRightPanel eventRightPanel = new EventRightPanel();

		eventLeftPanel.setPreferredSize(new Dimension(100, 100));
		eventCenterPanel.setPreferredSize(new Dimension(50, 100));
		eventRightPanel.setPreferredSize(new Dimension(100, 120));

		this.add(eventLeftPanel);
		this.add(eventCenterPanel);
		this.add(eventRightPanel);
		eventLeftPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		eventCenterPanel
				.setBorder(BorderFactory.createLineBorder(Color.orange));
		eventRightPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
	}

}
