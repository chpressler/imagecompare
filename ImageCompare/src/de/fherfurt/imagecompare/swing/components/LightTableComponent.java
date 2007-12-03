package de.fherfurt.imagecompare.swing.components;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.swing.controller.LightTableDropTarget;
import de.fherfurt.imagecompare.swing.layout.LightTableLayout;
import de.fherfurt.imagecompare.util.ICUtil;

public class LightTableComponent extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;

	JLayeredPane layeredPane;
    ImageComponent pic;
    ImageComponent zoomable;
    JLabel l;

    int xdiff = 0;
    int ydiff = 0;
    
	public LightTableComponent() {
		layeredPane = new JLayeredPane();
		new LightTableDropTarget(this);
		layeredPane.setLayout(new LightTableLayout());
		add(layeredPane);
        
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

	public void mousePressed(final MouseEvent e) {
		
		if(SwingUtilities.isRightMouseButton(e)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Component c = layeredPane.findComponentAt(e.getX() - layeredPane.getX(), e.getY() - layeredPane.getY());
					if (!(c instanceof JLabel)) {
						return;
					}
					pic = (ImageComponent) c;
					System.out.println("Anfang");
					ICUtil.getInstance().getHistogramData(pic.getImage());
					System.out.println("Ende");
					for(int i = 0; i < 256; i++) {
						System.out.println(i + ": " + ICUtil.getInstance().getLum().get(i));
					}
				}});
			//Context Menu
		}
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			Component c = layeredPane.findComponentAt(e.getX() - layeredPane.getX(), e.getY() - layeredPane.getY());
			if (!(c instanceof JLabel)) {
				return;
			}
			this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			pic = (ImageComponent) c;
			zoomable = (ImageComponent) c;
			layeredPane.moveToFront(pic);
			Point p1 = new Point(e.getX() - layeredPane.getX(), e.getY() - layeredPane.getY());
			Point p2 = new Point(c.getLocation());
			xdiff = p1.x - p2.x;
			ydiff = p1.y - p2.y;

			// pic.setLocation(e.getX(), e.getY());
			// pic.setSize(pic.getWidth() + 5, pic.getHeight() + 5);
			// layeredPane.add(pic, JLayeredPane.DRAG_LAYER);

			pic.setNewSize(pic.getWidth() + 10, pic.getHeight() + 10);
		}
	}
	 
	   
	public void mouseDragged(MouseEvent me) {
		if (pic == null) {
			return;
		}		
		pic.setLocation((me.getX() - (int)pic.getParent().getLocation().getX()) - xdiff, (me.getY()- (int)pic.getParent().getLocation().getY()) - ydiff);
		pic.updateUI();
//		pic.getGraphics().drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}
	 
	  
	public void mouseReleased(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if (pic == null) {
			return;
		}
		pic.setLocation((e.getX() - (int)pic.getParent().getLocation().getX()) - xdiff, (e.getY() - (int)pic.getParent().getLocation().getY()) - ydiff);
		pic.setNewSize(pic.getWidth()-10, pic.getHeight()-10);
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
		if(e.getWheelRotation() > 0) {
			zoomable.setNewSize((int) (zoomable.getWidth() / 1.2), (int) (zoomable.getHeight() / 1.2));
		}
		else if(e.getWheelRotation() < 0) {
			zoomable.setNewSize((int) (zoomable.getWidth() * 1.2), (int) (zoomable.getHeight() * 1.2));
		}
//		zoomable.setNewSize(zoomable.getWidth()+5, zoomable.getHeight()+5);
		
	}

	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	public ImageComponent getZoomable() {
		return zoomable;
	}

	public void setZoomable(ImageComponent zoomable) {
		this.zoomable = zoomable;
	}
	
	public void repaint() {
		if(getParent() != null) {
			
			layeredPane.setPreferredSize(getParent().getSize());
			System.out.println(layeredPane.getSize());
		}
	}
	
}
