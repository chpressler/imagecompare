package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;

import com.blogofbug.swing.components.JCarosel;

public class PreviewCarouselComponent extends JCarosel {

	private static final long serialVersionUID = 1L;
	
	public PreviewCarouselComponent() {
		super(128);
		setPreferredSize(new Dimension(400, 300));
		setMinimumSize(new Dimension(300, 100));
		setMaximumSize(new Dimension(400, 400));
		setBackground(Color.GRAY, Color.BLACK);
		
		try {
			add(new File("pics/1.jpg").toURL().toString(), "car");
			add(new File("pics/2.jpg").toURL().toString(), "car");
			add(new File("pics/3.jpg").toURL().toString(), "car");
			add(new File("pics/4.jpg").toURL().toString(), "car");
			add(new File("pics/5.jpg").toURL().toString(), "car");
//			add("http://www.xtrakt.muc.kobis.de/sin_clubs/Kinder/Nellie/pferd.jpg", "Pferd");
//			add("http://www1.istockphoto.com/file_thumbview_approve/2779454/2/istockphoto_2779454_horse_white_pferd.jpg", "Pferd");
//			add("http://www.sonja-roerig.de/Galerie/Freies/Bilder/Pferd_g.gif", "Pferd");
//			add("http://www.zoonetz.de/img_desktop/pferd_1024x768.bmp", "Pferd");
//			add("http://gimps.de/wettbewerb/albums/userpics/10014/pferd.jpg", "Pferd");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
