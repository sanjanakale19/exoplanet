import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

class GraphLineCurve extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    private ChartPanel panel;
    private final List<GraphParam> params;

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", false));
    }

    ChartPanel getPanel() {
        return panel;
    }

    GraphLineCurve(String title, List<GraphParam> params) {
        super(title);
        this.params = params;
        panel = (ChartPanel) createDemoPanel();
        panel.setPreferredSize(new java.awt.Dimension(1500, 800));
        setContentPane(panel);
    }

    void graph() {
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    static GraphComplexCurve timeToFreqGraph(GraphLineCurve curve, List<Double> domain, String myTitle) {
        return new GraphComplexCurve(myTitle, domain, FourierTransform.toFrequency(toComplexList(curve.getParams())));
    }

    static List<ComplexNumber> toComplexList(List<GraphParam> list) {
        List<ComplexNumber> nums = new ArrayList<>();
        for (GraphParam param : list) {
            nums.add(new ComplexNumber(param.getFlux(), 0));
        }
        return nums;
    }

    private static JFreeChart createLineChart(XYDataset dataSet) {
        JFreeChart chart = ChartFactory.createXYLineChart("Corrected Flux vs. BJD Time",
                "BJD - 2454833", "Corrected Flux", dataSet, PlotOrientation.VERTICAL, true,
                true, false);

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.cyan);
        plot.setRangeGridlinePaint(Color.cyan);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof StandardXYItemRenderer) {
            StandardXYItemRenderer renderer = (StandardXYItemRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        NumberAxis axis = (NumberAxis) plot.getDomainAxis();

        return chart;

    }

    private XYDataset createDataSet() {

        XYSeries s1 = new XYSeries(o -> 0);

        for (GraphParam param : params) {
            s1.add(param.getBJD(), param.getFlux());
        }
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(s1);

        return dataSet;
    }

    List<GraphParam> getParams() {
        return params;
    }

    private JPanel createDemoPanel() {
        JFreeChart chart = createLineChart(createDataSet());

        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }


}


