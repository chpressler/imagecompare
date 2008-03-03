package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.iptc.IptcDirectory;

import de.fherfurt.imagecompare.ImageAnalyser;
import de.fherfurt.imagecompare.ImportDBMySQLHandler;
import de.fherfurt.imagecompare.util.ICUtil;

public class DetailsFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JScrollPane jsp;
	
	ExifPanel ep;

	public DetailsFrame(ImageComponent ic) {
//		System.out.println(ICUtil.getInstance().getFaceCount(ic.getPath()));
		String name = ic.getPath().substring(ic.getPath().lastIndexOf("/")+1);
		setTitle(name);
		setAlwaysOnTop(true);
		setLayout(null);
		
		JLabel pathlabel = new JLabel(ic.getPath());
		pathlabel.setToolTipText(ic.getPath());
		pathlabel.setBounds(1, 1, 258, 15);
		add(pathlabel);
		
		JSeparator sep1 = new JSeparator(JSeparator.HORIZONTAL);
		sep1.setBounds(1, 17, 258, 2);
		add(sep1);
		
		int width = ic.getImage().getWidth();
		int height = ic.getImage().getHeight();
		if(width > height) {
			height = 80 * height / width;
			width = 80;
		}
		else if(width < height) {
			width = 80 * width / height;
			height = 80;
		}
		else {
			height = 80;
			width = 80;
		}
		ThumbImageComp ipc = new ThumbImageComp(ic.getImage(), width, height);
		ipc.setBounds(1, 20, width, height);
		add(ipc);
		JLabel l_name = new JLabel(name);
		l_name.setBounds(width + 10, 20, 150, 15);
		add(l_name);
		JLabel l_size = new JLabel(ic.getImage().getWidth() + " x " + ic.getImage().getHeight());
		l_size.setBounds(width + 10, 40, 150, 15);
		add(l_size);
		int pixcount = ic.getImage().getWidth() * ic.getImage().getHeight();
		JLabel l_pixel = new JLabel(pixcount + " Pixel");
		l_pixel.setBounds(width + 10, 60, 150, 15);
		add(l_pixel);
		
		if(height < 70) {
			height = 70;
		}
		
		JSeparator sep2 = new JSeparator(JSeparator.HORIZONTAL);
		sep2.setBounds(1, 22 + height, 258, 2);
		add(sep2);
		
		MyChart chart = new MyChart(ic);
		chart.setBounds(1, 26 + height, 258, 190);
		add(chart);
		
 		JSeparator sep3 = new JSeparator(JSeparator.HORIZONTAL);
		sep3.setBounds(1, 215 + height, 258, 2);
		add(sep3);
		
		ep = new ExifPanel(ic.getPath());
		jsp = new JScrollPane(ep);
		jsp.setBounds(1, 220 + height, 256, 150);
		jsp.updateUI();
		add(jsp);
		
		setSize(263, height + 402);
		jsp.updateUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		ep.setSize(258, ep.getUK() + 10);
//	}

}

class ExifPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	int uk = 0;
	
	public ExifPanel(String path) {
		setLayout(null);
		HashMap<String, String> attributes = new HashMap<String, String>();
		
		if(ImportDBMySQLHandler.getInstance().isImported(path)) {
			attributes = ImportDBMySQLHandler.getInstance().getAttributes(path.replaceAll("\\\\", "/"));
		} else {
			attributes = ImageAnalyser.getInstance().getImageAttributes(new File(path.replaceAll("\\\\", "/")));
		}
		
		JLabel name, value;
		Iterator iter = attributes.keySet().iterator();
		while(iter.hasNext()) {
			String k = (String) iter.next();
			name = new JLabel(k);
			name.setBounds(2, uk, 100, 15);
			value = new JLabel(attributes.get(k));
			value.setToolTipText(value.getText());
			value.setBounds(110, uk, 127, 15);
			uk += 20;
			add(name);
			add(value);
		}
		setPreferredSize(new Dimension(237, uk + 10));
	}	
	
	public int getUK() {
		return uk;
	}
	
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		setSize(258, uk + 10);
//		System.out.println("size: ---> " + getSize() + " - " + getParent().getParent().getSize());
//		updateUI();
//	}
}

class MyChart extends JComponent {
	
	private static final long serialVersionUID = 1L;

	JCheckBox rb;
	JCheckBox gb;
	JCheckBox bb;
	JCheckBox lb;
	
	JRadioButton lin_b;
	JRadioButton log_b;
	ButtonGroup bg;
	
	boolean log = false;
	
	ImageComponent ic;
	
	public MyChart(ImageComponent ic) {
		this.ic = ic;
		setPreferredSize(new Dimension(258, 200));
		setLayout(null);
		
		rb = new JCheckBox("r", true);
		gb = new JCheckBox("g", true);
		bb = new JCheckBox("b", true);
		lb = new JCheckBox("l", false);
		
		bg = new ButtonGroup();
		lin_b = new JRadioButton("linear", true);
		log_b = new JRadioButton("log", false);
		bg.add(lin_b);
		bg.add(log_b);
		lin_b.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					log = false;
					paint(getGraphics());
				}
			}});
		log_b.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					log = true;
					paint(getGraphics());
				}
			}});
		
		rb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				paint(getGraphics());
			}});
		gb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				paint(getGraphics());
			}});
		bb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				paint(getGraphics());
			}});
		lb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				paint(getGraphics());
			}});
		
		JLabel label = new JLabel("Histogramm: ");
		label.setBounds(10, 10, 80, 15);
		rb.setBounds(90, 10, 30, 15);
		gb.setBounds(130, 10, 30, 15);
		bb.setBounds(170, 10, 30, 15);
		lb.setBounds(210, 10, 30, 15);
		lin_b.setBounds(60, 165, 60, 20);
		log_b.setBounds(150, 165, 100, 20);
		add(label);
		add(rb);
		add(gb);
		add(bb);
		add(lb);
		add(lin_b);
		add(log_b);
	}
	
	@Override
	public void paint(Graphics g) {
		
		int f;
		if (ic.getPixelCount() <= 200000) {
			f = 20;
		} else if (ic.getPixelCount() > 200000 && ic.getPixelCount() <= 2000000) {
			f = 100;
		} else if (ic.getPixelCount() > 2000000
				&& ic.getPixelCount() <= 5000000) {
			f = 600;
		} else {
			f = 900;
		}
	
// System.out.println(ic.getPixelCount());
		
		g.clearRect(1, 1, 255, 199);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
//		g.drawImage((Image)ic.getImage(),0,0,256,160, null);
		
		int x = 0;
		for(int i = 0; i < 255; i++) {
			int val = 0;
			if(rb.isSelected()) {
				if(log) {
					f = 1;
					val = (int) (Math.log(ic.getRed().get(i)) * 10);
				} else {
					val = ic.getRed().get(i);
				}
//				g.setColor(new Color(0, 0, 0, 150));
//				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(i, 0, 0, 150));
				g.fillRect(x, 150-(val/f), 1, val/f);
				g.fillRect(x, 150, 1, 10);
			}
			if(gb.isSelected()) {
				if(log) {
					f = 1;
					val = (int) (Math.log(ic.getGreen().get(i)) * 10);
				} else {
					val = ic.getGreen().get(i);
				}
//				g.setColor(new Color(0, 0, 0, 150));
//				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(0, i, 0, 150));
				g.fillRect(x, 150-(val/f), 1, val/f);
				g.fillRect(x, 150, 1, 10);
				
			}
			if(bb.isSelected()) {
				if(log) {
					f = 1;
					val = (int) (Math.log(ic.getBlue().get(i)) * 10);
				} else {
					val = ic.getBlue().get(i);
				}
//				g.setColor(new Color(0, 0, 0, 150));
//				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(0, 0, i, 150));
				g.fillRect(x, 150-(val/f), 1, val/f);
				g.fillRect(x, 150, 1, 10);
				
			}
			if(lb.isSelected()) {
				if(log) {
					f = 1;
					val = (int) (Math.log(ic.getLum().get(i)) * 10);
				} else {
					val = ic.getLum().get(i);
				}
//				g.setColor(new Color(0, 0, 0, 150));
//				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(i, i, i, 150));
				g.fillRect(x, 150-(val/f), 1, val/f);
				g.fillRect(x, 150, 1, 10);
			}
//			g.fillRect(x, 150-(val/f), 1, val/f);
//			g.fillRect(x, 150, 1, 10);
			x++;
		}
		g.setColor(Color.black);
		g.drawRect(0, 150, 254, 10);
		g.drawRect(0, 0, 254, 150);
		
		super.paint(g);
	}
	
}

class ThumbImageComp extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private int width, height;
	
	public ThumbImageComp(BufferedImage image, int width, int height) {
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image,0,0,width,height, null);
	}
	
}