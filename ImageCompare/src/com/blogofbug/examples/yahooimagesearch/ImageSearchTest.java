package com.blogofbug.examples.yahooimagesearch;

import javax.swing.JFrame;

public class ImageSearchTest extends JFrame {

	public ImageSearchTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		add(new ImageCarousel());
	}
	
	public static void main(String[] args) {
		new ImageSearchTest();
	}
	
}
