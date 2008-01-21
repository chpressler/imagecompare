package de.fherfurt.imagecompare.swing.components;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class ImageThumbnailComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private String path;
	
	public ImageThumbnailComponent(BufferedImage image, String path) {
		this.image = image;
		this.path = path;
		if(image.getWidth() > image.getHeight()) {
			setPreferredSize(new Dimension(80, 80 * image.getHeight() / image.getWidth()));
		}
		else if(image.getWidth() < image.getHeight()) {
			setPreferredSize(new Dimension(80 * image.getWidth() / image.getHeight(), 80));
		}
		else {
			setPreferredSize(new Dimension(80, 80));
		}
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(	SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
					String p = ((ImageThumbnailComponent) e.getSource()).getPath();
					try {
						File file;
						if(p.startsWith("http")) {
								URLConnection con = null;
								URL url = new URL(p);
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
							file = new File(p);
						}
						Desktop.getDesktop().open(file);
					} catch (Exception e1) {
						e1.printStackTrace();
				}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public BufferedImage getImage() {
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public String getPath() {
		return path;
	} 
	
}
