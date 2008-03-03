package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;

import com.sun.j3d.utils.applet.MainFrame;

import de.fherfurt.imagecompare.Attributes;
import de.fherfurt.imagecompare.ImageBase;

public class SortComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private javax.swing.JButton jButton1;

	private javax.swing.JComboBox jComboBox1;

	private javax.swing.JComboBox jComboBox2;

	private javax.swing.JRadioButton jRadioButton1;

	private javax.swing.JRadioButton jRadioButton2;

	private javax.swing.JRadioButton jRadioButton3;

	private javax.swing.JRadioButton jRadioButton4;
	
	private ButtonGroup orderGroup = new ButtonGroup();
	
	private ButtonGroup typeGroup = new ButtonGroup();
	
	private boolean descenting = true;
	
	private String sortBy = Attributes.values()[0].toString();
	
	private boolean sorted = false;
	
	public boolean sorted() {
		return sorted;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	
	public boolean isDescenting() {
		return descenting;
	}
	    
	public SortComponent() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.white, Color.lightGray), "Sort"));
		setBackground(null);
	    setOpaque(false);

        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jRadioButton1 = new javax.swing.JRadioButton("", true);
        jRadioButton1.setBackground(null);
	    jRadioButton1.setOpaque(false);
	    jRadioButton1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					jComboBox1.setEnabled(false);
					jComboBox2.setEnabled(true);
				} else {
					jComboBox1.setEnabled(true);
					jComboBox2.setEnabled(false);
				}
				SwingUtilities.getRoot(((JRadioButton) e.getSource())).repaint();
			}});
	    jRadioButton1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.getRoot(((JRadioButton) e.getSource())).repaint();
			}});
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton2.setBackground(null);
	    jRadioButton2.setOpaque(false);
	    jRadioButton2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.getRoot(((JRadioButton) e.getSource())).repaint();
			}});
	    typeGroup.add(jRadioButton1);
	    typeGroup.add(jRadioButton2);
	    
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton3.setBackground(null);
	    jRadioButton3.setOpaque(false);
	    jRadioButton3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					descenting = false;
				} else {
					descenting = true;
				}
				SwingUtilities.getRoot(((JRadioButton) e.getSource())).repaint();
			}});
	    jRadioButton3.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.getRoot(((JRadioButton) e.getSource())).repaint();
			}});
        jRadioButton4 = new javax.swing.JRadioButton("", true);
        jRadioButton4.setBackground(null);
	    jRadioButton4.setOpaque(false);
//	    jRadioButton4.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				if(((JRadioButton) e.getSource()).isSelected()) {
//					descenting = true;
//				} else {
//					descenting = false;
//				}
//				SwingUtilities.getRoot(((JRadioButton) e.getSource())).repaint();
//			}});
	    jRadioButton4.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.getRoot(((JRadioButton) e.getSource())).repaint();
			}});
	    orderGroup.add(jRadioButton3);
	    orderGroup.add(jRadioButton4);
	    
        jButton1 = new javax.swing.JButton();

        jComboBox1.setModel(new DefaultComboBoxModel());
        jComboBox1.setEnabled(false);
        jComboBox1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortBy = ((JComboBox) e.getSource()).getSelectedItem().toString();
				System.out.println(sortBy);
			}});
        
//        Attributes[] aa = Attributes.values();
//        String[] v = new String[aa.length];
//        for(int i = 0; i < aa.length; i++) {
//        	v[i] = (Enum.valueOf(Attributes.class, aa[i].toString()).getDesc());
//        }
//        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(v));
        
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(Attributes.values()));
        jComboBox2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortBy = ((JComboBox) e.getSource()).getSelectedItem()
						.toString();
				System.out.println(sortBy);
			}
		});
       
        jRadioButton1.setText("by Atrribute");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jRadioButton2.setText("by Profile");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jRadioButton3.setText("upscenting");
        jRadioButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jRadioButton4.setText("descenting");
        jRadioButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jButton1.setText("sort");
        jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!sorted) {
					sorted = true;
				}
				SwingUtilities.getRoot(((JButton) e.getSource())).repaint();
				ImageBase.getInstance().sort();
			}});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, 0, 99, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(36, 36, 36)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addGap(36, 36, 36)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jRadioButton2)
                        .addComponent(jComboBox1, 0, 97, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton4))
                    .addComponent(jButton1))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
		
	}

}

class TypeModel implements ComboBoxModel {

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
	
}
