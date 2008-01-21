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
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.swing.components.ImageComponent;
import de.fherfurt.imagecompare.swing.components.ImageViewerComponent;

public class ImageViewerDropPathTarget implements DropTargetListener {

	DropTarget target = null;
	
	ImageViewerComponent lt;

	public ImageViewerDropPathTarget(ImageViewerComponent l) {
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
	          BufferedImage image = null;
	          String s = "";
	          if(tr.getTransferData(flavors[i]).toString().startsWith("[")) {
	        	  s = tr.getTransferData(flavors[i]).toString().substring(1, tr.getTransferData(flavors[i]).toString().length()-1);
	          } else {
	        	  s = tr.getTransferData(flavors[i]).toString();
	          }
	          if(s.startsWith("http")) {
	        	  image = ImageIO.read(new URL(s));
	          } else {
	        	  image = ImageIO.read(new File(s));
	          }
	          lt.setImage(image, s);
			  SwingUtilities.updateComponentTreeUI(lt);
	          dtde.dropComplete(true);
	          return;
	        }
	      }
	      dtde.rejectDrop();
	    } catch (Exception e) {
	      e.printStackTrace();
	      dtde.rejectDrop();
	    }
	  
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

}
