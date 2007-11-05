package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;

import com.blogofbug.swing.components.JCarosel;

public class PreviewCarouselComponent extends JCarosel {

	private static final long serialVersionUID = 1L;
	
	public PreviewCarouselComponent() {
		super(80);
		setPreferredSize(new Dimension(800, 300));
		setMinimumSize(new Dimension(400, 100));
		setMaximumSize(new Dimension(800, 400));
		setBackground(Color.GRAY, Color.BLACK);
		add("http://www.4x4offroads.com/image-files/1996-ford-bronco-picture.jpg", "car");
		add("http://www.drivesuv.com/images/FordBroncoConcept.jpg", "car");
		add("http://www.bigdawg4x4.com/bigdawg1.jpg", "car");
		add("http://z.about.com/d/4wheeldrive/1/0/0/L/1/Blackcomb_IL_93Bronco_2RR.jpg", "car");
		add("http://zenseeker.net/4x4/Bronco.jpg", "car");
	}

}
