package de.fherfurt.imagecompare.swing.components;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import de.fherfurt.imagecompare.ResourceHandler;

public class ImagePreviewComponent extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	public ImagePreviewComponent() {
		add(new PreviewThumbnailComponent() , ResourceHandler.getInstance().getStrings().getString("previewthumbnail"));
		PreviewCarouselComponent pc = new PreviewCarouselComponent();
		add(new JScrollPane(pc), ResourceHandler.getInstance().getStrings().getString("previewcarousel"));
		add(new PreviewListComponent() , ResourceHandler.getInstance().getStrings().getString("previewlist"));
	}

}
