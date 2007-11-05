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

import javax.swing.JLayeredPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.w3c.dom.Node;

import de.fherfurt.imagecompare.swing.components.LightTableComponent;

public class LightTableDropTarget implements DropTargetListener {

	DropTarget target = null;

	JLayeredPane lt = null;

	public LightTableDropTarget(JLayeredPane l) {
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
		Transferable tr = dtde.getTransferable();
	      DataFlavor[] flavors = tr.getTransferDataFlavors();
	      for (int i = 0; i < flavors.length; i++) {
	        if (tr.isDataFlavorSupported(flavors[i])) {
	        	System.out.println(flavors[i]);
	        }
	      }
//		Point pt = dtde.getLocation();
//	    DropTargetContext dtc = dtde.getDropTargetContext();
//	    JTree tree = (JTree) dtc.getComponent();
//	    TreePath parentpath = tree.getClosestPathForLocation(pt.x, pt.y);
//	    
//	    Node parent = (Node) parentpath
//	        .getLastPathComponent();
////	    if (!parent.hasChildNodes()) {
////	      dtde.rejectDrop();
////	      return;
////	    }
//
//	    try {
//	      Transferable tr = dtde.getTransferable();
//	      DataFlavor[] flavors = tr.getTransferDataFlavors();
//	      for (int i = 0; i < flavors.length; i++) {
//	        if (tr.isDataFlavorSupported(flavors[i])) {
//	          dtde.acceptDrop(dtde.getDropAction());
//	          TreePath p = (TreePath) tr.getTransferData(flavors[i]);
//	          Node node = (Node) p.getLastPathComponent();
//	          
//	          dtde.dropComplete(true);
//	          return;
//	        }
//	      }
//	      dtde.rejectDrop();
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	      dtde.rejectDrop();
//	    }
	  
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

}
