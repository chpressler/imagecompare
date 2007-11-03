package de.fherfurt.imagecompare.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class LightTableComponent extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	JLayeredPane layeredPane;
    JPanel background;
    JLabel pic;
	
	public LightTableComponent() {
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(new FlowLayout());
        add(layeredPane);
        layeredPane.setPreferredSize( new Dimension(200, 200) );
        background = new JPanel();
//        layeredPane.add(background, 2);
        pic = new JLabel(new ImageIcon("test.jpg"));
        layeredPane.add(pic, JLayeredPane.DEFAULT_LAYER);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		Component c = layeredPane.findComponentAt(e.getX(), e.getY());

		if (!(c instanceof JLabel)) {
			return;
		}

		pic.setLocation(e.getX(), e.getY());
		pic.setSize(pic.getWidth() + 5, pic.getHeight() + 5);
//		layeredPane.add(pic, JLayeredPane.DRAG_LAYER);
	}
	 
	   
	public void mouseDragged(MouseEvent me) {
		if (pic == null) {
			return;
		}
		pic.setLocation(me.getX(), me.getY());
	}
	 
	  
	public void mouseReleased(MouseEvent e) {
		pic.setLocation(e.getX(), e.getY());
		pic.setSize(pic.getWidth() - 5, pic.getHeight() - 5);
//		layeredPane.add(pic, JLayeredPane.DEFAULT_LAYER);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
