package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.yahoo.search.ImageSearchRequest;
import com.yahoo.search.ImageSearchResults;
import com.yahoo.search.SearchClient;
import com.yahoo.search.VideoSearchRequest;
import com.yahoo.search.VideoSearchResult;
import com.yahoo.search.VideoSearchResults;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;

public class YahooSearchAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private static SearchClient client;
	
	ImageSearchResults results;

	public YahooSearchAction() {
		client = new SearchClient("5myAFqbV34GNV1sI8eeYuoN8ifTOQCM7PWLGrdUUZfUrVdRkRVBBXz5innamFOrH");
		putValue(Action.NAME, "search");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("search")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("search"));
	}

	public void actionPerformed(ActionEvent arg0) {
		final String response = JOptionPane.showInputDialog(null,
				  "search for?",
				  "SearchString",
				  JOptionPane.QUESTION_MESSAGE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				VideoSearchRequest vrequest = new VideoSearchRequest("");   
				vrequest.setResults(20);
				vrequest.setAdultOk(true);
				try {
					VideoSearchResults vres = client.videoSearch(vrequest);
					for(VideoSearchResult vsr : vres.listResults()) {
						System.out.println(vsr.getClickUrl());
						
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				ImageSearchRequest request = new ImageSearchRequest(response);            
		        request.setAdultOk(true);
		        request.setResults(50);
//		        request.setStart(BigInteger.valueOf(10));
		        try {
					results = client.imageSearch(request);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				ImageBase.getInstance().setImageBase(results);
			}}).start();
	}

}
