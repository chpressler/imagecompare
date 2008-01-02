package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import de.fherfurt.imagecompare.ResourceHandler;

public class ImagePreviewComponent extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	public ImagePreviewComponent() {
		PreviewThumbnailComponent pt = new PreviewThumbnailComponent();
		JScrollPane tsp = new JScrollPane(pt);
		tsp.getHorizontalScrollBar().setUnitIncrement(50);
		add(tsp , ResourceHandler.getInstance().getStrings().getString("previewthumbnail"));
		PreviewCarouselComponent pc = new PreviewCarouselComponent();
		add(new JScrollPane(pc), ResourceHandler.getInstance().getStrings().getString("previewcarousel"));
//		add(new PreviewListComponent() , ResourceHandler.getInstance().getStrings().getString("previewlist"));
	}
	
//	@Override
//	public void paint(Graphics g) {
//		int w = (int) getSize().getWidth();
//		int h = 200;
//		Dimension d = new Dimension(w, h);
//		setSize(d);
//		super.paint(g);
//	}

}
