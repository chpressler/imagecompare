package timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JComponent;


public class StartStopButton extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private boolean stop = true;
	
	private boolean pressed = false;
	
	private Dimension d;
	
	private Thread thr;

	public StartStopButton() {
		d = new Dimension(80, 25);
		setSize(d);
		setPreferredSize(d);
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				stop = !stop;
				if(!stop) {
					int sec = Integer.parseInt(TimerPanel.getInstance().getSec().getText()) * 1000;
					int min = (Integer.parseInt(TimerPanel.getInstance().getMin().getText()) * 60) * 1000;
					int hour = (((Integer.parseInt(TimerPanel.getInstance().getHour().getText()) * 60) * 60) * 1000);
					
					final long starttime = GregorianCalendar.getInstance().getTimeInMillis();
					final long stoptime = starttime + (sec + min + hour);

					thr = new Thread(new Runnable() {
						@Override
						public void run() {
							while(true) {
								long tt = stoptime - GregorianCalendar.getInstance().getTimeInMillis();
							    
							    final SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
								String s = f.format(tt - 3600000);
								
								int p = (int) ((100 * (GregorianCalendar.getInstance().getTimeInMillis() - starttime)) / (stoptime - starttime));
								TimerPanel.getInstance().setPercent(p);
								
							    TimerPanel.getInstance().setT(s);
							    TimerPanel.getInstance().repaint();
							    
							    if(tt <= 0) {
						    		TimerPanel.getInstance().setT("00:00:00");
						    		stop = true;
									TimerPanel.getInstance().repaint(); 
							    		try {
							    			Runtime.getRuntime().exec(
							    				"Shutdown.exe -s -t 00 -f");
							    		} catch (IOException e) {
							    			e.printStackTrace();
							    		} finally {
							    			thr.stop();
							    		}
							    }
							    
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}});
					thr.start();
					
				} else {
					thr.stop();
					TimerPanel.getInstance().setT("00:00:00");
					TimerPanel.getInstance().setPercent(0);
					TimerPanel.getInstance().repaint(); 
				}
				TimerPanel.getInstance().repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				pressed = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				pressed = false;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				pressed = true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				pressed = false;
				repaint();
			}});
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
	    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    Font f = new Font("Arial", Font.BOLD, 19);
		g.setFont(f);
		setSize(d);
		setPreferredSize(d);
		String s = "";
		if(stop) {
			s = "start";
		} else {
			s = "stop";
		}
		if(pressed) {
			g.setColor(Color.darkGray);
			g.fillRoundRect(0, 0, 80, 25, 8, 8);
			g.setColor(Color.black);
			g.fillRoundRect(0, 12, 80, 12, 8, 8);
			g.setColor(Color.lightGray);
			g.drawRoundRect(0, 0, 79, 24, 8, 8);
			g.drawString(s, (getWidth() / 2) - getStringWidth(s, g) / 2, (getHeight() / 2) + (g.getFontMetrics().getHeight() / 2) - 3);
		} else {
			g.setColor(Color.darkGray);
			g.fillRoundRect(0, 0, 80, 25, 8, 8);
			g.setColor(Color.black);
			g.fillRoundRect(0, 12, 80, 12, 8, 8);
			g.setColor(Color.white);
			g.drawRoundRect(0, 0, 79, 24, 8, 8);
			g.drawString(s, (getWidth() / 2) - getStringWidth(s, g) / 2, (getHeight() / 2) + (g.getFontMetrics().getHeight() / 2) - 3);
		}
	}
	
	private int getStringWidth(String s, Graphics g) {
		int w = 0;
		for(char c : s.toCharArray()) {
			w += g.getFontMetrics().charWidth(c);
		}
		return w;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
