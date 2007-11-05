package de.fherfurt.imagecompare;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import de.fherfurt.imagecompare.swing.components.ControlPanel;
import de.fherfurt.imagecompare.swing.components.HistogramPanel;
import de.fherfurt.imagecompare.swing.components.ImageCompareComponent;
import de.fherfurt.imagecompare.swing.components.ImageCompareMenu;
import de.fherfurt.imagecompare.swing.components.ImageCompareToolBar;
import de.fherfurt.imagecompare.swing.components.ImagePreviewComponent;
import de.fherfurt.imagecompare.swing.components.StatusBar;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
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
			
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
			
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
		
		JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new ImageCompareComponent(), new ImagePreviewComponent());
		splitpane.setBorder(BorderFactory.createEmptyBorder());
		splitpane.setDividerLocation(0.5);
		
		add(new ControlPanel(), BorderLayout.WEST);
		add(splitpane, BorderLayout.CENTER);
		add(new HistogramPanel(), BorderLayout.EAST);
		add(new StatusBar(), BorderLayout.SOUTH);

		this.setVisible(true);
	
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}});
	}
	
}