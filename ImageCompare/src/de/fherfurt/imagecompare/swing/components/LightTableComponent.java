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
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.controller.LightTableDropPathTarget;
import de.fherfurt.imagecompare.swing.layout.LightTableLayout;

public class LightTableComponent extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;
	
	private static volatile LightTableComponent instance;
	
	public static synchronized LightTableComponent getInstance() {
		if(instance == null) {
			synchronized (LightTableComponent.class) {
				if(instance == null) {
					instance = new LightTableComponent();
				}
			}
		}
		return instance;
	}

	JLayeredPane layeredPane;
    ImageComponent pic;
    ImageComponent zoomable;
    JLabel l;
    private JPopupMenu popupMenu;

    int xdiff = 0;
    int ydiff = 0;
    
	private LightTableComponent() {
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
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
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

			for(ImageThumbnailComponent itc : ImageBase.getInstance().getimageList()) {
				if(itc.getPath().equals(pic.getPath())) {
					itc.setDragged(true);
					itc.repaint();
				}
			}
			
			// pic.setLocation(e.getX(), e.getY());
			// pic.setSize(pic.getWidth() + 5, pic.getHeight() + 5);
			// layeredPane.add(pic, JLayeredPane.DRAG_LAYER);

			pic.setNewSize(pic.getWidth() + 10, pic.getHeight() + 10);
			pic.setT(true);
//			pic.rotate();
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
		
		for(ImageThumbnailComponent itc : ImageBase.getInstance().getimageList()) {
			if(itc.getPath().equals(pic.getPath())) {
				itc.setDragged(false);
				itc.repaint();
			}
		}
		
		pic.setLocation((e.getX() - (int)pic.getParent().getLocation().getX()) - xdiff, (e.getY() - (int)pic.getParent().getLocation().getY()) - ydiff);
		if(SwingUtilities.isLeftMouseButton(e)) {
			pic.setNewSize(pic.getWidth()-10, pic.getHeight()-10);
		}
		pic.setT(false);
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
			JMenuItem removefromIB = new JMenuItem("remove from ImageBase");
			removefromIB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(ImageThumbnailComponent itc : ImageBase.getInstance().getimageList()) {
						if(itc.getPath().equals(p.getPath())) {
							layeredPane.remove(p);
							((JTabbedPane)layeredPane.getParent().getParent()).updateUI();
							ImageBase.getInstance().remove(itc);
						}
					}
				}});
			popupMenu.add(removefromIB);
			JMenuItem removePerm = new JMenuItem("delete File");
			removePerm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}});
			popupMenu.add(removePerm);
			popupMenu.addSeparator();
			JMenuItem hist = new JMenuItem("details");
			hist.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new DetailsFrame(p); 
				
				}});
			popupMenu.add(hist);
			popupMenu.addSeparator();
			
			JMenu rate = new JMenu("rate");
			JMenuItem r1 = new JMenuItem("1");
			JMenuItem r2 = new JMenuItem("2");
			JMenuItem r3 = new JMenuItem("3");
			JMenuItem r4 = new JMenuItem("4");
			JMenuItem r5 = new JMenuItem("5");
			rate.add(r1);
			rate.add(r2);
			rate.add(r3);
			rate.add(r4);
			rate.add(r5);
			popupMenu.add(rate);
			
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
//								file = new File("C:/temp.jpg");
//									if(file.exists()) {
//										file.delete();
//									} file.createNewFile();
								file = File.createTempFile("image", ".jpg");
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
						JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
						e1.printStackTrace();
				}
				}});
			open.add(open_ext);
			JMenuItem open_br = new JMenuItem("Browser");
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
							JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
							e1.printStackTrace();
						}
						Desktop.getDesktop().browse(uriFile);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
						e1.printStackTrace();
					}
				}});
			open.add(open_br);
			JMenuItem open_edit = new JMenuItem("Editor");
			open_edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().edit(new File(p.getPath()));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
						e1.printStackTrace();
					}
				}});
			open.add(open_edit);
			popupMenu.add(open);
			JMenu export = new JMenu("export");
			JMenuItem mail = new JMenuItem("as Email");
			mail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().mail(null); //mailto: Anhang...!!!
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
						e1.printStackTrace();
					}
				}});
			export.add(mail);
			JMenuItem pdf = new JMenuItem("as PDF");
			pdf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}});
			export.add(pdf);
			JMenuItem zip = new JMenuItem("as File");
			zip.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Anpassen SAVE_DIALOG und Dateiendung (Filter?)
					String path = ""; //FileChooser
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		            int status = fileChooser.showSaveDialog(null);
		            if (status == JFileChooser.APPROVE_OPTION) {
		            	path = fileChooser.getSelectedFile().getAbsolutePath();
		            } else {
		            	return;
		            }
					String s = p.getPath();
					if(s.startsWith("http")) {
						try {
							URL ur = new URL(s);
							String name = ur.getFile().split("/")[ur.getFile().split("/").length-1];
							File f = new File(path);
							f.createNewFile();
							FileOutputStream fos = new FileOutputStream(f);
							InputStream inputStream = ur.openConnection().getInputStream();
							int z = 0;
							while(z >= 0) {
								z = inputStream.read();
								fos.write(z);
							}
							fos.close();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
							e1.printStackTrace();
						}
					} else {
						//Original
						File f = new File(s);
						String name = f.getName();
						
						//Export
						File ef = new File(path);
						try {
							ef.createNewFile();
							
							FileInputStream fis = new FileInputStream(f);
							FileOutputStream fos = new FileOutputStream(ef);
							int ii = 0;
							while(ii != -1) {
								ii = fis.read();
								fos.write(ii);
							}
							fis.close();
							fos.close();
							
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
							e1.printStackTrace();
						}
					}
				}});
			export.add(zip);
			popupMenu.add(export);
			popupMenu.show(this, x, y);
	}
	
}
