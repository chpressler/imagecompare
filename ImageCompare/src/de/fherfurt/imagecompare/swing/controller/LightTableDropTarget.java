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

import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.swing.components.LightTableComponent;
import de.fherfurt.imagecompare.swing.components.ImageComponent;

public class LightTableDropTarget implements DropTargetListener {

	DropTarget target = null;
	
	LightTableComponent lt;

	public LightTableDropTarget(LightTableComponent l) {
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
	    System.out.println(dtc.getComponent().getClass());
	    System.out.println(pt.x + " - " + pt.y);

	    try {
	      Transferable tr = dtde.getTransferable();
	      DataFlavor[] flavors = tr.getTransferDataFlavors();
	      for (int i = 0; i < flavors.length; i++) {
	        if (tr.isDataFlavorSupported(flavors[i])) {
	          dtde.acceptDrop(dtde.getDropAction());
	          BufferedImage image = (BufferedImage) tr.getTransferData(flavors[i]);
	          
	          ImageComponent lab = new ImageComponent(image);
			  lab.setLocation(pt);
			  lt.getLayeredPane().add(lab, JLayeredPane.DRAG_LAYER);
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
