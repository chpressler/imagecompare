package de.fherfurt.imagecompare.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.swing.components.ControlPanel;
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
					for (int i = 0; i < ImageBase.getInstance().getimageList()
							.size(); i++) {
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
					for (int i = ImageBase.getInstance().getimageList().size() - 1; i >= 0; i--) {
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
				for (Component c : parent.getComponents()) {
					if (x > parent.getWidth() - w + 10) {
						y += c.getHeight() + 5;
						x = 5;
					}
					c.setBounds(x, y, w, h);
					x += c.getWidth() + 5;
				}
			}
		}
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
