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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GraphComplexCurve extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    private final List<Double> domain;
    private ChartPanel chartPanel;
    private final List<ComplexNumber> params;

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", false));
    }

    ChartPanel getPanel() {
        return chartPanel;
    }

    GraphComplexCurve(String title, List<Double> domain, List<ComplexNumber> params) {
        super(title);
        this.domain = domain;
        this.params = params;
        chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(1500, 800));
        setContentPane(chartPanel);
    }

    GraphComplexCurve subCurve(int start, int end) {
        return new GraphComplexCurve(getTitle(), domain.subList(start, end), params.subList(start, end));
    }

    void graph() {
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    static GraphTimeCurve freqToTimeGraph(GraphComplexCurve curve, GraphTimeCurve origGraph, String myTitle) {
        return new GraphTimeCurve(myTitle, toParamList(FourierTransform.toTime(curve.getParams()), origGraph.getParams()));
    } // values aren't stored from before but now they are but i still hate this code

    static GraphLineCurve freqToLineGraph(GraphComplexCurve curve, GraphLineCurve origGraph, String myTitle) {
        return new GraphLineCurve(myTitle, toParamList(FourierTransform.toTime(curve.getParams()), origGraph.getParams()));
    } // values aren't stored from before but now they are but i still hate this code

    private static List<GraphParam> toParamList(List<ComplexNumber> list, List<GraphParam> origList) {
        List<GraphParam> nums = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            nums.add(new GraphParam(origList.get(i).getBJD(), list.get(i).getReal()));
        }
        return nums;
    }

    private static JFreeChart createLineChart(XYDataset dataSet) {
        JFreeChart chart = ChartFactory.createXYLineChart("Frequency Domain vs Amplitude",
                "Frequency", "Amplitude", dataSet, PlotOrientation.VERTICAL, true,
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

    GraphComplexCurve lowPassFilter(String myTitle, double freq) {
        List<ComplexNumber> filteredParams = new ArrayList<>(params);
        for (int i = 0; i < params.size(); i++) {
            if (domain.get(i) > freq) {
                filteredParams.set(i, new ComplexNumber(0, 0));
            }
        }

        return new GraphComplexCurve(myTitle, domain, filteredParams);
    }

    private XYDataset createDataSet() {

        XYSeries s1 = new XYSeries(o -> 0);

        for (int i = 0; i < params.size(); i++) {
            s1.add(domain.get(i), (Number) params.get(i).getMagnitude());
        }
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(s1);

        return dataSet;
    }

    private List<ComplexNumber> getParams() {
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
