package de.fherfurt.imagecompare.swing.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;
import com.google.gdata.data.photos.UserFeed;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.models.ICPicasaComboBoxModel;

public class ExportFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static JTextField path;
	
	private JProgressBar pb;
	
	private static volatile ExportFrame instance;
	
	public static synchronized ExportFrame getInstance() {
		if(instance == null) {
			synchronized (ExportFrame.class) {
				if(instance == null) {
					instance = new ExportFrame();
				}
			}
		}
		return instance;
	}
	
	// Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    private ButtonGroup bg = new ButtonGroup();
    // End of variables declaration

	private ExportFrame() {
		super("Export");
		pb = new JProgressBar();
		
		setSize(new Dimension(300, 500));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setLayout(new GridLayout(2, 1));
		GridBagLayout gbl = new GridBagLayout();
//		this.setLayout(gbl);
		
		JPanel fsPanel = new JPanel();
		fsPanel.setBorder(new TitledBorder("FileSystem"));
		path = new JTextField(30);
		fsPanel.add(path);
		JButton fc = new JButton("chooseFolder");
		fc.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            int status = fileChooser.showSaveDialog(null);
	            if (status == JFileChooser.APPROVE_OPTION) {
	            	path.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            } else {
	            	return;
	            }
				
			}});
		fsPanel.add(fc);
		JButton exportfc = new JButton("export");
		exportfc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
//						ImageBase.getInstance().exportToDir("C:/export");
						pb.setIndeterminate(true);
			            ImageBase.getInstance().exportToDir(path.getText());
			            pb.setIndeterminate(false);
					}}).start();
			}});
		fsPanel.add(exportfc);
		fsPanel.add(pb);
		
		JPanel picasaPanel = new JPanel();
		picasaPanel.setBorder(new TitledBorder("Piasa"));
		 jRadioButton1 = new javax.swing.JRadioButton("new Album", true);
	        jRadioButton2 = new javax.swing.JRadioButton();
	        jTextField1 = new javax.swing.JTextField();
	        jComboBox1 = new javax.swing.JComboBox();
	        jButton1 = new javax.swing.JButton();
	        jProgressBar1 = new javax.swing.JProgressBar();

	        setName("Form"); // NOI18N

	        jRadioButton1.setName("jRadioButton1"); // NOI18N

	        jRadioButton2.setText("choose Album"); // NOI18N
	        jRadioButton2.setName("jRadioButton2"); // NOI18N
	        
	        bg.add(jRadioButton1);
	        bg.add(jRadioButton2);

	        jTextField1.setText(""); // NOI18N
	        jTextField1.setName("jTextField1"); // NOI18N
		try {
			jComboBox1.setModel(new ICPicasaComboBoxModel());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
			e.printStackTrace();
		}
	        jComboBox1.setName("jComboBox1"); // NOI18N

	        jButton1.setText("export"); // NOI18N
	        
	        jButton1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								jProgressBar1.setIndeterminate(true);
//								ArrayList<String> urls = new ArrayList<String>();
//								PicasawebService myService = new PicasawebService(
//										"exampleCo-exampleApp-1");
//								
//								myService.setUserCredentials(ResourceHandler.getInstance().getStrings().getString("user"), ResourceHandler.getInstance().getStrings().getString("pw"));
//								
//								URL albumAndPhotosUrl = new URL("http://picasaweb.google.com/data/feed/api/user/christian.pressler/album/" + e.getItem() + "?kind=photo");
//								UserFeed myUserFeed = myService.getFeed(albumAndPhotosUrl, UserFeed.class);
//								System.out.println(myUserFeed.getAlbumEntries().get(0).getTitle().getPlainText());
//								for(AlbumEntry ae : myUserFeed.getAlbumEntries()) {
//									
//								}
								
//								Query myQuery = new Query(albumAndPhotosUrl);
//								myQuery.setMaxResults(50);
								
//								PhotoFeed resultFeed = myService.query(myQuery,
//										PhotoFeed.class);
//								
//								List<PhotoEntry> photos = new ArrayList<PhotoEntry>();
//								
//								for (GphotoEntry entry : resultFeed
//										.getEntries()) {
//									
//									GphotoEntry adapted = entry
//											.getAdaptedEntry();
//									if (adapted instanceof PhotoEntry) {
//										photos.add((PhotoEntry) adapted);
//									}
//								}
//								for (PhotoEntry pho : photos) {
//									urls.add(pho.getMediaContents().get(0).getUrl().toString());
//								}
//								ImageBase.getInstance().setImageBase(urls);
								if(jRadioButton1.isSelected()) {
									ImageBase.getInstance().exportToPicasa(jTextField1.getText(), false);
								} else {
									ImageBase.getInstance().exportToPicasa(jComboBox1.getSelectedItem().toString(), true);
								}
					            
					          
								jProgressBar1.setIndeterminate(false);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
								e.printStackTrace();
							}
						}}).start();
				}});
	        
	        jButton1.setName("jButton1"); // NOI18N

	        jProgressBar1.setName("jProgressBar1"); // NOI18N

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(picasaPanel);
	        picasaPanel.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(41, 41, 41)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(jRadioButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                        .addGap(18, 18, 18)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
	                .addContainerGap(75, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(18, 18, 18)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jRadioButton1)
	                    .addComponent(jRadioButton2))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addGap(18, 18, 18)
	                .addComponent(jButton1)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(161, Short.MAX_VALUE))
	        );
		
//		JPanel p = new JPanel();
//		p.add(p);
//		addComponent(p, gbl, fsPanel, 0, 0, 1, 1, 1.0, 0.05);
//		addComponent(p, gbl, picasaPanel, 0, 1, 2, 2, 1.0, 1.0);
		
		add(fsPanel);
		add(picasaPanel);
		setVisible(true);
	}
	
	private static void addComponent(JPanel p, GridBagLayout gbl,
			Component c, int x, int y, int width, int height, double weightx,
			double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(c, gbc);
		p.add(c);
	}
	
	@Override
	public void setVisible(boolean b) {
		try {
			jComboBox1.setModel(new ICPicasaComboBoxModel());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
			e.printStackTrace();
		}
		super.setVisible(b);
	}

}
