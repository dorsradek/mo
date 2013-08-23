package org.mo.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	public MainPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//StatePanel statePanel = new StatePanel();
		// EventPanel eventPanel = new EventPanel();
		//GanttDemo1 eventPanel = new GanttDemo1("asd");
		//SimulatePanel simulatePanel = new SimulatePanel();
		JTabbedPane tabbedPane = new TabbedMainPanel();
		//statePanel.setPreferredSize(new Dimension(100, 100));
		//eventPanel.setPreferredSize(new Dimension(100, 500));
		//simulatePanel.setPreferredSize(new Dimension(100, 120));

		
		//this.add(statePanel);
		//this.add(eventPanel);
		//this.add(simulatePanel);
		this.add(tabbedPane);
		//statePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		//eventPanel.setBorder(BorderFactory.createLineBorder(Color.orange));
		//simulatePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		tabbedPane.setBorder(BorderFactory.createLineBorder(Color.RED));
		
	}
}
