package de.fherfurt.imagecompare.swing.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;

import javax.swing.tree.TreePath;

import org.w3c.dom.Node;

import com.blogofbug.swing.components.ReflectedImageLabel;

public class ReflectedImageDragSource implements DragSourceListener, DragGestureListener {

	DragSource source;

	DragGestureRecognizer recognizer;

	TransferableImage transferable;

	Node oldNode;

	ReflectedImageLabel image;

	public ReflectedImageDragSource(ReflectedImageLabel image, int actions) {
		this.image = image;
		source = new DragSource();
		recognizer = source.createDefaultDragGestureRecognizer(image,
				actions, this);
	}

	public void dragDropEnd(DragSourceDropEvent dsde) {
		// TODO Auto-generated method stub

	}

	public void dragEnter(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	public void dragExit(DragSourceEvent dse) {
		// TODO Auto-generated method stub

	}

	public void dragOver(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	public void dropActionChanged(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	public void dragGestureRecognized(DragGestureEvent dge) {
		System.out.println(image.getClass());
//		TreePath path = sourceTree.getSelectionPath();
//		if ((path == null)) {
//			return;
//		}
//		oldNode = (Node) path.getLastPathComponent();
		transferable = new TransferableImage();
		source.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
	}

}

class TransferableImage implements Transferable {
	
	public static DataFlavor IMAGE_FLAVOR = new DataFlavor(ReflectedImageLabel.class,
			"Image");

	DataFlavor flavors[] = { IMAGE_FLAVOR };
	
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public TransferableImage() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

//class TransferableTreeNode implements Transferable {
//	public static DataFlavor TREE_PATH_FLAVOR = new DataFlavor(TreePath.class,
//			"Tree Path");
//
//	DataFlavor flavors[] = { TREE_PATH_FLAVOR };
//
//	TreePath path;
//
//	public TransferableTreeNode(TreePath tp) {
//		path = tp;
//	}
//
//	public synchronized DataFlavor[] getTransferDataFlavors() {
//		return flavors;
//	}
//
//	public boolean isDataFlavorSupported(DataFlavor flavor) {
//		return (flavor.getRepresentationClass() == TreePath.class);
//	}
//
//	public synchronized Object getTransferData(DataFlavor flavor)
//			throws UnsupportedFlavorException, IOException {
//		if (isDataFlavorSupported(flavor)) {
//			return (Object) path;
//		} else {
//			throw new UnsupportedFlavorException(flavor);
//		}
//	}
//}