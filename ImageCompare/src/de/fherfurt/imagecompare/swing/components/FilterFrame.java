package de.fherfurt.imagecompare.swing.components;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import de.fherfurt.imagecompare.Attributes;

public class FilterFrame extends JFrame {
	
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
	
	private HashMap<Operator, JTextField> filterMap = new HashMap<Operator, JTextField>();
	
	public HashMap<Operator, JTextField> getFilterMap() {
		return filterMap;
	}
	
	public FilterFrame() {
		super("FilterOptions");
		 getContentPane().setLayout(new GridLayout(0, 3));
		    for(Attributes a : Attributes.values()) {
		    	JTextField textfield = new JTextField();
		    	textfield.setText("");
		    	textfield.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						
					}
					@Override
					public void keyReleased(KeyEvent e) {
						
					}
					@Override
					public void keyTyped(KeyEvent e) {
						PreviewThumbnailComponent.getInstance().getLayout().layoutContainer(PreviewThumbnailComponent.getInstance());
						PreviewThumbnailComponent.getInstance().updateUI();
					}});
		    	Operator o = new Operator();
		    	o.setName(a.toString());
		    	o.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						PreviewThumbnailComponent.getInstance().getLayout().layoutContainer(PreviewThumbnailComponent.getInstance());
						PreviewThumbnailComponent.getInstance().updateUI();
					}});
		    	textfield.setName(a.toString());
		    	getContentPane().add(new JLabel(a.toString()));
		    	getContentPane().add(o);
		    	getContentPane().add(textfield);
		    	filterMap.put(o, textfield);
		    }
		    ButtonGroup bg = new ButtonGroup();
		    JRadioButton on = new JRadioButton("Filter On", false);
		    on.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(((JRadioButton) e.getSource()).isSelected()) {
						filter = true;
						PreviewThumbnailComponent.getInstance().getLayout().layoutContainer(PreviewThumbnailComponent.getInstance());
						PreviewThumbnailComponent.getInstance().updateUI();
					}
				}});
		    JRadioButton off = new JRadioButton("Filter Off", true);
		    off.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(((JRadioButton) e.getSource()).isSelected()) {
						filter = false;
						PreviewThumbnailComponent.getInstance().getLayout().layoutContainer(PreviewThumbnailComponent.getInstance());
						PreviewThumbnailComponent.getInstance().updateUI();
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