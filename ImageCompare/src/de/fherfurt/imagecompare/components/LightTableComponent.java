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

import de.fherfurt.imagecompare.ResourceHandler;

public class LightTableComponent extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	JLayeredPane layeredPane;
    JPanel background;
    JLabel pic;
	
	public LightTableComponent() {
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(new FlowLayout());
        add(layeredPane);
        layeredPane.setPreferredSize( new Dimension(800, 600) );
        background = new JPanel();
//        layeredPane.add(background, 2);
        pic = new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT")));
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
//		System.out.println(c.getClass());
		if (!(c instanceof JLabel)) {
			return;
		}

//		pic.setLocation(e.getX(), e.getY());
//		pic.setSize(pic.getWidth() + 5, pic.getHeight() + 5);
//		layeredPane.add(pic, JLayeredPane.DRAG_LAYER);
	}
	 
	   
	public void mouseDragged(MouseEvent me) {
		if (pic == null) {
			return;
		}
		System.out.println(pic.getLocation());
		pic.setLocation(me.getX() - (int)pic.getParent().getLocation().getX(), me.getY() - (int)pic.getParent().getLocation().getY());
		System.out.println(pic.getLocation());
	}
	 
	  
	public void mouseReleased(MouseEvent e) {
		pic.setLocation(e.getX() - (int)pic.getParent().getLocation().getX(), e.getY() - (int)pic.getParent().getLocation().getY());
//		pic.setSize(pic.getWidth() - 5, pic.getHeight() - 5);
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
