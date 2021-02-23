import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

class GraphTimeCurve extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    private ChartPanel chartPanel;
    private final List<GraphParam> params;

    ChartPanel getPanel() {
        return chartPanel;
    }

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", false));
    }

    GraphTimeCurve(String title, List<GraphParam> params) {
        super(title);
        this.params = params;
        chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(1500, 800));
        setContentPane(chartPanel);
    }

    void graph() {
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    static GraphComplexCurve timeToFreqGraph(GraphTimeCurve curve, List<Double> domain, String myTitle) {
        return new GraphComplexCurve(myTitle, domain, FourierTransform.toFrequency(toComplexList(curve.getParams())));
    }

    private static List<ComplexNumber> toComplexList(List<GraphParam> list) {
        List<ComplexNumber> nums = new ArrayList<>();
        for (GraphParam param : list) {
            nums.add(new ComplexNumber(param.getFlux(), 0));
        }
        return nums;
    }

    private static JFreeChart createTimeChart(XYDataset dataSet) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Corrected Flux vs. Time",
                "Date", "Corrected Flux", dataSet, true, true, false);

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

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("M/d/yy, k:m:s"));

        return chart;

    }

    List<GraphParam> getParams() {
        return params;
    }

    private XYDataset createTimeDataSet() {

        TimeSeries s1 = new TimeSeries("");

        for (GraphParam param : params) {
            s1.add(RegularTimePeriod.createInstance(Second.class, getDate(param), TimeZone.getTimeZone("UTC"),
                    Locale.getDefault()), param.getFlux());
        }

        TimeSeriesCollection dataSet = new TimeSeriesCollection();
        dataSet.addSeries(s1);

        return dataSet;
    }

    private JPanel createDemoPanel() {
        JFreeChart chart = createTimeChart(createTimeDataSet());

        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    private static Date getDate(GraphParam param) {
        double bjd = param.getBJD();
        double dec = bjd - (long)bjd;
        int sec = (int) (dec * 86400);

        LocalDate date =  LocalDate.MIN.with(java.time.temporal.JulianFields.JULIAN_DAY, (long)bjd);
        GregorianCalendar cal = new GregorianCalendar(date.getYear(), date.getMonthValue() - 1,
                                date.getDayOfMonth(), 12, 0, 0);
        cal.add(Calendar.SECOND, sec);
        return cal.getTime();

    }
}



























































