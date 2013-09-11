package org.mo.app.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	public MainPanel() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JTabbedPane tabbedPane = new TabbedMainPanel();
		this.add(tabbedPane);
	}
}
