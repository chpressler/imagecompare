package de.fherfurt.imagecompare.swing.components;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static volatile StatusBar instance;
	
	private JProgressBar pb;
	
	private JLabel text;
	
	public static synchronized StatusBar getInstance() {
		if(instance == null) {
			synchronized (StatusBar.class) {
				if(instance == null) {
					instance = new StatusBar();
				}
			}
		}
		return instance;
	}
	
	private StatusBar() {
		text = new JLabel("");
		pb = new JProgressBar();
//		setLayout(new FlowLayout());
		add(pb);
		add(text);
	}

	public void activateProgressBar() {
		pb.setIndeterminate(true);
	}

	public void deactivateProgressBar() {
		pb.setIndeterminate(false);
	}
	
	public void setStatusText(String s) {
		text.setText(s);
	}
	
}
