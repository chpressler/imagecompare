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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.PreviewThumbnailComponent;
import de.fherfurt.imagecompare.swing.components.StatusBar;
import de.fherfurt.imagecompare.util.ICUtil;

public class PreviewThumbnailComponentExternalDropTarget implements
		DropTargetListener {

	DropTarget target = null;

	PreviewThumbnailComponent ptc;

	public PreviewThumbnailComponentExternalDropTarget(
			PreviewThumbnailComponent ptc) {
		this.ptc = ptc;
		target = new DropTarget(ptc, this);
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		Point pt = dtde.getLocation();
		DropTargetContext dtc = dtde.getDropTargetContext();
		try {
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				if (tr.isDataFlavorSupported(flavors[i])) {
					dtde.acceptDrop(dtde.getDropAction());
					String s = tr.getTransferData(flavors[i]).toString()
							.substring(
									1,
									tr.getTransferData(flavors[i]).toString()
											.length() - 1);
					String[] sa = s.split(",");
					for (String st : sa) {
						final File f = new File(st.trim());
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									ImageBase.getInstance().setImageBase(f);
								} catch (IOException e) {
									JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
									e.printStackTrace();
								}
							}
						}).start();
					}
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

}
