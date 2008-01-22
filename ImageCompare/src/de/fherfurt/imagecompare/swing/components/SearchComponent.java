package de.fherfurt.imagecompare.swing.components;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.blogofbug.examples.yahooimagesearch.YahooImageSearch;
import com.yahoo.search.ImageSearchRequest;
import com.yahoo.search.ImageSearchResults;
import com.yahoo.search.SearchClient;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.swing.actions.YahooSearchAction;

public class SearchComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField textfield;
	
	private JButton button;
	
	private static SearchClient client;
	
	ImageSearchResults results;
	
	public SearchComponent() {
	client = new SearchClient("5myAFqbV34GNV1sI8eeYuoN8ifTOQCM7PWLGrdUUZfUrVdRkRVBBXz5innamFOrH");
	setBackground(null);
    setOpaque(false);
	setLayout(new GridLayout(0, 1));
	textfield = new JTextField(10);
	add(textfield);
	button = new JButton("search");
	button.setOpaque(false);
	button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			final String response = textfield.getText();
			new Thread(new Runnable() {
				@Override
				public void run() {
					ImageSearchRequest request = new ImageSearchRequest(response);            
			        request.setAdultOk(true);
			        request.setResults(50);
//			        request.setStart(BigInteger.valueOf(10));
			        try {
						results = client.imageSearch(request);
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
					
					ImageBase.getInstance().setImageBase(results);
				}}).start();
		}});
	add(button);
	setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search"));
	}
	
}
