package com.github.hi_fi.testapp;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestSwingApp {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel textPanel;
	private JTextField textField;

	public TestSwingApp() {
		prepareGUI();
	}

	public static void main(String[] args) {
		System.out.println("Starting test app");
		TestSwingApp testSwingApp = new TestSwingApp();
		testSwingApp.showEventDemo();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Test application for Remote Sikuli Library");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(4, 1));

		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);

		statusLabel.setSize(350, 100);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		
		textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.add(textPanel);
		mainFrame.setVisible(true);
		
	}

	private void showEventDemo() {
		headerLabel.setText("Control in action: Button");

		JButton okButton = new JButton("OK");
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		JLabel label = new JLabel("Test field: ", JLabel.RIGHT);
		textField = new JTextField(20);

		okButton.setActionCommand("OK");
		submitButton.setActionCommand("Submit");
		cancelButton.setActionCommand("Cancel");

		okButton.addActionListener(new ButtonClickListener());
		submitButton.addActionListener(new ButtonClickListener());
		cancelButton.addActionListener(new ButtonClickListener());
		
		controlPanel.add(okButton);
		controlPanel.add(submitButton);
		controlPanel.add(cancelButton);

		textPanel.add(label);
		textPanel.add(textField);
		
		mainFrame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("OK")) {
				statusLabel.setText("Ok Button clicked.");
			} else if (command.equals("Submit")) {
				statusLabel.setText("Submit Button clicked.");
			} else {
				statusLabel.setText("Cancel Button clicked.");
			}
		}
	}
}
