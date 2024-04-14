

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Chart extends JFrame {
    private XYSeriesCollection dataset;

    public Chart(double data[]){

        XYSeries series = new XYSeries("Data");
        for (int i = 0; i < data.length; i++) {
            series.add(i, data[i]);
        }
        this.dataset = new XYSeriesCollection(series);
    }

    public void createGraph(){
        JFreeChart chart = ChartFactory.createXYLineChart(" ", "k",
                "R[k]", dataset);
        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame frame = new JFrame("Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
