package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.swing.components.ImageCompareMenu;
import de.fherfurt.imagecompare.swing.components.ImageViewerComponent;

public class ImageCompare extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	final ImageViewerComponent ivc1;
	
	final ImageViewerComponent ivc2;
	
	private JLabel label;
	
	private final JLayeredPane lap;
	
	public ImageCompare() {	
		super("ImageCompare");
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
//			UIManager.installLookAndFeel("JGoodies Windows LaF",
//					"com.jgoodies.looks.windows.WindowsLookAndFeel");
//			UIManager.installLookAndFeel("JGoodies Plastic LaF",
//					"com.jgoodies.looks.plastic.PlasticLookAndFeel");
//			UIManager.installLookAndFeel("JGoodies Plastic3D LaF",
//					"com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
//			UIManager.installLookAndFeel("JGoodies PlasticXP LaF",
//					"com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		
//			PlasticLookAndFeel.setPlasticTheme(new DarkStar());

//			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
//			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
		} catch (Exception e1) {
			final JDialog jd = new JDialog();
			JOptionPane.showMessageDialog(jd, e1.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE);
		}
		
		lap = new JLayeredPane();
		lap.setLayout(new FlowLayout());
		label = new JLabel(new ImageIcon("test.jpg"));
		
		
		
		lap.add(label, (Integer) (JLayeredPane.DEFAULT_LAYER + (2 - 1) * 2));
		
		JPanel p = new JPanel();
		JButton button = new JButton("load");
		p.add(button, BorderLayout.WEST);
		
		ivc1 = new ImageViewerComponent();
		
		ivc2 = new ImageViewerComponent();
				
		final Loupe loupe = new Loupe(lap);
        Dimension size = loupe.getPreferredSize();
        loupe.setZoomLevel(5);
       
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ivc1.setImage( ImageIO.read(new File("test.jpg")) );
					
//					ivc2.setImage( ImageIO.read(new File("test.jpg")) );
//					ivc2.setImage( ImageIO.read(new URL("http://www.reitenimseewinkel.at/_img/_max/pferd_terry.jpg")));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				JPanel pan = new JPanel();
//				pan.update(ivc1.getG());
//				JFrame f = new JFrame();
//				f.add(pan);
//				f.setVisible(true);
				lap.add(loupe, (Integer) (JLayeredPane.DEFAULT_LAYER + 1 * 2 + 1));
				SwingUtilities.updateComponentTreeUI(lap);
			}});
		
		setLayout(new BorderLayout());
		p.add(ivc1, BorderLayout.EAST);
		p.add(ivc2, BorderLayout.CENTER);
		p.add(lap, BorderLayout.NORTH);
		
		add(p);
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		this.getRootPane().setJMenuBar(new ImageCompareMenu(this));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ImageCompare();
			}});
	}
	
}
