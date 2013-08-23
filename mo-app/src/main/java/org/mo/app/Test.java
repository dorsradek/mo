package org.mo.app;

import javax.swing.JFrame;

import org.mo.app.gui.MainPanel;

public class Test {

	private static void createAndShowGUI() {

		JFrame frame = new JFrame("DES");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new MainPanel());

		frame.pack();
		frame.setVisible(true);
		frame.setSize(800, 500);
		frame.setResizable(false);
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

}
