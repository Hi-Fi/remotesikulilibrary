package com.github.hi_fi.testapp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TestSwingApp {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel textPanel;
	private JTextField textField;
	
	public TestSwingApp(String title) {
		prepareGUI(title);
	}

	public static void main(String[] args) {
		String title = "Test application for Remote Sikuli Library";
		if (args.length > 0) {
			title = args[0];
		}
		
		TestSwingApp testSwingApp = new TestSwingApp(title);
		testSwingApp.showEventDemo();
	}

	private void prepareGUI(String title) {
		mainFrame = new JFrame(title);
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

		okButton.addMouseListener(this.getClickListener("OK"));
		submitButton.addMouseListener(this.getClickListener("Submit"));
		cancelButton.addMouseListener(this.getClickListener("Cancel"));
		
		controlPanel.add(okButton);
		controlPanel.add(submitButton);
		controlPanel.add(cancelButton);

		textPanel.add(label);
		textPanel.add(textField);
		
		mainFrame.setVisible(true);
	}
	
	private MouseAdapter getClickListener(final String buttonName) {
		return new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		    	String times = "once";
		    	String button = "left";
		    	if (SwingUtilities.isRightMouseButton(e)) {
		    		button = "right";
		    	}
		        if(e.getClickCount()==2){
		            times = "twice";
		        }
		        statusLabel.setText(buttonName+" Button clicked "+times+" with "+button+" button.");
		    }
		};
	}
}
