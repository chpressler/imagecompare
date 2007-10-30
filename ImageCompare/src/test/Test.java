package test;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Test() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
	}
	
	public static void drawImage(Graphics2D g2d) {
		BufferedImage bimg = null;    
	    try {
			bimg = ImageIO.read(new File("test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    g2d.drawImage(bimg, null, 0, 0);  
	}

	public static void main(String[] args) throws IOException {
		Test t = new Test();
		drawImage(((Graphics2D) t.getRootPane().getGraphics()));
	}
	
}
