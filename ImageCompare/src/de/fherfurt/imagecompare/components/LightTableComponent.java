package de.fherfurt.imagecompare.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

	private static final long serialVersionUID = 1L;

	JLayeredPane layeredPane;
    JPanel background;
    JLabel pic;
    JLabel l;
	
	public LightTableComponent() {
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(new FlowLayout());
        add(layeredPane);
        layeredPane.setPreferredSize( new Dimension(800, 600) );
        background = new JPanel();
//      layeredPane.add(background, 2);new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT")));
//		l = new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT")), JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT"))));
//        layeredPane.add(new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT"))), JLayeredPane.DEFAULT_LAYER);
        
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
		System.out.println(c.getClass());
		if (!(c instanceof JLabel)) {
			return;
		}
		pic = (JLabel) c;
//		pic.setLocation(e.getX(), e.getY());
//		pic.setSize(pic.getWidth() + 5, pic.getHeight() + 5);
//		layeredPane.add(pic, JLayeredPane.DRAG_LAYER);
	}
	 
	   
	public void mouseDragged(MouseEvent me) {
		if (pic == null) {
			return;
		}
		pic.setLocation(me.getX() - (int)pic.getParent().getLocation().getX(), me.getY() - (int)pic.getParent().getLocation().getY());
	}
	 
	  
	public void mouseReleased(MouseEvent e) {
		if (pic == null) {
			return;
		}
		pic.setLocation(e.getX() - (int)pic.getParent().getLocation().getX(), e.getY() - (int)pic.getParent().getLocation().getY());
		pic = null;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println(e.getX() + " - " + e.getY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
