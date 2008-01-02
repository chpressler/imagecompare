package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.Histogram;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;

import de.fherfurt.imagecompare.util.ICUtil;

public class HistogramComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private XYPlot graph;
	
	public HistogramComponent() {
		DefaultIntervalXYDataset d = new DefaultIntervalXYDataset();
		
		JFreeChart chart = ChartFactory.createHistogram("", "", "", new DS(), PlotOrientation.VERTICAL, true, true, true);
		chart.getPlot().setForegroundAlpha(0.5f);
		chart.setBorderVisible(true);
		ChartPanel panel = new ChartPanel(chart);
		panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		panel.setPreferredSize(new Dimension(200, 120));
		add(panel);
		
//		graph = new XYPlot();
//		graph.plot( ICUtil.getInstance().getHistogram(null) );
//		add(graph);
		
	}

}

class DS extends DefaultIntervalXYDataset { //implements IntervalXYDataset {

	private static final long serialVersionUID = 1L;
	
//	public DS() {
//		
//	}

	@Override
	public Number getEndX(int arg0, int arg1) {
		return 256;
	}

	@Override
	public double getEndXValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 50000;
	}

	@Override
	public Number getEndY(int arg0, int arg1) {
		return 100000;
	}

	@Override
	public double getEndYValue(int arg0, int arg1) {
		return 15000;
	}

	@Override
	public Number getStartX(int arg0, int arg1) {
		return 0;
	}

	@Override
	public double getStartXValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 12000;
	}

	@Override
	public Number getStartY(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getStartYValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 4;
	}

//	@Override
//	public DomainOrder getDomainOrder() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public int getItemCount(int arg0) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

//	@Override
//	public Number getX(int arg0, int arg1) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public double getXValue(int arg0, int arg1) {
//		// TODO Auto-generated method stub
//		return 15;
//	}

//	@Override
//	public Number getY(int arg0, int arg1) {
//		// TODO Auto-generated method stub
//		return 15;
//	}

//	@Override
//	public double getYValue(int arg0, int arg1) {
//		// TODO Auto-generated method stub
//		return 15;
//	}

//	@Override
//	public int getSeriesCount() {
//		// TODO Auto-generated method stub
//		return 40;
//	}

//	@Override
//	public Comparable getSeriesKey(int arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public int indexOf(Comparable arg0) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void addChangeListener(DatasetChangeListener arg0) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public DatasetGroup getGroup() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public void removeChangeListener(DatasetChangeListener arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setGroup(DatasetGroup arg0) {
//		// TODO Auto-generated method stub
//		
//	}
	
}
