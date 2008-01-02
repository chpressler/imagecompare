package de.fherfurt.imagecompare.swing.controller;

import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import com.blogofbug.swing.components.ReflectedImageLabel;

public class ReflectedImageDragPathSource implements DragSourceListener, DragGestureListener {

	DragSource source;

	DragGestureRecognizer recognizer;

	TransferableImagePath transferable;
	
	ReflectedImageLabel ril;

	public ReflectedImageDragPathSource(ReflectedImageLabel ril, int actions) {
		this.ril = ril;
		source = new DragSource();
		recognizer = source.createDefaultDragGestureRecognizer(ril, actions, this);
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
		transferable = new TransferableImagePath( ril.getPath() );
		try {
			source.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
		} catch (Exception e) {
		}
	}
}