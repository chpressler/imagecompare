package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static volatile StatusBar instance;
	
	private JProgressBar pb;
	
	private JLabel text;
	
	JSlider slider;
	
	ArrayList<ThumbnailSizeListener> listeners = new ArrayList<ThumbnailSizeListener>();
	
	public void addThumbnailSizeListener(ThumbnailSizeListener tsl) {
		listeners.add(tsl);
	}
	
	public void removeThumbnailSizeListener(ThumbnailSizeListener tsl) {
		listeners.remove(tsl);
	}
	
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
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
//				System.out.println(((JSlider) e.getSource()).getValue());
				for(ThumbnailSizeListener tsl : listeners) {
					tsl.thumbnailSizChanged();
				}
			}});
		slider.setValue(9);
		setLayout(new GridLayout(0, 3));
		add(slider);
		add(pb);
		add(text);
	}
	
	public int getSliderValue() {
		if((slider.getValue() * 10) < 30) {
			return 30;
		} 
		return slider.getValue() * 10;
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
	
//	@Override
//	public void paint(Graphics g) {
//		if (getParent() != null) {
//			System.out.println((int) getParent().getSize().getWidth() + "" + getParent());
////			setSize( (int) getParent().getSize().getWidth(), getHeight() );
//			setPreferredSize(new Dimension((int) getParent().getSize().getWidth(), getHeight()));
//		}
//		super.paint(g);
//	}
	
}
