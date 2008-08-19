package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;
import com.yahoo.search.ImageSearchRequest;
import com.yahoo.search.ImageSearchResults;
import com.yahoo.search.SearchClient;

import de.fherfurt.imagecompare.DerbyImageSearcher;
import de.fherfurt.imagecompare.ILocalImageSearcher;
import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;

public class SearchComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	private static SearchClient client;
	
	ImageSearchResults results;
	
	private javax.swing.JButton jButton1;
	
	private javax.swing.JCheckBox jCheckBox1;
	
	private javax.swing.JCheckBox jCheckBox2;
	
	private javax.swing.JCheckBox jCheckBox3;
	
	private javax.swing.JCheckBox jCheckBox4;
	
	private javax.swing.JCheckBox jCheckBox5;
	
	private javax.swing.JTextField jTextField1;
	
	private boolean yahoo, google, torrent, gnutella = false;
	
	private boolean local = true;
	
	private ILocalImageSearcher localSearcher;
	
	public SearchComponent() {
		
		client = new SearchClient("5myAFqbV34GNV1sI8eeYuoN8ifTOQCM7PWLGrdUUZfUrVdRkRVBBXz5innamFOrH");
		setBackground(null);
	    setOpaque(false);
	    
	    setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.white, Color.lightGray), "Search"));
		
	    localSearcher = new DerbyImageSearcher();
	    
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox5.setSelected(true);
        jCheckBox1.setOpaque(false);
        jCheckBox1.setBackground(null);
        jCheckBox2.setOpaque(false);
        jCheckBox2.setBackground(null);
        jCheckBox3.setOpaque(false);
        jCheckBox3.setBackground(null);
        jCheckBox4.setOpaque(false);
        jCheckBox4.setBackground(null);
        jCheckBox5.setOpaque(false);
        jCheckBox5.setBackground(null);
        
        jCheckBox1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if( ((JCheckBox) e.getSource()).isSelected() ) {
					yahoo = true;
				} else {
					yahoo = false;
				}
			}});
        jCheckBox2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if( ((JCheckBox) e.getSource()).isSelected() ) {
					google = true;
				} else {
					google = false;
				}
			}});
        jCheckBox3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if( ((JCheckBox) e.getSource()).isSelected() ) {
					torrent = true;
				} else {
					torrent = false;
				}
			}});
        jCheckBox4.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if( ((JCheckBox) e.getSource()).isSelected() ) {
					gnutella = true;
				} else {
					gnutella = false;
				}
			}});
        jCheckBox5.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if( ((JCheckBox) e.getSource()).isSelected() ) {
					local = true;
				} else {
					local = false;
				}
			}});

        jButton1.setText("search");
        jButton1.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			//TODO -> erst alle laufenden Threads beenden! (ThreadWorker???)
    			final String searchstring = jTextField1.getText();
    			if(yahoo) {
    			new Thread(new Runnable() {
    				@Override
    				public void run() {
    					ImageSearchRequest request = new ImageSearchRequest(searchstring);            
    			        request.setAdultOk(true);
    			        request.setResults(50);
//    			        request.setStart(BigInteger.valueOf(10));
    			        try {
    						results = client.imageSearch(request);
    					} catch (Exception e1) {
    						JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e1.getMessage());
    						e1.printStackTrace();
    					} 
    					ImageBase.getInstance().setImageBase(results);
    				}}).start();
    			}
    			if(google) {
    				new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								ArrayList<String> urls = new ArrayList<String>();
								PicasawebService myService = new PicasawebService(
										"exampleCo-exampleApp-1");
								
								URL feedUrl = new URL(
										"http://picasaweb.google.com/data/feed/api/all?q=" + jTextField1.getText());
								
								Query myQuery = new Query(feedUrl);
//								myQuery.setMaxResults(50);
								
								PhotoFeed resultFeed = myService.query(myQuery,
										PhotoFeed.class);
								
								List<PhotoEntry> photos = new ArrayList<PhotoEntry>();
								
								for (GphotoEntry entry : resultFeed
										.getEntries()) {
									
									GphotoEntry adapted = entry
											.getAdaptedEntry();
									if (adapted instanceof PhotoEntry) {
										photos.add((PhotoEntry) adapted);
									}
								}
								for (PhotoEntry pho : photos) {
									urls.add(pho.getMediaContents().get(0).getUrl().toString());
								}
								ImageBase.getInstance().setImageBase(urls);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
								e.printStackTrace();
							}
						}}).start();
    			}
    			if(torrent) {
    				new Thread(new Runnable() {
						@Override
						public void run() {
							
						}}).start();
    			}
    			if(gnutella) {
    				new Thread(new Runnable() {
						@Override
						public void run() {
							
						}}).start();
    			}
    			if(local) {
    				new Thread(new Runnable() {
    					@Override
        				public void run() {
    						try {
								ArrayList<String> results = localSearcher
										.getResults(searchstring);
								for (String p : results) {
									File f = null;
									try {
										f = new File(p);
									} catch (Exception e) {
										JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
										e.printStackTrace();
									}
									if (f == null || !f.exists()) {
										localSearcher.deleteImage(p);
									} else {
										ImageBase.getInstance().setImageBase(f);
									}
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
								e.printStackTrace();
								StatusBar.getInstance().deactivateProgressBar();
							}
        				}}).start();
    			}
    		}});

        jCheckBox1.setText("Yahoo");
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jCheckBox2.setText("Google");
        jCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jCheckBox3.setText("Torrent");
        jCheckBox3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        
        jCheckBox4.setText("Gnutella");
        jCheckBox4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        
        jCheckBox5.setText("Local");
        jCheckBox5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox5.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

	}
	
}
