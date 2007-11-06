package de.fherfurt.imagecompare.swing.layout;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

import de.fherfurt.imagecompare.swing.components.LightTableComponent;
import de.fherfurt.imagecompare.swing.components.LightTableImage;

public class LightTableLayout implements LayoutManager, Serializable {
	/** Constant that specifies left alignment.  */
	public static final int LEFT = 0;
	/** Constant that specifies center alignment.  */
	public static final int CENTER = 1;
	/** Constant that specifies right alignment.  */
	public static final int RIGHT = 2;

	public static final int LEADING = 3;

	public static final int TRAILING = 4;
	
	boolean b = false;
	
	// Serialization constant
	private static final long serialVersionUID = -7262534875583282631L;

	public void addLayoutComponent(String name, Component comp) {
		// Nothing.
	}

	public int getAlignment() {
		return align;
	}

	public int getHgap() {
		return hgap;
	}

	public int getVgap() {
		return vgap;
	}

	public LightTableLayout() {
		this(CENTER, 5, 5);
	}

	public LightTableLayout(int align) {
		this(align, 5, 5);
	}

	public LightTableLayout(int align, int hgap, int vgap) {
		// Use methods to set fields so that we can have all the checking
		// in one place.
		setVgap(vgap);
		setHgap(hgap);
		setAlignment(align);
	}

	public void layoutContainer(Container parent) {
		if(!b) {
		synchronized (parent.getTreeLock()) {
			int num = parent.getComponentCount();
			// This is more efficient than calling getComponents().
			Component[] comps = parent.getComponents();

			Dimension d = parent.getSize();
			Insets ins = parent.getInsets();

			ComponentOrientation orient = parent.getComponentOrientation();
			boolean left_to_right = orient.isLeftToRight();

			int y = ins.top + vgap;
			int i = 0;
			while (i < num) {
				// Find the components which go in the current row.
				int new_w = ins.left + hgap + ins.right;
				int new_h = 0;
				int j;
				boolean found_one = false;
				for (j = i; j < num; ++j) {
					// Skip invisible items.
					if (!comps[j].isVisible())
						continue;

					Dimension c = comps[j].getPreferredSize();

					int next_w = new_w + hgap + c.width;
					if (next_w <= d.width || !found_one) {
						new_w = next_w;
						new_h = Math.max(new_h, c.height);
						found_one = true;
					} else {
						// Must start a new row, and we already found an item
						break;
					}
				}

				// Set the location of each component for this row.
				int x;

				int myalign = align;
				if (align == LEADING)
					myalign = left_to_right ? LEFT : RIGHT;
				else if (align == TRAILING)
					myalign = left_to_right ? RIGHT : LEFT;

				if (myalign == RIGHT)
					x = ins.left + (d.width - new_w) + hgap;
				else if (myalign == CENTER)
					x = ins.left + (d.width - new_w) / 2 + hgap;
				else
					// LEFT and all other values of align.
					x = ins.left + hgap;

				for (int k = i; k < j; ++k) {
					if (comps[k].isVisible()) {
						Dimension c = comps[k].getPreferredSize();
						comps[k].setBounds(x, y + (new_h - c.height) / 2,
								c.width, c.height);
						x += c.width + hgap;
					}
				}

				// Advance to next row.
				i = j;
				y += new_h + vgap;
			}
		}
		
		} else {
			Component[] comps = parent.getComponents();
			for(Component co : comps) {
				if(co.getBounds().getWidth() == 0 || co.getHeight() == 0) {
					co.setBounds((int) co.getBounds().getX() - (int) co.getParent().getX(), (int) co.getY()  - (int) co.getParent().getY(), 10, 10);
					((LightTableComponent) co.getParent().getParent()).setZoomable((LightTableImage) co);
				}
				else {
					co.setBounds(co.getBounds());
				}
			}
		}
		b = true;
		
		
	}

	public Dimension minimumLayoutSize(Container cont) {
		return getSize(cont, true);
	}

	public Dimension preferredLayoutSize(Container cont) {
		return getSize(cont, false);
	}

	public void removeLayoutComponent(Component comp) {

	}

	public void setAlignment(int align) {

		this.align = align;
	}

	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	public String toString() {
		return ("[" + getClass().getName() + ",hgap=" + hgap + ",vgap=" + vgap
				+ ",align=" + align + "]");
	}

	private Dimension getSize(Container parent, boolean is_min) {
		synchronized (parent.getTreeLock()) {
			int w, h, num = parent.getComponentCount();

			Component[] comps = parent.getComponents();

			w = 0;
			h = 0;
			for (int i = 0; i < num; ++i) {
				if (!comps[i].isVisible())
					continue;

				Dimension d;

				if (is_min)
					d = comps[i].getMinimumSize();
				else
					d = comps[i].getPreferredSize();

				w += d.width;
				h = Math.max(d.height, h);
			}

			Insets ins = parent.getInsets();

			if (num == 0)
				w += 2 * hgap + ins.left + ins.right;
			else
				w += (num + 1) * hgap + ins.left + ins.right;
			h += 2 * vgap + ins.top + ins.bottom;

			return new Dimension(w, h);
		}
	}

	private int align;

	private int hgap;

	private int vgap;
}