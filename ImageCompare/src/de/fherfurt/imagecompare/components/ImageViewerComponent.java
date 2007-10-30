package de.fherfurt.imagecompare.components;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ImageViewerComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int width = 0;
	
	private int height = 0;
	
	private BufferedImage image;
	
//	private JLayeredPane layeredPane;
	
	private JPanel imagePanel;
	
	private JLabel imageLabel;
	
	private JScrollPane jsp;
	
	private Graphics2D g;
	
	public ImageViewerComponent() {
		imagePanel = new JPanel();
		imageLabel = new JLabel();
		imagePanel.setPreferredSize(new Dimension(301, 601));
//		imageLabel.setPreferredSize(new Dimension(301, 601));
		jsp = new JScrollPane(imageLabel);
		jsp.setPreferredSize(new Dimension(300, 600));
	
//		imagepanel.setPreferredSize(new Dimension(500, 500));
//		layeredPane = new JLayeredPane();
//		layeredPane.setLayout(new FlowLayout());
		
		imageLabel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() > 0) {
					width /= 1.15;
					height /= 1.15;
				}
				else if(e.getWheelRotation() < 0) {
					width *= 1.15;
					height *= 1.15;
				}
				imageLabel.setBounds((jsp.getWidth() - width)/2, (jsp.getHeight() - height)/2, width, height);
				jsp.repaint();

				if (((Graphics2D) imageLabel.getGraphics()) != null) {
					imageLabel.setPreferredSize(new Dimension(width, height));
					g = ((Graphics2D) imageLabel.getGraphics());
							g.drawImage(image, 0, 0, width,
									height, 0, 0, image
											.getWidth(), image
											.getHeight(), null);
				}
//				SwingUtilities.updateComponentTreeUI(jsp);
//				if(image != null) {	
//					((Graphics2D) ((JLabel) e.getSource()).getGraphics()).drawImage(image, 0, 0, width,
//							height, 0, 0, image
//							.getWidth(), image
//							.getHeight(), null);
////					((JLabel) e.getSource()).updateUI();
//				}
			}});
		
		imageLabel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					try {
						JFileChooser fc = new JFileChooser();
						int ret = fc.showOpenDialog(null);
//						fc.addChoosableFileFilter(new FileFilter() {
//							public boolean accept(File f) {
//								if (f.isDirectory())
//									return true;
//								return f.getName().toLowerCase().endsWith(".jpg");
//							}
//
//							public String getDescription() {
//								return "JPG Images";
//							}
//						});
						fc.setMultiSelectionEnabled(false);
						//fc.setCurrentDirectory(new File("resources/"));
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						if (ret == JFileChooser.APPROVE_OPTION) {
							image = ImageIO.read(new File(fc.getSelectedFile().getAbsolutePath()));
							setImage(image);
							System.out.println(fc.getSelectedFile().getAbsolutePath());
						}
						if (ret == JFileChooser.CANCEL_OPTION) {
							
						}

					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(new JDialog(), e1.getMessage(),
								"Exception", JOptionPane.ERROR_MESSAGE);
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
				
			}});
		
//		layeredPane.add(imagePanel, (Integer) (JLayeredPane.DEFAULT_LAYER) + 2);
		
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bild"));
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(jsp);
		
//		new Thread(new Runnable() {
//			public void run() {
//				while (true) {
//					if(image != null) {
//						try {
//							if (((Graphics2D) imagePanel.getGraphics()) != null) {
//								((Graphics2D) imagePanel.getGraphics())
//										.drawImage(image, 0, 0, width,
//												height, 0, 0, image
//														.getWidth(), image
//														.getHeight(), null);
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}}).start();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(final BufferedImage image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public Graphics2D getG() {
		return g;
	}

	public void setG(Graphics2D g) {
		this.g = g;
	}

//	public JLayeredPane getLayeredPane() {
//		return layeredPane;
//	}
//
//	public void setLayeredPane(JLayeredPane layeredPane) {
//		this.layeredPane = layeredPane;
//	}

}
