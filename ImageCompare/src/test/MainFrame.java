package test;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JButton button;
	
	private JPanel imagePanel;
	
	public MainFrame() {
		imagePanel = new JPanel();
		add(new JScrollPane(imagePanel));
		button = new JButton("open Image");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}});
//		add(button);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		loadImage(mf.getImagePanel(), "test.jpg");
	}
	
	public static void loadImage(JPanel p, String s) {
		BufferedImage bimg = null;    
	    try {
			bimg = ImageIO.read(new File(s));
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		int test = 0;
		for(int i = 0; i < bimg.getHeight(); i++) {   
	        for(int j = 0; j < bimg.getWidth(); j++) {  
	        	test++;
//	        	System.out.println(bimg.getRGB(j, i));
//	        	System.out.println();
//	            if(bimg.getRGB(j, i) == -10898381) {   
//	            	bimg.setRGB(j, i, 0x8F1C1C);  	
//	            }   
//	        	if(i%2 == 0 && j%2 == 0) {
//	        		bimg.setRGB(j, i, 0x8F1C1C); 
//	        	}
	        	System.out.println(Integer.toHexString(bimg.getRGB(j, i)));
	        	//diesen hex string in 2stellen häppchen aufteilen, erster ist alpha, rest sind r g b
	        	//einzelne r g b werte wieder in int umrechnen
	        	//Integer.toHexString(bimg.getRGB(j, i));
	        }   
	    } 
		System.out.println(test);
		
//	   ((Graphics2D) p.getGraphics()).drawImage(bimg, null, 0, 0);  
	   
	   ((Graphics2D) p.getGraphics()).drawImage(bimg, 0, 0, 500, 500, 0, 0, bimg.getWidth(), bimg.getHeight(), null);   
	}

	public JPanel getImagePanel() {
		return imagePanel;
	}

	public void setImagePanel(JPanel imagePanel) {
		this.imagePanel = imagePanel;
	}

}
