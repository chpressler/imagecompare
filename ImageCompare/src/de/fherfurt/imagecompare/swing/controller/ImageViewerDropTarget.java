package de.fherfurt.imagecompare.swing.controller;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.ImageComponent;
import de.fherfurt.imagecompare.swing.components.ImageViewerComponent;

public class ImageViewerDropTarget implements DropTargetListener {

	DropTarget target = null;
	
	ImageViewerComponent lt;

	public ImageViewerDropTarget(ImageViewerComponent l) {
		lt = l;
		target = new DropTarget(lt, this);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
	}

	public void dragExit(DropTargetEvent dte) {
	}

	public void dragOver(DropTargetDragEvent dtde) {
	}

	public void drop(DropTargetDropEvent dtde) {
		Point pt = dtde.getLocation();
	    DropTargetContext dtc = dtde.getDropTargetContext();

	    try {
	      Transferable tr = dtde.getTransferable();
	      DataFlavor[] flavors = tr.getTransferDataFlavors();
	      for (int i = 0; i < flavors.length; i++) {
	        if (tr.isDataFlavorSupported(flavors[i])) {
	          dtde.acceptDrop(dtde.getDropAction());
	          BufferedImage image = (BufferedImage) tr.getTransferData(flavors[i]);
//	          lt.setImage(image, );
	         
			  SwingUtilities.updateComponentTreeUI(lt);
	          dtde.dropComplete(true);
	          return;
	        }
	      }
	      dtde.rejectDrop();
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
	    	e.printStackTrace();
	    	dtde.rejectDrop();
	    }
	  
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

}
