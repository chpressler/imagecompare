package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	
	private boolean filter = false;
	
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
	
	private HashMap<String, JTextField> filterMap = new HashMap<String, JTextField>();
	
	public HashMap<String, JTextField> getFilterMap() {
		return filterMap;
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
		    	filterMap.put(a.toString(), textfield);
		    }
		    ButtonGroup bg = new ButtonGroup();
		    JRadioButton on = new JRadioButton("Filter On", false);
		    on.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(((JRadioButton) e.getSource()).isSelected()) {
						filter = true;
					}
				}});
		    JRadioButton off = new JRadioButton("Filter Off", true);
		    off.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(((JRadioButton) e.getSource()).isSelected()) {
						filter = false;
					}
				}});
		    bg.add(on);
		    bg.add(off);
		    getContentPane().add(on);
	    	getContentPane().add(off);
		    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    pack();
	}
	
	public boolean isFilterOn() {
		return filter;
	}
}

class Operator extends JComboBox {
	
	private static String[] s = {"=", "<", ">"};
	
	public Operator() {
		super(s);
	}
	
}
