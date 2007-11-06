package de.fherfurt.imagecompare.swing.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.controller.LightTableDropTarget;
import de.fherfurt.imagecompare.swing.layout.LightTableLayout;

public class LightTableComponent extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;

	JLayeredPane layeredPane;
    JPanel background;
    LightTableImage pic;
    LightTableImage zoomable;
    JLabel l;
    ArrayList<JLabel> al = new ArrayList<JLabel>();
    
	public LightTableComponent() {
		layeredPane = new JLayeredPane();
		new LightTableDropTarget(this);
		layeredPane.setLayout(new LightTableLayout());
		add(layeredPane);
        layeredPane.setPreferredSize( new Dimension(800, 600) );
        background = new JPanel();
//      layeredPane.add(background, 2);new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT")));
//		l = new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT")), JLayeredPane.DEFAULT_LAYER);
        
//        al.add(new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT"))));
//        al.add(new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT"))));
        
//        for(JLabel l : al) {
//        	layeredPane.add(l, JLayeredPane.DEFAULT_LAYER);
//        }
        
        layeredPane.add(new LightTableImage("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT")), JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(new LightTableImage("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("saveT")), JLayeredPane.DEFAULT_LAYER);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
	}
	
	public void addDD(JComponent c) {
		
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

	public void mousePressed(final MouseEvent e) {
		Component c = layeredPane.findComponentAt(e.getX() - layeredPane.getX(), e.getY() - layeredPane.getY());
		if (!(c instanceof JLabel)) {
			return;
		}
		pic = (LightTableImage) c;
		zoomable = (LightTableImage) c;
		layeredPane.moveToFront(pic);
		// pic.setLocation(e.getX(), e.getY());
		// pic.setSize(pic.getWidth() + 5, pic.getHeight() + 5);
		// layeredPane.add(pic, JLayeredPane.DRAG_LAYER);
	}
	 
	   
	public void mouseDragged(MouseEvent me) {
		if (pic == null) {
			return;
		}		
		pic.setLocation(me.getX() - (int)pic.getParent().getLocation().getX(), me.getY()- (int)pic.getParent().getLocation().getY());
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
//		System.out.println(e.getX() + "--" + e.getY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(zoomable == null) {
			return;
		}
//		System.out.println(zoomable.getSize());
//		zoomable.setSize(new Dimension(zoomable.getWidth()+10, zoomable.getHeight()+10));
//		
//		System.out.println(zoomable.getSize());
		zoomable.setNewSize(zoomable.getWidth()+5, zoomable.getHeight()+5);
	}

	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	public LightTableImage getZoomable() {
		return zoomable;
	}

	public void setZoomable(LightTableImage zoomable) {
		this.zoomable = zoomable;
	}
	
}
