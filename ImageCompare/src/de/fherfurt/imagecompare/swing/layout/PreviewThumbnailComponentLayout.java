package de.fherfurt.imagecompare.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

//TODO -> implement PrieviwThumbnailComponentLayout
public class PreviewThumbnailComponentLayout implements LayoutManager, Serializable {

	private static final long serialVersionUID = 1L;

	private int minWidth = 0;
	
	private int minHeight = 0;
	
	private int preferredWidth = 0;
	
	private int preferredHeight = 0;
	
	private boolean sizesSet = false;
	
	private int maxComponentWidth = 0;
	
	private int maxComponentHeight = 0;
	
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

		// compute the maximum component widths and heights
		// and set the preferred size to the sum of the component sizes.
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
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			int x = 5, y = 5;
			for (Component c : parent.getComponents()) {
				System.out.println("parent: " + parent.getSize() + " c: " + c.getSize());
				c.setBounds(x, y, 90, 90);
				x += c.getWidth() + 5;
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

}
