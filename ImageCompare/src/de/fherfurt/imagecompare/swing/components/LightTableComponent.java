package de.fherfurt.imagecompare.swing.components;

import ij.ImageJ;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.swing.controller.LightTableDropPathTarget;
import de.fherfurt.imagecompare.swing.layout.LightTableLayout;

public class LightTableComponent extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;

	JLayeredPane layeredPane;
    ImageComponent pic;
    ImageComponent zoomable;
    JLabel l;
    private JPopupMenu popupMenu;

    int xdiff = 0;
    int ydiff = 0;
    
	public LightTableComponent() {
		setBackground(Color.black);
		layeredPane = new JLayeredPane();
		new LightTableDropPathTarget(this);
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
					zoomable = (ImageComponent) c;
					layeredPane.moveToFront(pic);
					Point p1 = new Point(e.getX() - layeredPane.getX(), e.getY() - layeredPane.getY());
					Point p2 = new Point(c.getLocation());
					xdiff = p1.x - p2.x;
					ydiff = p1.y - p2.y;
					buildMenu(e.getX(), e.getY(), pic);
				}});
				
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
			
			pic.rotate();
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
		if(SwingUtilities.isLeftMouseButton(e)) {
			pic.setNewSize(pic.getWidth()-10, pic.getHeight()-10);
		}
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
	
	public void buildMenu(int x, int y, final ImageComponent p) { 
			popupMenu = new JPopupMenu();
			JMenuItem remove = new JMenuItem("remove");
			remove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					layeredPane.remove(p);
					((JTabbedPane)layeredPane.getParent().getParent()).updateUI();
				}});
			popupMenu.add(remove);
			JMenuItem hist = new JMenuItem("details");
			hist.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new DetailsFrame(p); 
				
				}});
			popupMenu.add(hist);
			popupMenu.addSeparator();
			
			JMenu open = new JMenu("open");
			
			JMenuItem open_ij = new JMenuItem("ImageJ");
			open_ij.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ImageJ.main(new String[]{p.getPath()});
				}});
			open.add(open_ij);
			JMenuItem open_ext = new JMenuItem("Standard");
			open_ext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						File file;
						if(p.getPath().startsWith("http")) {
								URLConnection con = null;
								URL url = new URL(p.getPath());
								con = url.openConnection();
								con.setDoOutput(true);
								file = new File("C:/temp.jpg");
									if(file.exists()) {
										file.delete();
									} file.createNewFile();
								FileOutputStream fos = new FileOutputStream(file);
								int xz = 0;
								while(xz >= 0) {
									xz = con.getInputStream().read();
									fos.write(xz);
								}
							fos.close();
							System.out.println(file.getAbsolutePath());
						} else {
							file = new File(p.getPath());
						}
						Desktop.getDesktop().open(file);
					} catch (Exception e1) {
						e1.printStackTrace();
				}
				}});
			open.add(open_ext);
			JMenuItem open_br = new JMenuItem("browse");
			open_br.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						System.out.println(new File(p.getPath()).toURI());
						
						String test = "///" + new File(p.getPath()).toURI();
						URI uriFile = null;
						try {
							uriFile = new URI("file", test, null);
							System.out.println(uriFile);
						} catch (URISyntaxException e1) {
							e1.printStackTrace();
						}
						Desktop.getDesktop().browse(uriFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}});
			open.add(open_br);
			JMenuItem open_edit = new JMenuItem("edit");
			open_edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().edit(new File(p.getPath()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}});
			open.add(open_edit);
			popupMenu.add(open);
			JMenuItem mail = new JMenuItem("mail");
			mail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().mail(null); //mailto: Anhang...!!!
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}});
			popupMenu.add(mail);
			popupMenu.show(this, x, y);
	}
	
}
