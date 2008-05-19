package de.fherfurt.imagecompare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.skin.SubstanceRavenLookAndFeel;

import de.fherfurt.imagecompare.swing.components.ControlPanel;
import de.fherfurt.imagecompare.swing.components.ImageCompareComponent;
import de.fherfurt.imagecompare.swing.components.ImageCompareMenu;
import de.fherfurt.imagecompare.swing.components.ImageCompareToolBar;
import de.fherfurt.imagecompare.swing.components.ImagePreviewComponent;
import de.fherfurt.imagecompare.swing.components.StatusBar;
import de.fherfurt.imagecompare.swing.uidelegates.PilotTabbedPaneUI;
import de.offis.faint.controller.MainController;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JSplitPane splitpane;
	
	public MainFrame() {
		super("ImageCompare");
		MainController.getInstance();
		try {
			
			UIManager.installLookAndFeel("JGoodies Windows LaF",
					"com.jgoodies.looks.windows.WindowsLookAndFeel");
			UIManager.installLookAndFeel("JGoodies Plastic LaF",
					"com.jgoodies.looks.plastic.PlasticLookAndFeel");
			UIManager.installLookAndFeel("JGoodies Plastic3D LaF",
					"com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
			UIManager.installLookAndFeel("JGoodies PlasticXP LaF",
					"com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
			
//			PlasticLookAndFeel.setPlasticTheme(new DarkStar());
//			PlasticLookAndFeel.setPlasticTheme(new Silver());
//			PlasticLookAndFeel.setPlasticTheme(new SkyGreen());
//			PlasticLookAndFeel.setPlasticTheme(new SkyBlue());
//			PlasticLookAndFeel.setPlasticTheme(new DesertYellow());
//			PlasticLookAndFeel.setPlasticTheme(new DesertRed());
//			PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
//			PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
//			PlasticLookAndFeel.setPlasticTheme(new com.jgoodies.looks.plastic.theme.BrownSugar());
//			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
			
			UIManager.setLookAndFeel(new SubstanceRavenLookAndFeel());
			
			SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
			
//			UIManager.put("TabbedPaneUI", PilotTabbedPaneUI.class);

			
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
		splitpane.setDividerLocation(0.5);
		splitpane.setOneTouchExpandable(true);
//		splitpane.setSize(800, 600);
		splitpane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent c) {
				splitpane.setDividerLocation(0.5);
			}
		});

		
		add(splitpane, BorderLayout.CENTER);
		add(StatusBar.getInstance(), BorderLayout.SOUTH);

		((JPanel) getGlassPane()).setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 1;
		gbc.weightx = Integer.MAX_VALUE;
		((JPanel) getGlassPane()).add(ControlPanel.getInstance(), gbc);
		
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
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
				jw.setVisible(false);
				jw.dispose();
			}});
		
	}
	
}