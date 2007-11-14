package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.IntervalXYDataset;

public class HistogramComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public HistogramComponent() {
		JFreeChart chart = ChartFactory.createHistogram("Pfad/zum/Bild", "FarbWert", "Häufigkeit", new DS(), PlotOrientation.VERTICAL, true, true, true);
		chart.getPlot().setForegroundAlpha(0.5f);
		ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(200, 170));
		add(panel);
	}

}

class DS implements IntervalXYDataset {

	@Override
	public Number getEndX(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEndXValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number getEndY(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEndYValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number getStartX(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getStartXValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number getStartY(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getStartYValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DomainOrder getDomainOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemCount(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number getX(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getXValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number getY(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getYValue(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSeriesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Comparable getSeriesKey(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Comparable arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addChangeListener(DatasetChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DatasetGroup getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeChangeListener(DatasetChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGroup(DatasetGroup arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
