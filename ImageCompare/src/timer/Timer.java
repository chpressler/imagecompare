package timer;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Timer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int x = 0;
	
	private int y = 0;
	
	private static volatile Timer instance;

	public static synchronized Timer getInstance() {
		if(instance == null) {
			synchronized (Timer.class) {
				if(instance == null) {
					instance = new Timer();
				}
			}
		}
		return instance;
	}
	
	private Timer() {
		super("cp Timer");
		setUndecorated(true); 
		
//		getRootPane().setOpaque(false);
//		getRootPane().setBackground(new Color(0,0,0,0));
//		setBackground(new Color(0,0,0,0));
		
		setSize(300, 170);
		setPreferredSize(new Dimension(300, 170));
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int h = dim.height;
		int w = dim.width;
		setBounds(w / 2 - 150, h / 2 - 75, getWidth(), getHeight());
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getPoint().y > 152) {
					try {
						Desktop.getDesktop().mail(new URI("mailto", "christian.pressler@googlemail.com?subject=MyShutdownTimer", null)); //mailto: Anhang...!!!
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}	
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				TimerPanel.getInstance().setInfoString();
				TimerPanel.getInstance().repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p 
				= SwingUtilities.getRoot(((JFrame) e.getSource())).getLocation();
				
				Point lp = e.getPoint();
				int x1 = p.x + x;
				int y1 = p.y + y;
				lp.x = (lp.x + x1);
				lp.y = (lp.y + y1);
				
				SwingUtilities
				.getRoot(((JFrame) e.getSource()))
				.setLocation(lp.x - x - x, lp.y - y - y);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(e.getPoint().y > 152) {
					TimerPanel.getInstance().setInfo();
					TimerPanel.getInstance().setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else {
					TimerPanel.getInstance().setInfoString();
					TimerPanel.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				TimerPanel.getInstance().repaint();
			}});
		
		getContentPane().add(TimerPanel.getInstance());
		
		setAlwaysOnTop(true);
		
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	public static void main(String[] args) {
		new Timer();
	}
	
}