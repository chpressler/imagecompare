package com.blogofbug.tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;

import de.fherfurt.imagecompare.swing.components.ImageComponent;
import de.fherfurt.imagecompare.util.ICUtil;

public class ChartTest extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public ChartTest(ImageComponent ic) {
		getContentPane().add(new MyChart(ic));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(270, 250);
	}
	
	public static void main(String[] args) {
//		new ChartTest();
	}

}

class MyChart extends JComponent {
	
	private static final long serialVersionUID = 1L;

	ButtonGroup group;
	JRadioButton rb;
	JRadioButton gb;
	JRadioButton bb;
	JRadioButton lb;
	
	ImageComponent ic;
	
	public MyChart(ImageComponent ic) {
		this.ic = ic;
		setPreferredSize(new Dimension(256, 200));
		setLayout(new FlowLayout());
		group = new ButtonGroup();
		rb = new JRadioButton("r");
		gb = new JRadioButton("g");
		bb = new JRadioButton("b");
		lb = new JRadioButton("l", true);
		group.add(rb);
		group.add(gb);
		group.add(bb);
		group.add(lb);
		rb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		gb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		bb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		lb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		add(rb);
		add(gb);
		add(bb);
		add(lb);
	}
	
	@Override
	public void paint(Graphics g) {
		g.clearRect(1, 1, 255, 199);
		int x = 0;
		for(int i = 0; i < 255; i++) {
			int val = 0;
			if(rb.isSelected()) {
				val = ic.getRed().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 200-(val/100), 1, val/100);
				g.setColor(new Color(i, 0, 0));
			}
			else if(gb.isSelected()) {
				val = ic.getGreen().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 200-(val/100), 1, val/100);
				g.setColor(new Color(0, i, 0));
			}
			else if(bb.isSelected()) {
				val = ic.getBlue().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 200-(val/100), 1, val/100);
				g.setColor(new Color(0, 0, i));
			}
			else {
				val = ic.getLum().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 200-(val/100), 1, val/100);
				g.setColor(new Color(i, i, i));
			}
			g.fillRect(x, 200-(val/100), 1, val/100);
			g.fillRect(x, 200, 1, 10);
			x++;
		}
		g.setColor(Color.black);
		g.drawRect(0, 200, 256, 10);
		g.drawRect(0, 0, 256, 200);
		super.paint(g);
	}
	
}