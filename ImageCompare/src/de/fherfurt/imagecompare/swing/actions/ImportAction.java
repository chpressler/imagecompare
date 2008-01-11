package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;
import java.math.BigInteger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.yahoo.search.ImageSearchRequest;
import com.yahoo.search.ImageSearchResults;
import com.yahoo.search.SearchClient;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;

public class ImportAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private static SearchClient client;
	
	ImageSearchResults results;

	public ImportAction() {
		
		client = new SearchClient("5myAFqbV34GNV1sI8eeYuoN8ifTOQCM7PWLGrdUUZfUrVdRkRVBBXz5innamFOrH");
		
		putValue(Action.NAME, "import");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("import")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("import"));
	}

	public void actionPerformed(ActionEvent arg0) {
		final String response = JOptionPane.showInputDialog(null,
				  "search for?",
				  "SearchString",
				  JOptionPane.QUESTION_MESSAGE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				ImageSearchRequest request = new ImageSearchRequest(response);            
		        request.setAdultOk(true);
		        request.setResults(50);
//		      request.setStart(BigInteger.valueOf(10));
		        
		        try {
					results = client.imageSearch(request);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				ImageBase.getInstance().setImageBase(results);
			}}).start();
	}

}
