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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.fherfurt.imagecompare.swing.components.ImageComponent;

public class LightTableDragSource implements DragSourceListener, DragGestureListener {

	DragSource source;

	DragGestureRecognizer recognizer;
	
	ImageComponent ic;
	
	public LightTableDragSource(ImageComponent ic, int actions) {
		this.ic = ic;
		source = new DragSource();
		recognizer = source.createDefaultDragGestureRecognizer(ic, actions, this);
	}
	
	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {		
	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {		
	}

	@Override
	public void dragExit(DragSourceEvent dse) {		
	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {		
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {		
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		source.startDrag(dge, DragSource.DefaultCopyDrop, new Transferable(){
			
			public DataFlavor FILE_FLAVOR = new  DataFlavor(File.class, "File");

			DataFlavor flavors[] = { FILE_FLAVOR };

			String path;
			
			@Override
			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor)) {
					return (Object) new File(path);
				} else {
					throw new UnsupportedFlavorException(flavor);
				}
			}

			@Override
			public DataFlavor[] getTransferDataFlavors() {
				return flavors;
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return (flavor.getRepresentationClass() == File.class);
			}}, this);
	}

}
