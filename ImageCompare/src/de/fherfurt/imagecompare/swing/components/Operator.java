package de.fherfurt.imagecompare.swing.components;

import javax.swing.JComboBox;

public class Operator extends JComboBox {
	
	private static String[] s = {"=", "<", ">"};
	
	public Operator() {
		super(s);
	}
	
}