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

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;

public class ThumbnailDragSource implements DragSourceListener, DragGestureListener {

	DragSource source;

	DragGestureRecognizer recognizer;

	TransferableImagePath transferable;
	
	ImageThumbnailComponent th;

	public ThumbnailDragSource(ImageThumbnailComponent th, int actions) {
		this.th = th;
		source = new DragSource();
		recognizer = source.createDefaultDragGestureRecognizer(th, actions, this);
	}

	public void dragDropEnd(DragSourceDropEvent dsde) {
		
	}

	public void dragEnter(DragSourceDragEvent dsde) {
		
	}

	public void dragExit(DragSourceEvent dse) {
		
	}

	public void dragOver(DragSourceDragEvent dsde) {
		
	}

	public void dropActionChanged(DragSourceDragEvent dsde) {
		
	}

	public void dragGestureRecognized(DragGestureEvent dge) {
		transferable = new TransferableImagePath(th.getPath());
		try {
			source.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
		} catch (Exception e) {
		}
	}

}

class TransferableImagePath implements Transferable {

	public DataFlavor STRING_FLAVOR = new DataFlavor(String.class, "String");

	DataFlavor flavors[] = { STRING_FLAVOR };

	String path;

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor)) {
			return (Object) path;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	public TransferableImagePath(String s) {
		path = s;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavor.getRepresentationClass() == String.class);
	}

}