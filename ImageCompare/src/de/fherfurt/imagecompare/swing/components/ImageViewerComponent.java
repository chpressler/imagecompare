package de.fherfurt.imagecompare.swing.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.swing.controller.ImageViewerDropPathTarget;

public class ImageViewerComponent extends JPanel implements ImageViewerListener {

	private static final long serialVersionUID = 1L;
	
	private int width = 0;
	
	private int height = 0;
	
	private BufferedImage image;
	
//	private JLayeredPane layeredPane;
	
	private JPanel imagePanel;
	
	private ImageComponent imageLabel;
	
	private JScrollPane jsp;
	
	private Graphics2D g;
	
	private JPopupMenu popupMenu;
	
	public ImageViewerComponent() {
		new ImageViewerDropPathTarget(this);
		imagePanel = new JPanel();
		imageLabel = new ImageComponent();
		imagePanel.setPreferredSize(new Dimension(600, 380));
//		imageLabel.setPreferredSize(new Dimension(301, 601));
		jsp = new JScrollPane(imageLabel);
		jsp.setPreferredSize(new Dimension(600, 380));
		
		jsp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				((ImageViewerComponents) ((Component) e.getSource()).getParent().getParent().getParent()).scrolledVertical(e.getValue());
			}});
		jsp.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				((ImageViewerComponents) ((Component) e.getSource()).getParent().getParent().getParent()).scrolledHorizontal(e.getValue());
			}});
		
//		imagepanel.setPreferredSize(new Dimension(500, 500));
//		layeredPane = new JLayeredPane();
//		layeredPane.setLayout(new FlowLayout());
		
		imageLabel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() > 0) {
					((ImageViewerComponents) ((ImageComponent) e.getSource()).getParent().getParent().getParent().getParent()).zoomedOut();
//					width /= 1.15;
//					height /= 1.15;
//
				}
				else if(e.getWheelRotation() < 0) {
					((ImageViewerComponents) ((ImageComponent) e.getSource()).getParent().getParent().getParent().getParent()).zoomedIn();
//					width *= 1.15;
//					height *= 1.15;
				}
				
//				imageLabel.setBounds((jsp.getWidth() - width)/2, (jsp.getHeight() - height)/2, width, height);
//				
//				new Thread(new Runnable() {
//					public void run() {
//						if (((Graphics2D) imageLabel.getGraphics()) != null) {
////							imageLabel.setBounds((jsp.getWidth() - width)/2, (jsp.getHeight() - height)/2, width, height);
//							imageLabel.setPreferredSize(new Dimension(width, height));
//							g = ((Graphics2D) imageLabel.getGraphics());
//									g.drawImage(image, 0, 0, width,
//											height, 0, 0, image
//													.getWidth(), image
//													.getHeight(), null);
//									
//						}
//					}}).start();
				
//				imageLabel.setNewSize(width, height);
//				jsp.repaint();
				
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
					buildMenu(e.getX() - ((JComponent) e.getSource()).getX(), e.getY() - ((JComponent) e.getSource()).getY());
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
			}});
		
//		layeredPane.add(imagePanel, (Integer) (JLayeredPane.DEFAULT_LAYER) + 2);
		
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
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

	public void setImage(final BufferedImage image, String p) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		imageLabel.setImage(image);
		imageLabel.setPath(p);
		imageLabel.setNewSize(width, height);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), p));
//		jsp.repaint();
//		imagePanel.setPreferredSize(new Dimension(height, width));
//		imageLabel.paintComponent(image.getGraphics());
	}
	
//	public void setImage(ImageComponent imageLabel) {
//		this.imageLabel = imageLabel;
//		imageLabel.updateUI();
//	}

	public Graphics2D getG() {
		return g;
	}

	public void setG(Graphics2D g) {
		this.g = g;
	}

	@Override
	public void scrolledHorizontal(int i) {
		jsp.getHorizontalScrollBar().setValue(i);
		jsp.updateUI();
	}
	
	@Override
	public void scrolledVertical(int i) {
		jsp.getVerticalScrollBar().setValue(i);
		jsp.updateUI();
	}

	@Override
	public void zoomedIn() {
		width *= 1.15;
		height *= 1.15;
		
		imageLabel.setBounds((jsp.getWidth() - width)/2, (jsp.getHeight() - height)/2, width, height);
		
		imageLabel.setNewSize(width, height);
		jsp.repaint();
	}

	@Override
	public void zoomedOut() {
		width /= 1.15;
		height /= 1.15;
		
		imageLabel.setBounds((jsp.getWidth() - width)/2, (jsp.getHeight() - height)/2, width, height);
		
		imageLabel.setNewSize(width, height);
		jsp.repaint();
	}

//	public JLayeredPane getLayeredPane() {
//		return layeredPane;
//	}
//
//	public void setLayeredPane(JLayeredPane layeredPane) {
//		this.layeredPane = layeredPane;
//	}
	
	@Override
	public void paint( Graphics g ) {
		setPreferredSize(new Dimension(getParent().getWidth()/2-10, getParent().getHeight()/2-10));
		super.paint(g);
        this.revalidate();
    }
	
	public void buildMenu(int x, int y) {
		popupMenu = new JPopupMenu();
		JMenuItem remove = new JMenuItem("remove");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		popupMenu.add(remove);
		JMenuItem or_size = new JMenuItem("set to original Size");
		or_size.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imageLabel.setNewSize(image.getWidth(), image.getHeight());
			}
		});
		popupMenu.add(or_size);
		JMenuItem open = new JMenuItem("open Image");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fc = new JFileChooser();
					int ret = fc.showOpenDialog(null);
//					fc.addChoosableFileFilter(new FileFilter() {
//						public boolean accept(File f) {
//							if (f.isDirectory())
//								return true;
//							return f.getName().toLowerCase().endsWith(".jpg");
//						}
//
//						public String getDescription() {
//							return "JPG Images";
//						}
//					});
					fc.setMultiSelectionEnabled(false);
					//fc.setCurrentDirectory(new File("resources/"));
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					if (ret == JFileChooser.APPROVE_OPTION) {
						image = ImageIO.read(new File(fc.getSelectedFile().getAbsolutePath()));
						setImage(image, fc.getSelectedFile().getAbsolutePath());
//						setImage(new ImageComponent(image));
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
		});
		popupMenu.add(open);
		popupMenu.show(this, x, y);
	}

}
