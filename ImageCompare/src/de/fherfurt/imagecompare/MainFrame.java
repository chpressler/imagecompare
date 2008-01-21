package de.fherfurt.imagecompare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import de.fherfurt.imagecompare.swing.components.FilterAndSortComponent;
import de.fherfurt.imagecompare.swing.components.FilterComponent;
import de.fherfurt.imagecompare.swing.components.ImageCompareComponent;
import de.fherfurt.imagecompare.swing.components.ImageCompareMenu;
import de.fherfurt.imagecompare.swing.components.ImageCompareToolBar;
import de.fherfurt.imagecompare.swing.components.ImagePreviewComponent;
import de.fherfurt.imagecompare.swing.components.StatusBar;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JSplitPane splitpane;
	
	public MainFrame() {
		super("ImageCompare");
		try {
			UIManager.installLookAndFeel("JGoodies Windows LaF",
					"com.jgoodies.looks.windows.WindowsLookAndFeel");
			UIManager.installLookAndFeel("JGoodies Plastic LaF",
					"com.jgoodies.looks.plastic.PlasticLookAndFeel");
			UIManager.installLookAndFeel("JGoodies Plastic3D LaF",
					"com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
			UIManager.installLookAndFeel("JGoodies PlasticXP LaF",
					"com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
			
			PlasticLookAndFeel.setPlasticTheme(new DarkStar());
//			PlasticLookAndFeel.setPlasticTheme(new Silver());
//			PlasticLookAndFeel.setPlasticTheme(new SkyGreen());
//			PlasticLookAndFeel.setPlasticTheme(new SkyBlue());
//			PlasticLookAndFeel.setPlasticTheme(new DesertYellow());
//			PlasticLookAndFeel.setPlasticTheme(new DesertRed());
//			PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
//			PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
//			PlasticLookAndFeel.setPlasticTheme(new com.jgoodies.looks.plastic.theme.BrownSugar());
			
			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
			
		} catch (Exception e1) {
			final JDialog jd = new JDialog();
			JOptionPane.showMessageDialog(jd, e1.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE);
		}

		SwingUtilities.updateComponentTreeUI(this);
		this.setSize(800, 600);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getRootPane().setJMenuBar(new ImageCompareMenu(this));
		this.setLayout(new BorderLayout());
		this.add(new ImageCompareToolBar(), BorderLayout.NORTH);
		
		splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new ImageCompareComponent(), new ImagePreviewComponent());
		splitpane.setBorder(BorderFactory.createEmptyBorder());
		splitpane.setDividerLocation(0.8);
		splitpane.setOneTouchExpandable(true);
		splitpane.setSize(800, 600);
		
//		add(new ControlPanel(), BorderLayout.WEST);
		add(splitpane, BorderLayout.CENTER);
		add(StatusBar.getInstance(), BorderLayout.SOUTH);

		((JPanel) getGlassPane()).setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 1;
		gbc.weightx = Integer.MAX_VALUE;
		
		FilterAndSortComponent fc = new FilterAndSortComponent();
		((JPanel) getGlassPane()).add(fc, gbc);
		
		this.setVisible(true);
	
	}

	public static void main(String[] args) {
		final JWindow jw = new JWindow();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int h = dim.height;
		int w = dim.width;
		JLabel label = new JLabel(new ImageIcon("splash.jpg"));
		jw.getContentPane().add(label);
		jw.setBounds(h / 2 - 150, w / 2 - 250, 500, 300);
		jw.setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
				jw.setVisible(false);
				jw.dispose();
			}});
		
	}
	
}