package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
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
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.IImport;
import de.fherfurt.imagecompare.ImageAnalyser;
import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ImportDBDerbyHandler;
import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.actions.RemoveSelectedAction;

public class ImageThumbnailComponent extends JComponent implements ThumbnailSizeListener, Comparable<ImageThumbnailComponent> {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private String path;
	
	private int imagewidth, imageheight;
	
	private int defsize;
	
	private boolean selected = false;
	
	private boolean dragged = false;
	
	private JPopupMenu popupMenu;
	
	private HashMap<String, String> attributes = new HashMap<String, String>();
	
	private IImport importer = ImportDBDerbyHandler.getInstance();
	
	public void setDragged(boolean b) {
		dragged = b;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	public ImageThumbnailComponent(BufferedImage image, String path) {
		StatusBar.getInstance().addThumbnailSizeListener(this);
		defsize = StatusBar.getInstance().getSliderValue();
		this.image = image;
		if(path.startsWith("http")) {
			this.path = path;
		} else {
			this.path = path.replaceAll("\\\\", "/");
		}
		setToolTipText(this.path);
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
							} else {
								file = new File(p);
							}
							Desktop.getDesktop().open(file);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
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
//					Iterator iter = attributes.keySet().iterator();
//					System.out.println("---------------------------------");
//					while(iter.hasNext()) {
//						String k = (String) iter.next();
//						System.out.println(k + " - " + attributes.get(k));
//					}
//					System.out.println("---------------------------------");
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
		if(importer.isImported(getPath())) {
			attributes = importer.getAttributes(getPath());
		} else {
			if (getPath().startsWith("http:")) {
				// aus InputStream TempFile machen
				File f = null;
				try {
					URL ur = new URL(getPath());
					String name = ur.getFile().split("/")[ur.getFile().split(
							"/").length - 1];
					// String suffix =
					// name.split(".")[name.split(".").length-1];
					f = new File("temp" + name);
//					f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f);
					InputStream inputStream = ur.openConnection()
							.getInputStream();
					int z = 0;
					while (z >= 0) {
						z = inputStream.read();
						fos.write(z);
					}
					fos.close();
					attributes = ImageAnalyser.getInstance()
							.getImageAttributes(f, getPath());
					f.delete();
				} catch (Exception e) {
					f.delete();
					JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
					e.printStackTrace();
				}
			} else {
				attributes = ImageAnalyser.getInstance().getImageAttributes(
						new File(getPath()), "");
			}
			importer.addImport(this);
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public void paint(Graphics g) {
		if (image != null) {
			if(selected) {
				g.setColor(Color.orange);
				g.drawRect(0, 0, defsize-1, defsize-1);
				g.drawRect(1, 1, defsize-3, defsize-3);
				g.drawImage(image, ((defsize - imagewidth) / 2) + 1, ((defsize - imageheight) / 2) + 1, imagewidth-2, imageheight-2, this);
			} else {
				g.setColor(Color.gray);
				g.drawRect(0, 0, defsize-1, defsize-1);
				g.drawImage(image, (defsize - imagewidth) / 2, (defsize - imageheight) / 2, imagewidth, imageheight, this);
			}
			
			if(dragged) {
				g.setColor(Color.red);
				g.drawRect(0, 0, defsize-1, defsize-1);
				g.drawRect(1, 1, defsize-3, defsize-3);
				g.drawRect(2, 2, defsize-5, defsize-5);
				g.drawRect(3, 3, defsize-7, defsize-7);
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
		popupMenu = new JPopupMenu();
		JMenuItem rem = new JMenuItem("remove selected from ImageBase");
		rem.addActionListener(new RemoveSelectedAction());
		popupMenu.add(rem);
		JMenuItem move_up = new JMenuItem("move up");
		move_up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = ( ImageBase.getInstance().getimageList().indexOf(((ImageThumbnailComponent) ((JPopupMenu)((JMenuItem) e.getSource()).getParent()).getInvoker())) );
				if(index == 0) {
					return;
				}
				Collections.rotate(ImageBase.getInstance().getimageList().subList(index, index+2), -1);				
//				PreviewThumbnailComponent.getInstance().getLayout().layoutContainer(PreviewThumbnailComponent.getInstance());
				PreviewThumbnailComponent.getInstance().updateUI();
			}});
		popupMenu.add(move_up);
		JMenuItem move_down = new JMenuItem("move down");
		move_down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = ( ImageBase.getInstance().getimageList().indexOf(((ImageThumbnailComponent) ((JPopupMenu)((JMenuItem) e.getSource()).getParent()).getInvoker())) );
				if(index == 0) {
					return;
				}
				Collections.rotate(ImageBase.getInstance().getimageList().subList(index-1, index+1), -1);				
//				PreviewThumbnailComponent.getInstance().getLayout().layoutContainer(PreviewThumbnailComponent.getInstance());
				PreviewThumbnailComponent.getInstance().updateUI();
			}});
		popupMenu.add(move_down);
 		popupMenu.show(this, x, y);
	}

	@Override
	public int compareTo(ImageThumbnailComponent o) {
		//sort by path
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("path")) {
			if(attributes.get("path") == null && o.getAttributes().get("path") == null) {
				return 0;
			} else if(attributes.get("path") != null && o.getAttributes().get("path") == null) {
				return -1;
			} else if(attributes.get("path") == null && o.getAttributes().get("path") != null) {
				return 1;
			} else {
				return o.getAttributes().get("path").compareToIgnoreCase(getAttributes().get("path"));
			}
		}
		//sort by keywords
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("keywords")) {
			if(attributes.get("keywords") == null && o.getAttributes().get("keywords") == null) {
				return 0;
			} else if(attributes.get("keywords") != null && o.getAttributes().get("keywords") == null) {
				return -1;
			} else if(attributes.get("keywords") == null && o.getAttributes().get("keywords") != null) {
				return 1;
			} else {
				return o.getAttributes().get("keywords").compareToIgnoreCase(getAttributes().get("keywords"));
			}
		}
		//sort by cameratype
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("cameratype")) {
			if(attributes.get("cameratype") == null && o.getAttributes().get("cameratype") == null) {
				return 0;
			} else if(attributes.get("cameratype") != null && o.getAttributes().get("cameratype") == null) {
				return -1;
			} else if(attributes.get("cameratype") == null && o.getAttributes().get("cameratype") != null) {
				return 1;
			} else {
				return o.getAttributes().get("cameratype").compareToIgnoreCase(getAttributes().get("cameratype"));
			}
		}
		//sort by filesize
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("filesize")) {
			if(attributes.get("filesize") == null && o.getAttributes().get("filesize") == null) {
				return 0;
			} else if(attributes.get("filesize") != null && o.getAttributes().get("filesize") == null) {
				return -1;
			} else if(attributes.get("filesize") == null && o.getAttributes().get("filesize") != null) {
				return 1;
			} else {
				return (int) (Long.parseLong(o.getAttributes().get("filesize")) - (Long.parseLong(attributes.get("filesize"))));
			}
		}
		//sort by createdate
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("createdate")) {
		
		}
		//sort by changedate
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("changedate")) {
		
		}
		//sort by makedate
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("makedate")) {
		
		}
		//sort by pixelcount
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("pixelCount")) {
			if(attributes.get("pixelCount") == null && o.getAttributes().get("pixelCount") == null) {
				return 0;
			} else if(attributes.get("pixelCount") != null && o.getAttributes().get("pixelCount") == null) {
				return -1;
			} else if(attributes.get("pixelCount") == null && o.getAttributes().get("pixelCount") != null) {
				return 1;
			} else {
				return (int) (Long.parseLong(o.getAttributes().get("pixelCount")) - (Long.parseLong(attributes.get("pixelCount"))));
			}
		}
		//sort by exposureTime
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("exposureTime")) {
		
		}
		//sort by fnumber
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("fnumber")) {
			
		}
		//sort by focalLength
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("focalLength")) {
		
		}
		//sort by flash
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("flash")) {
		
		}
		//sort by iso
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("iso")) {
		
		}
		//sort by stars
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("stars")) {
		
		}
		//sort by avgSat
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("avgSat")) {
			if(attributes.get("avgSat") == null && o.getAttributes().get("avgSat") == null) {
				return 0;
			} else if(attributes.get("avgSat") != null && o.getAttributes().get("avgSat") == null) {
				return -1;
			} else if(attributes.get("avgSat") == null && o.getAttributes().get("avgSat") != null) {
				return 1;
			} else {
				return (int) (Long.parseLong(o.getAttributes().get("avgSat")) - (Long.parseLong(attributes.get("avgSat"))));
			}
		}
		//sort by avgLum
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("avgLum")) {
			if(attributes.get("avgLum") == null && o.getAttributes().get("avgLum") == null) {
				return 0;
			} else if(attributes.get("avgLum") != null && o.getAttributes().get("avgLum") == null) {
				return -1;
			} else if(attributes.get("avgLum") == null && o.getAttributes().get("avgLum") != null) {
				return 1;
			} else {
				return (int) (Long.parseLong(o.getAttributes().get("avgLum")) - (Long.parseLong(attributes.get("avgLum"))));
			}
		}
		//sort by contrast
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("contrast")) {
			if(attributes.get("contrast") == null && o.getAttributes().get("contrast") == null) {
				return 0;
			} else if(attributes.get("contrast") != null && o.getAttributes().get("contrast") == null) {
				return -1;
			} else if(attributes.get("contrast") == null && o.getAttributes().get("contrast") != null) {
				return 1;
			} else {
				return (int) (Long.parseLong(o.getAttributes().get("contrast")) - (Long.parseLong(attributes.get("contrast"))));
			}
		}
		//sort by dynamic
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("dynamic")) {
			if(attributes.get("dynamic") == null && o.getAttributes().get("dynamic") == null) {
				return 0;
			} else if(attributes.get("dynamic") != null && o.getAttributes().get("dynamic") == null) {
				return -1;
			} else if(attributes.get("dynamic") == null && o.getAttributes().get("dynamic") != null) {
				return 1;
			} else {
				return (int) (Long.parseLong(o.getAttributes().get("dynamic")) - (Long.parseLong(attributes.get("dynamic"))));
			}
		}
		//sort by faceCount
		if(ControlPanel.getInstance().getSortComponent().getSortBy().equals("faceCount")) {
			if(attributes.get("faceCount") == null && o.getAttributes().get("faceCount") == null) {
				return 0;
			} else if(attributes.get("faceCount") != null && o.getAttributes().get("faceCount") == null) {
				return -1;
			} else if(attributes.get("faceCount") == null && o.getAttributes().get("faceCount") != null) {
				return 1;
			} else {
				return (int) (Long.parseLong(o.getAttributes().get("faceCount")) - (Long.parseLong(attributes.get("faceCount"))));
			}
		} else {
			String name = "";
			int weight = 0;
			String value = "";
			int erg = 0;
			for (int i = 0; i < ControlPanel.getInstance().getSortComponent()
					.getDoc().getDocumentElement().getChildNodes().getLength(); i++) {
				if (ControlPanel.getInstance().getSortComponent().getDoc()
						.getDocumentElement().getChildNodes().item(i)
						.getAttributes().getNamedItem("name").getNodeValue()
						.equals(
								ControlPanel.getInstance().getSortComponent()
										.getSortBy())) {
					for (int i1 = 0; i1 < ControlPanel.getInstance()
							.getSortComponent().getDoc().getDocumentElement()
							.getChildNodes().item(i).getChildNodes()
							.getLength(); i1++) {
						name = ControlPanel.getInstance().getSortComponent()
								.getDoc().getDocumentElement().getChildNodes()
								.item(i).getChildNodes().item(i1)
								.getAttributes().getNamedItem("name")
								.getNodeValue();
						weight = Integer.parseInt(ControlPanel.getInstance()
								.getSortComponent().getDoc()
								.getDocumentElement().getChildNodes().item(i)
								.getChildNodes().item(i1).getAttributes()
								.getNamedItem("weight").getNodeValue());
						value = ControlPanel.getInstance().getSortComponent()
								.getDoc().getDocumentElement().getChildNodes()
								.item(i).getChildNodes().item(i1)
								.getAttributes().getNamedItem("value")
								.getNodeValue();

						// //////////////////////////////////////////////////////////////////////////////////////////////////////////

						// sort by filesize
						if (name.equals("filesize")) {
							if (attributes.get("filesize") == null
									&& o.getAttributes().get("filesize") == null) {
								erg += 0 * weight;
							} else if (attributes.get("filesize") != null
									&& o.getAttributes().get("filesize") == null) {
								erg += -1 * weight;
							} else if (attributes.get("filesize") == null
									&& o.getAttributes().get("filesize") != null) {
								erg += 1 * weight;
							} else {
								if((int) (Long.parseLong(o.getAttributes()
										.get("filesize")) - (Long
												.parseLong(attributes.get("filesize")))) > 0) {
									erg += 1 * weight;
								} else if((int) (Long.parseLong(o.getAttributes()
										.get("filesize")) - (Long
												.parseLong(attributes.get("filesize")))) < 0) {
									erg += -1 * weight;
								} else {
									erg += 0 * weight;
								}
								
							}
						}
						// sort by pixelcount
						if (name.equals("pixelCount")) {
							if (attributes.get("pixelCount") == null
									&& o.getAttributes().get("pixelCount") == null) {
								erg += 0 * weight;
							} else if (attributes.get("pixelCount") != null
									&& o.getAttributes().get("pixelCount") == null) {
								erg += -1 * weight;
							} else if (attributes.get("pixelCount") == null
									&& o.getAttributes().get("pixelCount") != null) {
								erg += 1 * weight;
							} else {
								if((int) (Long.parseLong(o.getAttributes()
										.get("pixelCount")) - (Long
												.parseLong(attributes.get("pixelCount")))) > 0) {
									erg += 1 * weight;
								} else if((int) (Long.parseLong(o.getAttributes()
										.get("pixelCount")) - (Long
												.parseLong(attributes.get("pixelCount")))) < 0) {
									erg += -1 * weight;
								} else {
									erg += 0 * weight;
								}
							}
						}
						// sort by avgSat
						if (name.equals("avgSat")) {
							if (attributes.get("avgSat") == null
									&& o.getAttributes().get("avgSat") == null) {
								erg += 0 * weight;
							} else if (attributes.get("avgSat") != null
									&& o.getAttributes().get("avgSat") == null) {
								erg += -1 * weight;
							} else if (attributes.get("avgSat") == null
									&& o.getAttributes().get("avgSat") != null) {
								erg += 1 * weight;
							} else {
								if((int) (Long.parseLong(o.getAttributes()
										.get("avgSat")) - (Long
												.parseLong(attributes.get("avgSat")))) > 0) {
									erg += 1 * weight;
								} else if((int) (Long.parseLong(o.getAttributes()
										.get("avgSat")) - (Long
												.parseLong(attributes.get("avgSat")))) < 0) {
									erg += -1 * weight;
								} else {
									erg += 0 * weight;
								}
							
							}
						}
						// sort by avgLum
						if (name.equals("avgLum")) {
							if (attributes.get("avgLum") == null
									&& o.getAttributes().get("avgLum") == null) {
								erg += 0 * weight;
							} else if (attributes.get("avgLum") != null
									&& o.getAttributes().get("avgLum") == null) {
								erg += -1 * weight;
							} else if (attributes.get("avgLum") == null
									&& o.getAttributes().get("avgLum") != null) {
								erg += 1 * weight;
							} else {
								if((int) (Long.parseLong(o.getAttributes()
										.get("avgLum")) - (Long
												.parseLong(attributes.get("avgLum")))) > 0) {
									erg += 1 * weight;
								} else if((int) (Long.parseLong(o.getAttributes()
										.get("avgLum")) - (Long
												.parseLong(attributes.get("avgLum")))) < 0) {
									erg += -1 * weight;
								} else {
									erg += 0 * weight;
								}
								
							}
						}
						// sort by contrast
						if (name.equals("contrast")) {
							if (attributes.get("contrast") == null
									&& o.getAttributes().get("contrast") == null) {
								erg += 0 * weight;
							} else if (attributes.get("contrast") != null
									&& o.getAttributes().get("contrast") == null) {
								erg += -1 * weight;
							} else if (attributes.get("contrast") == null
									&& o.getAttributes().get("contrast") != null) {
								erg += 1 * weight;
							} else {
								if((int) (Long.parseLong(o.getAttributes()
										.get("contrast")) - (Long
												.parseLong(attributes.get("contrast")))) > 0) {
									erg += 1 * weight;
								} else if((int) (Long.parseLong(o.getAttributes()
										.get("contrast")) - (Long
												.parseLong(attributes.get("contrast")))) < 0) {
									erg += -1 * weight;
								} else {
									erg += 0 * weight;
								}
								
							}
						}
						// sort by dynamic
						if (name.equals("dynamic")) {
							if (attributes.get("dynamic") == null
									&& o.getAttributes().get("dynamic") == null) {
								erg += 0 * weight;
							} else if (attributes.get("dynamic") != null
									&& o.getAttributes().get("dynamic") == null) {
								erg += -1 * weight;
							} else if (attributes.get("dynamic") == null
									&& o.getAttributes().get("dynamic") != null) {
								erg += 1 * weight;
							} else {
								if((int) (Long.parseLong(o.getAttributes()
										.get("dynamic")) - (Long
												.parseLong(attributes.get("dynamic")))) > 0) {
									erg += 1 * weight;
								} else if((int) (Long.parseLong(o.getAttributes()
										.get("dynamic")) - (Long
												.parseLong(attributes.get("dynamic")))) < 0) {
									erg += -1 * weight;
								} else {
									erg += 0 * weight;
								}
								
							}
						}
						// sort by faceCount
						if (name.equals("faceCount")) {
							if (attributes.get("faceCount") == null
									&& o.getAttributes().get("faceCount") == null) {
								erg += 0 * weight;
							} else if (attributes.get("faceCount") != null
									&& o.getAttributes().get("faceCount") == null) {
								erg += -1 * weight;
							} else if (attributes.get("faceCount") == null
									&& o.getAttributes().get("faceCount") != null) {
								erg += 1 * weight;
							} else {
								if((int) (Long.parseLong(o.getAttributes()
										.get("faceCount")) - (Long
												.parseLong(attributes.get("faceCount")))) > 0) {
									erg += 1 * weight;
								} else if((int) (Long.parseLong(o.getAttributes()
										.get("faceCount")) - (Long
												.parseLong(attributes.get("faceCount")))) < 0) {
									erg += -1 * weight;
								} else {
									erg += 0 * weight;
								}
								
							}
						}

						////////////////////////////////////////////////////////////////////////////////////////////////////////////
					}
				}

			}
//			System.out.println("erg " + erg);
			return erg;
		}
	}

	public int getImagewidth() {
		return imagewidth;
	}

	public void setImagewidth(int imagewidth) {
		this.imagewidth = imagewidth;
	}

	public int getImageheight() {
		return imageheight;
	}

	public void setImageheight(int imageheight) {
		this.imageheight = imageheight;
	}

	public int getDefsize() {
		return defsize;
	}

	public void setDefsize(int defsize) {
		this.defsize = defsize;
	}
	
}
