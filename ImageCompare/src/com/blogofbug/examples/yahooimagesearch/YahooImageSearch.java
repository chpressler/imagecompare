/*
 * YahooImageSearch.java
 *
 * Created on April 12, 2007, 9:28 AM
 *
 * Copyright 2006-2007 Nigel Hughes
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at http://www.apache.org/
 * licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.blogofbug.examples.yahooimagesearch;

import com.blogofbug.swing.SwingBugUtilities;
import com.blogofbug.swing.components.JCarosel;
import com.blogofbug.utility.ImageUtilities;
import com.yahoo.search.ImageSearchRequest;
import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;
import com.yahoo.search.SearchClient;
import com.yahoo.search.SearchException;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Hashtable;
import javax.swing.SwingUtilities;

/**
 *
 * @author nigel
 */
public class YahooImageSearch implements ActionListener {
    JCarosel        carousel;
    int             timerCounter=0;
    int             page = 0;
    int             resultsPerPage=0;
    ImageSearchResults results = null;
    String          lastSearch=null;
    private static SearchClient client = new SearchClient("5myAFqbV34GNV1sI8eeYuoN8ifTOQCM7PWLGrdUUZfUrVdRkRVBBXz5innamFOrH");
    String          searchString=null;
    Hashtable<Component, ImageSearchResult> cachedResults = new Hashtable<Component, ImageSearchResult>();
    String          status="Inactive";
    Thread          searchThread  = null;
    
    /** Creates a new instance of YahooImageSearch */
    public YahooImageSearch(JCarosel carousel,int resultsPerPage) {
        this.carousel=carousel;
        timerCounter=0;
        this.resultsPerPage = resultsPerPage;
        SwingBugUtilities.addTimerListener(this);
    }
    
    public void stopSearch(){
        if (searchThread!=null){
            searchThread.interrupt();
            searchThread=null;
        }        
    }
    
    public void searchFor(String search){
        page = 0;
        searchString = search;
        lastSearch=searchString;
        cachedResults.clear();
        status="Searching...";
        stopSearch();
        SwingBugUtilities.addTimerListener(this);
    }

    public String getSearchString() {
        return lastSearch;
    }
    
    public void showPage(int page){
        //Make it remove them all, and update the page
        this.page=page;
        searchString = lastSearch;
        status="Getting Page "+page+"...";
        stopSearch();
        SwingBugUtilities.addTimerListener(this);
    }

    public ImageSearchResult getResultFor(Component component){
        return cachedResults.get(component);
    }

    public String getStatus() {
        return status;
    }

    public int getResultsOnPage() {
        if (results==null){
            return resultsPerPage;
        }
        return Math.min(resultsPerPage,results.getTotalResultsReturned().intValue());
    }
    

    
    private void doSearch(){
        ImageSearchRequest request = new ImageSearchRequest(searchString);            
        request.setAdultOk(false);
        request.setResults(Math.min(resultsPerPage,50));
        request.setStart(BigInteger.valueOf((long) resultsPerPage*page+1));
        searchString=null;
        try {
            // Execute the search.
            status="Requesting results...";
            System.out.println("Doing Search");

            results = client.imageSearch(request);
            showPage();
        } catch (IOException e) {
            // Most likely a network exception of some sort.
            System.err.println("Error calling Yahoo! Search Service: " +
                    e.toString());
            e.printStackTrace(System.err);
        } catch (SearchException e) {
            // An issue with the XML or with the service.
            System.err.println("Error calling Yahoo! Search Service: " +
                    e.toString());
            e.printStackTrace(System.err);
        }        
    }
        
    private boolean validResultThumbnail(String url){
        
        //Don't add it if it's there
        for (Component component : carousel.getComponents()){
            ImageSearchResult result = cachedResults.get(component);
            if (result.getThumbnail().getUrl().equals(url)){
                return false;
            }
        }
        
        if (results==null){
            return false;
        }
        
        //Don't add it if it's not in the current result set
        for (ImageSearchResult result : results.listResults()){
            if (result.getThumbnail().getUrl().equals(url)){
                return true;
            }
        }
        
        
        return false;
    }
    
    private void showPage(){
        System.out.println("Loading Thumbnails");
        for (int i = 0; i < Math.min(resultsPerPage,results.listResults().length); i++) {
            final ImageSearchResult result = results.listResults()[i];
            final BufferedImage image = loadSquareImage(result.getThumbnail().getUrl());
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (validResultThumbnail(result.getThumbnail().getUrl())){
                        cachedResults.put(carousel.add(image,result.getTitle()),result);
                        status="Loading thumbnails ("+carousel.getComponentCount()+" of "+getResultsOnPage()+")";
                    }
                }
            });
        }
        System.out.println("Thumbnails done");
        
    }

    public int getPage() {
        return page;
    }
    

    
    public void actionPerformed(ActionEvent actionEvent) {
        timerCounter++;
        if (timerCounter<5){
            return;
        }
        timerCounter = 0;
        if (carousel.getComponentCount()>0){
            carousel.remove(carousel.getComponent(carousel.getComponentCount()-1));            
        } else {
            SwingBugUtilities.removeTimerListener(this);
            //Invoke search, or move to next page...
            searchThread = new Thread(new Runnable() {
                public void run() {
                    doSearch();
                }
            });
            searchThread.start();
        }
    }
    
    private BufferedImage loadSquareImage(String url){
        BufferedImage source = ImageUtilities.loadCompatibleImage(url);
        BufferedImage square = null;
        boolean       wider = source.getWidth()>source.getHeight() ? true : false;
        if (wider){
            square = ImageUtilities.createCompatibleImage(source.getWidth(), source.getWidth());
        } else {
            square = ImageUtilities.createCompatibleImage(source.getHeight(),source.getHeight());
        }
        Graphics2D graphics = square.createGraphics();
        
        graphics.drawImage(source,(square.getWidth()-source.getWidth())/2,(square.getHeight()-source.getHeight())/2,null);
        graphics.dispose();
        return square;
    }
}
