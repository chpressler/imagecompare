package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.swing.actions.RemoveSelectedAction;

public class ImageThumbnailComponent extends JComponent implements ThumbnailSizeListener, Comparable<ImageThumbnailComponent> {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private String path;
	
	private int imagewidth, imageheight;
	
	private int defsize;
	
	private boolean selected = false;
	
	private JPopupMenu popupMenu;
	
	public ImageThumbnailComponent(BufferedImage image, String path) {
		StatusBar.getInstance().addThumbnailSizeListener(this);
		defsize = StatusBar.getInstance().getSliderValue();
		this.image = image;
		this.path = path;
		setPreferredSize(new Dimension(defsize, defsize));
		try {
			if (image.getWidth() > image.getHeight()) {
				imageheight = (defsize - 2) * image.getHeight()
						/ image.getWidth();
				imagewidth = (defsize - 2);
				// setPreferredSize(new Dimension(80, 80 * image.getHeight() /
				// image.getWidth()));
			} else if (image.getWidth() < image.getHeight()) {
				imagewidth = (defsize - 2) * image.getWidth()
						/ image.getHeight();
				imageheight = (defsize - 2);
				// setPreferredSize(new Dimension(80 * image.getWidth() /
				// image.getHeight(), 80));
			} else {
				imagewidth = defsize - 2;
				imageheight = defsize - 2;
				// setPreferredSize(new Dimension(80, 80));
			}
		} catch (Exception e) {

		}
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(	SwingUtilities.isLeftMouseButton(e)) {
					String p = ((ImageThumbnailComponent) e.getSource()).getPath();
					if(e.getClickCount() == 2) {
						try {
							File file;
							if(p.startsWith("http")) {
									URLConnection con = null;
									URL url = new URL(p);
									con = url.openConnection();
									con.setDoOutput(true);
//									file = new File("C:/temp.jpg");
//										if(file.exists()) {
//											file.delete();
//										} file.createNewFile();
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
								file = new File(p);
							}
							Desktop.getDesktop().open(file);
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
					} else {
						selected = !selected;
						repaint(); 
					}
				} else if(SwingUtilities.isRightMouseButton(e)) {
					if(!selected) {
						selected = true;
					}
					buildMenu(e.getX(), e.getY());
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
		});
	}
	
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public void paint(Graphics g) {
		if (image != null) {
			if(selected) {
				g.setColor(Color.red);
				g.drawRect(0, 0, defsize-1, defsize-1);
				g.drawRect(1, 1, defsize-3, defsize-3);
				g.drawImage(image, ((defsize - imagewidth) / 2) + 1, ((defsize - imageheight) / 2) + 1, imagewidth-2, imageheight-2, this);
			} else {
				g.setColor(Color.gray);
				g.drawRect(0, 0, defsize-1, defsize-1);
				g.drawImage(image, (defsize - imagewidth) / 2, (defsize - imageheight) / 2, imagewidth, imageheight, this);
			}
		} else {
			if(selected) {
				g.setColor(Color.red);
				g.drawRect(0, 0, defsize-1, defsize-1);
				g.drawRect(1, 1, defsize-3, defsize-3);
				g.drawLine(0, 0, defsize-1, defsize-1);
			} else {
				g.setColor(Color.gray);
				g.drawRect(0, 0, defsize-1, defsize-1);
				g.setColor(Color.red);
				g.drawLine(0, 0, defsize-1, defsize-1);
			}
		}
		super.paint(g);
	}

	public String getPath() {
		return path;
	}

	@Override
	public void thumbnailSizChanged() {
		try {
			defsize = StatusBar.getInstance().getSliderValue();
			setPreferredSize(new Dimension(defsize, defsize));
			if (image.getWidth() > image.getHeight()) {
				imageheight = (defsize - 2) * image.getHeight()
						/ image.getWidth();
				imagewidth = (defsize - 2);
			} else if (image.getWidth() < image.getHeight()) {
				imagewidth = (defsize - 2) * image.getWidth()
						/ image.getHeight();
				imageheight = (defsize - 2);
			} else {
				imagewidth = defsize - 2;
				imageheight = defsize - 2;
			}
		} catch (Exception e) {

		}
		validate();
		updateUI();
		repaint();
	} 
	
	public boolean isSelected() {
		return selected;
	}
	
	private void buildMenu(int x, int y) { 
		final ImageThumbnailComponent parent = this;
		popupMenu = new JPopupMenu();
		JMenuItem rem = new JMenuItem("remove selected from ImageBase");
		rem.addActionListener(new RemoveSelectedAction());
		popupMenu.add(rem);
		popupMenu.show(this, x, y);
}

	@Override
	public int compareTo(ImageThumbnailComponent o) {
		return 0;
	}
	
}
