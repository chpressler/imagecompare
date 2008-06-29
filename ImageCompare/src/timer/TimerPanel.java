package timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class TimerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField hour;
	
	private JTextField min;
	
	private JTextField sec;
	
	private BufferedImage image;
	
	private StartStopButton ssb;
	
	private ResourceBundle icons = null;
	
	private CloseButton cb;
	
	private JRadioButton shutdown;
	
	private JRadioButton alarm;
	
	private ButtonGroup bg;
	
	private int percent = 0;
	
	private String info = "christian.pressler@gmail.com - Java, meine Perle °";
	
	private String infostr = "info";
	
	public void setInfoString() {
		infostr = "info";
	}
	
	public void setInfo() {
		infostr = info;
	}
	
	public void setPercent(int p) {
		this.percent = p;
	}
	
	public CloseButton getCloseButton() {
		return cb;
	}
	
	private String t = "00:00:00";
	
	public void setT(String t) {
		this.t = t;
	}
	
	public String getT() {
		return t;
	}
	
	private static volatile TimerPanel instance;
	
	public static synchronized TimerPanel getInstance() {
		if(instance == null) {
			synchronized (TimerPanel.class) {
				if(instance == null) {
					instance = new TimerPanel();
				}
			}
		}
		return instance;
	}
	
	private TimerPanel() {
		
		setLayout(null);
		
		icons = ResourceBundle.getBundle("icons.Icons");
		
		try {
			InputStream file = this.getClass().getResource("/icons/" + icons.getString("affe")).openStream();
//			InputStream file = this.getClass().getClassLoader().getResource("/icons/" + icons.getString(imageName)).openStream();
			image = ImageIO.read(file);
//			image = ImageIO.read(new File("Big_foot_128.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
//		shutdown = new JRadioButton("shutdown", true);
//		shutdown.setOpaque(false);
//		alarm = new JRadioButton("alarm");
//		alarm.setOpaque(false);
//		bg = new ButtonGroup();
//		bg.add(shutdown);
//		bg.add(alarm);
//		shutdown.setBounds(10, 70, 90, 25);
//		alarm.setBounds(10, 110, 60, 25);
//		add(shutdown);
//		add(alarm);
		
		hour = new JTextField("0");
		hour.setBackground(Color.darkGray);
		hour.setForeground(Color.white);
		hour.setSize(new Dimension(30, 25));
		hour.setPreferredSize(new Dimension(30, 25));
		hour.setBounds(new Rectangle(80, 110, hour.getWidth(), hour.getHeight()));
		
		min = new JTextField("0");
		min.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			@Override
			public void keyTyped(KeyEvent e) {
//				System.out.println(  Integer.parseInt( (String) e.getKeyChar() ) );
			}});
		min.setBackground(Color.darkGray);
		min.setForeground(Color.white);
		min.setSize(new Dimension(30, 25));
		min.setPreferredSize(new Dimension(30, 25));
		min.setBounds(new Rectangle(120, 110, min.getWidth(), min.getHeight()));
		sec = new JTextField("0");
		sec.setBackground(Color.darkGray);
		sec.setForeground(Color.white);
		sec.setSize(new Dimension(30, 25));
		sec.setPreferredSize(new Dimension(30, 25));
		sec.setBounds(new Rectangle(160, 110, sec.getWidth(), sec.getHeight()));
		
//		setOpaque(false);
		setBackground(Color.darkGray);
		
		cb = new CloseButton();
		cb.setBounds(new Rectangle(5, 5, 20, 20));
		add(cb);
		add(hour);
		add(min);
		add(sec);
		
		ssb = new StartStopButton();
		ssb.setBounds(205, 110, ssb.getWidth(), ssb.getHeight());
		add(ssb);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2D = (Graphics2D)g;
	    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    Font f = new Font("Arial", Font.ITALIC, 30);
	    g.setColor(Color.black);
		g.fillRect(0, getHeight() / 4, getWidth(), getHeight());
		
		g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), this);
		
		g.setFont(f);
		g.setColor(Color.gray);
		g.drawString("cp Timer", 170, 33);
		g.setColor(Color.lightGray);
		g.drawString("cp Timer", 167, 30);
		
		if(ssb.isStop()) {
			g.setColor(Color.darkGray);
			g.drawString(t, 150, 75);
		} else {
			g.setColor(Color.white);
			g.drawString(t, 150, 75);
		}
		g.setColor(Color.gray);
		g.fillRect(150, 85, percent * 120 / 100, 10);
		g.drawRect(150, 85, 120, 10);
		g.setColor(Color.lightGray);
		f = new Font("Arial", Font.BOLD, 14);
		g.setFont(f);
		g.drawString("H", 90, 147);
		g.drawString("M", 130, 147);
		g.drawString("S", 170, 147);
		
		f = new Font("Arial", Font.PLAIN, 12);
		g.setFont(f);
		g.drawLine(0, 152, 300, 152);
		g.drawString(infostr, 5, 165);
		
		g.setColor(Color.lightGray);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}

	public JTextField getHour() {
		return hour;
	}

	public void setHour(JTextField hour) {
		this.hour = hour;
	}

	public JTextField getMin() {
		return min;
	}

	public void setMin(JTextField min) {
		this.min = min;
	}

	public JTextField getSec() {
		return sec;
	}

	public void setSec(JTextField sec) {
		this.sec = sec;
	}
	
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		Graphics2D g2D = (Graphics2D)g;
//	    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//	    Font f = new Font("Arial", Font.ITALIC, 30);
//		g.setFont(f);
//		g.setColor(Color.gray);
//		g.drawString("Spungi-Timer", 103, 33);
//		g.setColor(Color.lightGray);
//		g.drawString("Spungi-Timer", 100, 30);
//		g.setColor(Color.black);
//		g.fillRect(0, getHeight() / 4, getWidth(), getHeight());
//		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
//	}
	
}
