package de.fherfurt.imagecompare.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.iptc.IptcDirectory;
import com.drew.metadata.iptc.IptcReader;

public class DetailsFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JScrollPane jsp;
	
	ExifPanel ep;

	public DetailsFrame(ImageComponent ic) {
		setTitle(ic.getPath());
		setAlwaysOnTop(true);
		setLayout(null);
		MyChart chart = new MyChart(ic);
		chart.setBounds(1, 1, 258, 162);
		add(chart);
 		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		sep.setBounds(1, 165, 258, 2);
		add(sep);
		ep = new ExifPanel(ic.getPath());
//		ep.setBounds(1, 170, 258, ep.getUK() + 10);
		System.out.println("sizeEP " + ep.getSize());
		jsp = new JScrollPane(ep);
		jsp.setBounds(1, 175, 255, 300);
		jsp.updateUI();
		add(jsp);
		setSize(263, 510);
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
		Metadata metadata = new Metadata();
		setLayout(null);
		File jpegFile = new File(path);
		try {
			if (path.startsWith("http")) {
				try {
					InputStream inputStream = new URL(path).openConnection()
							.getInputStream();
					metadata = JpegMetadataReader.readMetadata(inputStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				metadata = JpegMetadataReader.readMetadata(jpegFile);
			}
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			new ExifReader(jpegFile).extract(metadata);
//		} catch (JpegProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			new IptcReader(jpegFile).extract(metadata);
//		} catch (JpegProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// iterate through metadata directories
		Iterator directories = metadata.getDirectoryIterator();
		while (directories.hasNext()) {
			final Directory directory = (Directory) directories.next();
			// iterate through tags and print to System.out
			Iterator tags = directory.getTagIterator();
			while (tags.hasNext()) {
				final Tag tag = (Tag) tags.next();
				if(tag.getTagType() == ExifDirectory.TAG_WIN_KEYWORDS || tag.getTagType() == ExifDirectory.TAG_IMAGE_DESCRIPTION) {
					final int tt = tag.getTagType();
					JLabel tag_name = new JLabel(tag.getTagName());
					JTextField jtf = new JTextField();
					jtf.addKeyListener(new KeyListener() {
						@Override
						public void keyPressed(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void keyReleased(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void keyTyped(KeyEvent e) {
							System.out.println(((JTextField)e.getSource()).getText());
							directory.setString(tt, ((JTextField)e.getSource()).getText());
						}});
					jtf.setText(directory.getString(tt));
					tag_name.setBounds(2, uk, 100, 15);
					jtf.setBounds(150, uk, 100, 15);
					uk += 20;
					add(tag_name);
					add(jtf);
				} else {
					JLabel tag_name = new JLabel(tag.getTagName());
					JLabel tag_value = new JLabel();
					tag_value.setText(directory.getString(tag.getTagType()));
					tag_value.setToolTipText(directory.getString(tag.getTagType()));
					tag_name.setBounds(2, uk, 100, 15);
					tag_value.setBounds(150, uk, 100, 15);
					uk += 20;
					add(tag_name);
					add(tag_value);
				}
			}
		} 
		Directory exifDirectory = 
			metadata.getDirectory(ExifDirectory.class); 
			String cameraMake = exifDirectory.getString(ExifDirectory.TAG_MAKE); 
			String cameraModel = exifDirectory.getString(ExifDirectory.TAG_MODEL); 
			Directory iptcDirectory = metadata.getDirectory(IptcDirectory.class); 
			String caption = iptcDirectory.getString(IptcDirectory.TAG_CAPTION); 
		try {
			byte[] thumbnail = exifDirectory.getByteArray(ExifDirectory.TAG_THUMBNAIL_DATA);
//			Image im = getToolkit().createImage(thumbnail);
//			getGraphics().drawImage(im, 0, 0, 160, 120, null);
//			for(byte b : thumbnail) {
//				System.out.println(Integer.parseInt(Byte.toString(b)));
//			}
//			System.out.println();
		} catch (MetadataException e) {
			e.printStackTrace();
		}
		setPreferredSize(new Dimension(258, uk + 10));
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

	ButtonGroup group;
	JRadioButton rb;
	JRadioButton gb;
	JRadioButton bb;
	JRadioButton lb;
	
	ImageComponent ic;
	
	public MyChart(ImageComponent ic) {
		this.ic = ic;
		setPreferredSize(new Dimension(258, 160));
		setLayout(new FlowLayout());
		group = new ButtonGroup();
		rb = new JRadioButton("r");
		gb = new JRadioButton("g");
		bb = new JRadioButton("b");
		lb = new JRadioButton("l", true);
		group.add(rb);
		group.add(gb);
		group.add(bb);
		group.add(lb);
		
		rb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		gb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		bb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		lb.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					paint(getGraphics());
				}
			}});
		add(rb);
		add(gb);
		add(bb);
		add(lb);
	}
	
	@Override
	public void paint(Graphics g) {
		
		int f;
		if(ic.getPixelCount() <= 200000) {
			f = 20;
		} else if(ic.getPixelCount() > 200000 && ic.getPixelCount() <= 2000000) {
			f = 100;
		} else if(ic.getPixelCount() > 2000000 && ic.getPixelCount() <= 5000000) {
			f = 600;
		}
		else {
			f = 900;
		}
		
		System.out.println(ic.getPixelCount());
		
		g.clearRect(1, 1, 255, 199);
		
		g.drawImage((Image)ic.getImage(),0,0,256,160, null);
		
		int x = 0;
		for(int i = 0; i < 255; i++) {
			int val = 0;
			if(rb.isSelected()) {
				val = ic.getRed().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(i, 0, 0));
			}
			else if(gb.isSelected()) {
				val = ic.getGreen().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(0, i, 0));
			}
			else if(bb.isSelected()) {
				val = ic.getBlue().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(0, 0, i));
			}
			else {
				val = ic.getLum().get(i);
				g.setColor(Color.black);
				g.drawRect(x, 150-(val/f), 1, val/f);
				g.setColor(new Color(i, i, i));
			}
			g.fillRect(x, 150-(val/f), 1, val/f);
			g.fillRect(x, 150, 1, 10);
			x++;
		}
		g.setColor(Color.black);
		g.drawRect(0, 150, 256, 10);
		g.drawRect(0, 0, 256, 150);
		
		super.paint(g);
	}
	
}