package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;

import javax.swing.JPanel;

import com.blogofbug.swing.components.JCarosel;

public class PreviewCarouselComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public PreviewCarouselComponent() {
		JCarosel carosel = new JCarosel(10);
		carosel.setBackground(Color.DARK_GRAY, Color.BLACK);
		carosel
				.add(
						"http://images.nubiles.net/galleries2/nastya/erotic-hottie/14.jpg",
						"Thanks to You!");
		carosel.add("http://english.people.com.cn/200611/03/images/auto1.jpg",
				"Thanks to You!");
		this.add(carosel);
	}

}
