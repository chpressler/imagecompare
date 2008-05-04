package timer;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class CloseButton extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private boolean pressed = false;
	
	private ResourceBundle icons = null;
	
	private TrayIcon trayIcon;
	
	public TrayIcon getTrayIcon() {
		return trayIcon;
	}
	
	public CloseButton() {
		setSize(20, 20);
		setPreferredSize(new Dimension(20, 20));
		
		icons = ResourceBundle.getBundle("icons.Icons");
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
//				System.exit(0);
				if (SystemTray.isSupported()) {
			          final SystemTray tray = SystemTray.getSystemTray();

			          Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/" + icons.getString("affe_tray")));
//			          Image image = Toolkit.getDefaultToolkit().getImage("Big_foot_16.png");
			          PopupMenu popup = new PopupMenu();
			          trayIcon = new TrayIcon(image, "MyJavaTimer - " + TimerPanel.getInstance().getT(), popup);

			          trayIcon.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Timer.getInstance().setVisible(true);
							tray.remove(trayIcon);
						}});
			          
			          MenuItem item = new MenuItem("Close MyJavaTimer");
			          item.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	tray.remove(trayIcon);
			            	System.exit(0);
			            }
			            
				  });
			          SwingUtilities.getRoot((JComponent) e.getSource()).setVisible(false);
			          popup.add(item);
			          try {
			            tray.add(trayIcon);
			          } catch (AWTException e1) {
			            System.err.println("Can't add to tray");
			          }
			        } else {
			          System.err.println("Tray unavailable");
			        }

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
		setSize(20, 20);
		setPreferredSize(new Dimension(20, 20));
		if(pressed) {
			g.setColor(Color.darkGray);
			g.fillRoundRect(0, 0, 20, 20, 8, 8);
			g.setColor(Color.black);
			g.fillRoundRect(0, 10, 20, 10, 8, 8);
			g.setColor(Color.lightGray);
			g.drawRoundRect(0, 0, 19, 19, 8, 8);
			g.drawString("°", (getWidth() / 2) - (g.getFontMetrics().charWidth('°') / 2), (getHeight() / 2) + (g.getFontMetrics().getHeight() / 2) - 3);
		} else {
			g.setColor(Color.darkGray);
			g.fillRoundRect(0, 0, 20, 20, 8, 8);
			g.setColor(Color.black);
			g.fillRoundRect(0, 10, 20, 10, 8, 8);
			g.setColor(Color.white);
			g.drawRoundRect(0, 0, 19, 19, 8, 8);
			g.drawString("°", (getWidth() / 2) - (g.getFontMetrics().charWidth('°') / 2), (getHeight() / 2) + (g.getFontMetrics().getHeight() / 2) - 3);
		}
	}

}
