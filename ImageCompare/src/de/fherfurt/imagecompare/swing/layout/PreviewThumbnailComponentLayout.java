package de.fherfurt.imagecompare.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;
import java.util.Iterator;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.swing.components.ControlPanel;
import de.fherfurt.imagecompare.swing.components.FilterFrame;
import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;
import de.fherfurt.imagecompare.swing.components.Operator;
import de.fherfurt.imagecompare.swing.components.StatusBar;
import de.fherfurt.imagecompare.swing.components.ThumbnailSizeListener;

public class PreviewThumbnailComponentLayout implements LayoutManager, ThumbnailSizeListener, Serializable {

	private static final long serialVersionUID = 1L;

	private int minWidth = 0;
	
	private int minHeight = 0;
	
	private int preferredWidth = 0;
	
	private int preferredHeight = 0;
	
	private boolean sizesSet = false;
	
	private int maxComponentWidth = 0;
	
	private int maxComponentHeight = 0;
	
	int w, h = 0; 
	
	int x = 5, y = 5;
	
	public void setSizes(Container parent) {
		if (sizesSet)
			return;
		int n = parent.getComponentCount();

		preferredWidth = 0;
		preferredHeight = 0;
		minWidth = 0;
		minHeight = 0;
		maxComponentWidth = 0;
		maxComponentHeight = 0;

		for (int i = 0; i < n; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				Dimension d = c.getPreferredSize();
				maxComponentWidth = Math.max(maxComponentWidth, d.width);
				maxComponentHeight = Math.max(maxComponentHeight, d.height);
				preferredWidth += d.width;
				preferredHeight += d.height;
			}
		}
		minWidth = preferredWidth / 2;
		minHeight = preferredHeight / 2;
		sizesSet = true;
	}
	
	public PreviewThumbnailComponentLayout() {
		StatusBar.getInstance().addThumbnailSizeListener(this);
		w = StatusBar.getInstance().getSliderValue();
		h = StatusBar.getInstance().getSliderValue();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			x = 5;
			y = 5;
			if (ControlPanel.getInstance().getSortComponent().sorted()) {
				if (ControlPanel.getInstance().getSortComponent()
						.isDescenting()) {
					int i1 = 0; 
					for (int i = 0; i < ImageBase.getInstance().getimageList()
							.size(); i++) {
						i1++;
						if(StatusBar.getInstance().getImageBaseSize() < i1) {
							ImageBase.getInstance().getimageList().get(i).setBounds(0,0,0,0);
							continue;
						}
						if(FilterFrame.getInstance().isFilterOn()) {
							if(!isPictureOk(ImageBase.getInstance().getimageList().get(i))) {
								ImageBase.getInstance().getimageList().get(i).setBounds(0,0,0,0);
								i1--;
								continue;
							}
						}
						if (x > parent.getWidth() - w + 10) {
							y += ImageBase.getInstance().getimageList().get(i)
									.getHeight() + 5;
							x = 5;
						}
						ImageBase.getInstance().getimageList().get(i)
								.setBounds(x, y, w, h);
						x += ImageBase.getInstance().getimageList().get(i)
								.getWidth() + 5;
					}
				} else {
					int i1 = 0;
					for (int i = ImageBase.getInstance().getimageList().size() - 1; i >= 0; i--) {
						i1++;
						if(StatusBar.getInstance().getImageBaseSize() < i1) {
							ImageBase.getInstance().getimageList().get(i).setBounds(0,0,0,0);
							continue;
						}
						if(FilterFrame.getInstance().isFilterOn()) {
							if(!isPictureOk(ImageBase.getInstance().getimageList().get(i))) {
								ImageBase.getInstance().getimageList().get(i).setBounds(0,0,0,0);
								i1--;
								continue;
							}
						}
						if (x > parent.getWidth() - w + 10) {
							y += ImageBase.getInstance().getimageList().get(i)
									.getHeight() + 5;
							x = 5;
						}
						ImageBase.getInstance().getimageList().get(i)
								.setBounds(x, y, w, h);
						x += ImageBase.getInstance().getimageList().get(i)
								.getWidth() + 5;
					}
				}
			} else {
				
				int i = 0;
				for (int ii = ImageBase.getInstance().getimageList().size() - 1; ii >= 0; ii--) {
					ImageThumbnailComponent itc = ImageBase.getInstance().getimageList().get(ii);
					i++;
					if(StatusBar.getInstance().getImageBaseSize() < i) {
						itc.setBounds(0,0,0,0);
						continue;
					} 
					if (FilterFrame.getInstance().isFilterOn()) {
						if (!isPictureOk(itc)) {
							itc.setBounds(0, 0, 0, 0);
							i--;
							continue;
						}
					}
					if (x > parent.getWidth() - w + 10) {
						y += itc.getHeight() + 5;
						x = 5;
					}
					itc.setBounds(x, y, w, h);
					x += itc.getWidth() + 5;
				}
				
//				int i = 0;
//				for (Component c : parent.getComponents()) {
//					i++;
//					if(StatusBar.getInstance().getImageBaseSize() < i) {
//						c.setBounds(0,0,0,0);
//						continue;
//					} 
//					if (FilterFrame.getInstance().isFilterOn()) {
//						if (!isPictureOk((ImageThumbnailComponent) c)) {
//							c.setBounds(0, 0, 0, 0);
//							i--;
//							continue;
//						}
//					}
//					if (x > parent.getWidth() - w + 10) {
//						y += c.getHeight() + 5;
//						x = 5;
//					}
//					c.setBounds(x, y, w, h);
//					x += c.getWidth() + 5;
//				}
			}
		}
	}
	
	private boolean isPictureOk(ImageThumbnailComponent image) {
		Iterator<Operator> iter = FilterFrame.getInstance().getFilterMap().keySet().iterator();
		boolean ok = true;
		while(iter.hasNext()){
			Operator o = iter.next();
			
			//Filesize
			if(o.getName().equals("filesize")) {
				if(o.getSelectedItem().toString().equals("=")) {
					try {
						if (Long.parseLong(image.getAttributes()
								.get("filesize").toString()) == Long
								.parseLong(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
						} else {
							ok  = false;
						}
					} catch (Exception e) {
						
					}
				} else if(o.getSelectedItem().toString().equals("<")) {
					try {
						if (Long.parseLong(image.getAttributes()
								.get("filesize").toString()) < Long
								.parseLong(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
					
					}
				} else if(o.getSelectedItem().toString().equals(">")) {
					try {
						if (Long.parseLong(image.getAttributes()
								.get("filesize").toString()) > Long
								.parseLong(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
	
					}
				}
			}
			
			//Saturation
			if(o.getName().equals("avgSat")) {
				if(o.getSelectedItem().toString().equals("=")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("avgSat").toString()) == Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
						} else {
							ok  = false;
						}
					} catch (Exception e) {
						
					}
				} else if(o.getSelectedItem().toString().equals("<")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("avgSat").toString()) < Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
					
					}
				} else if(o.getSelectedItem().toString().equals(">")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("avgSat").toString()) > Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
	
					}
				}
			}
			
			//Luminance
			if(o.getName().equals("avgLum")) {
				if(o.getSelectedItem().toString().equals("=")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("avgLum").toString()) == Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
						} else {
							ok  = false;
						}
					} catch (Exception e) {
						
					}
				} else if(o.getSelectedItem().toString().equals("<")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("avgLum").toString()) < Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
					
					}
				} else if(o.getSelectedItem().toString().equals(">")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("avgLum").toString()) > Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
	
					}
				}
			}
			
			//Contrast
			if(o.getName().equals("contrast")) {
				if(o.getSelectedItem().toString().equals("=")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("contrast").toString()) == Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
						} else {
							ok  = false;
						}
					} catch (Exception e) {
						
					}
				} else if(o.getSelectedItem().toString().equals("<")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("contrast").toString()) < Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
					
					}
				} else if(o.getSelectedItem().toString().equals(">")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("contrast").toString()) > Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
	
					}
				}
			}
			
			//Dynamic
			if(o.getName().equals("dynamic")) {
				if(o.getSelectedItem().toString().equals("=")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("dynamic").toString()) == Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
						} else {
							ok  = false;
						}
					} catch (Exception e) {
						
					}
				} else if(o.getSelectedItem().toString().equals("<")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("dynamic").toString()) < Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
					
					}
				} else if(o.getSelectedItem().toString().equals(">")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("dynamic").toString()) > Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
	
					}
				}
			}
			
			//FaceCount
			if(o.getName().equals("faceCount")) {
				if(o.getSelectedItem().toString().equals("=")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("faceCount").toString()) == Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
						} else {
							ok  = false;
						}
					} catch (Exception e) {

					}
				} else if(o.getSelectedItem().toString().equals("<")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("faceCount").toString()) < Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
						
					}
				} else if(o.getSelectedItem().toString().equals(">")) {
					try {
						if (Integer.parseInt(image.getAttributes()
								.get("faceCount").toString()) > Integer
								.parseInt(FilterFrame.getInstance()
										.getFilterMap().get(o).getText())) {
							
						} else {
							ok = false;
						}
					} catch (Exception e) {
						
					}
				}
			}
			
		}
		return ok;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		 setSizes(parent);
	      Insets insets = parent.getInsets();
	      int width = minWidth + insets.left + insets.right;
	      int height = minHeight + insets.top + insets.bottom;
	      return new Dimension(width, height);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		setSizes(parent);
	      Insets insets = parent.getInsets();
	      int width = preferredWidth + insets.left + insets.right;
	      int height = preferredHeight + insets.top + insets.bottom;
	      return new Dimension(width, height);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public void thumbnailSizChanged() {
		w = StatusBar.getInstance().getSliderValue();
		h = StatusBar.getInstance().getSliderValue();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

}
