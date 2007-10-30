package com.blogofbug.swing.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class DefaultNavigationNodeCellRenderer extends JLabel implements ListCellRenderer {

    public DefaultNavigationNodeCellRenderer() {
        super();
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object object, int index, boolean selected, boolean hasFocus) {
        if (object instanceof NavigationNode){
            NavigationNode node = (NavigationNode) object;
            if (selected) {
                setBackground(new Color(238, 238, 255));
            } else {
                setBackground(Color.WHITE);
            }
            setText(node.getName());
            setIcon(node.getIcon());
            return this;            
        } else {
            setText(object.toString());
            setIcon(null);
            return this;
        }
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
    }
}
