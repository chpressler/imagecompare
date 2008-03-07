package de.fherfurt.imagecompare.swing.components;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.fherfurt.imagecompare.ImageBase;

public class StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static volatile StatusBar instance;
	
	private JProgressBar pb;
	
	private JLabel text;
	
	private JSlider slider;
	
	private JComboBox size;
	
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
		String[] sizes = {"all", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "20", "30", "50", "60", "70", "80", "90", "100"};
		text = new JLabel("");
		pb = new JProgressBar();
		slider = new JSlider();
		size = new JComboBox(sizes);
		size.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				PreviewThumbnailComponent.getInstance().getLayout().layoutContainer(PreviewThumbnailComponent.getInstance());
				PreviewThumbnailComponent.getInstance().updateUI();
				PreviewThumbnailComponent.getInstance().repaint();
			}});
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
//				System.out.println(((JSlider) e.getSource()).getValue());
				for(ThumbnailSizeListener tsl : listeners) {
					tsl.thumbnailSizChanged();
				}
			}});
		slider.setValue(9);
		setLayout(new GridLayout(0, 4));
		add(slider);
		add(size);
		add(pb);
		add(text);
	}
	
	public int getImageBaseSize() {
		if(size.getSelectedItem().toString().equals("all")) {
			return ImageBase.getInstance().getimageList().size();
		} else {
			return Integer.parseInt(size.getSelectedItem().toString());
		}
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
