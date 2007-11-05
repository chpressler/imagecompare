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
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.tree.TreePath;

import org.w3c.dom.Node;

import com.blogofbug.swing.components.ReflectedImageLabel;

public class ReflectedImageDragSource implements DragSourceListener, DragGestureListener {

	DragSource source;

	DragGestureRecognizer recognizer;

	TransferableImage transferable;

	Node oldNode;
	
	BufferedImage bufferedImage;

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
		bufferedImage = image.getBufferedImage();
		transferable = new TransferableImage(bufferedImage);
		source.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
	}

}

class TransferableImage implements Transferable {
	
	public static DataFlavor BUFFERED_IMAGE_FLAVOR = new DataFlavor(BufferedImage.class,
			"Image");

	DataFlavor flavors[] = { BUFFERED_IMAGE_FLAVOR };
	
	BufferedImage image;
	
	@Override
	public Object getTransferData(DataFlavor flavor)
//			throws UnsupportedFlavorException, IOException {
//		// TODO Auto-generated method stub
//		return null;
	throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor)) {
			return (Object) image;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	public TransferableImage(BufferedImage i) {
		image = i;
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		return (flavor.getRepresentationClass() == BufferedImage.class);
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