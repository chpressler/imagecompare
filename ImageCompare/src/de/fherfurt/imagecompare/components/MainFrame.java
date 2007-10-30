package de.fherfurt.imagecompare.components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import de.fherfurt.imagecompare.ResourceHandler;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MainFrame() {
		super("Sirius Client");
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
		
		JPanel startPanel = new JPanel();
		startPanel.setBackground(Color.WHITE);
		JLabel logolabel = new JLabel(new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("logo")));
		startPanel.add(logolabel);
		
		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new MenuPanel(), startPanel);
		splitpane.setBorder(BorderFactory.createEmptyBorder());
		splitpane.setDividerLocation(0.8);
		
		this.add(splitpane, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
	
}