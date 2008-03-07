package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fherfurt.imagecompare.Attributes;

public class FilterComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField string = new JTextField(10);
	
	private JTextField a = new JTextField(5);
	
	private JTextField f = new JTextField(5);
	
	public FilterComponent() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.white, Color.lightGray), "Filter"));
		setBackground(null);
	    setOpaque(false);
		
	    JButton b = new JButton("options");
	    b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterFrame.getInstance().setVisible(true);
			}});
	    add(b);
	}

}

class FilterFrame extends JFrame {
	
	private static volatile FilterFrame instance;
	
	public static synchronized FilterFrame getInstance() {
		if(instance == null) {
			synchronized (FilterFrame.class) {
				if(instance == null) {
					instance = new FilterFrame();
				}
			}
		}
		return instance;
	}
	
	public FilterFrame() {
		super("FilterOptions");
		 getContentPane().setLayout(new GridLayout(0, 3));
		    for(Attributes a : Attributes.values()) {
		    	JTextField textfield = new JTextField();
		    	Operator o = new Operator();
		    	o.setName(a.toString());
		    	textfield.setName(a.toString());
		    	getContentPane().add(new JLabel(a.toString()));
		    	getContentPane().add(o);
		    	getContentPane().add(textfield);
		    }
		    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    pack();
	}
}

class Operator extends JComboBox {
	
	private static String[] s = {"=", "<", ">"};
	
	public Operator() {
		super(s);
	}
	
}
