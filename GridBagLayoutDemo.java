import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


public class GridBagLayoutDemo {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    public static void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
        JPanel jPanel;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
//        if (shouldFill) {
//            //natural height, maximum width
//            constraints.fill = GridBagConstraints.HORIZONTAL;
//        }
//
//        button = new JButton("Button 1");
//        if (shouldWeightX) {
//            constraints.weightx = 0.5;
//        }
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        pane.add(button, constraints);
//
//        button = new JButton("Button 2");
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.weightx = 0.5;
//        constraints.gridx = 1;
//        constraints.gridy = 1;
//        pane.add(button, constraints);
//
//        button = new JButton("Button 3");
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.weightx = 0.5;
//        constraints.gridx = 2;
//        constraints.gridy = 2;
//        pane.add(button, constraints);
//
//        button = new JButton("Button 4");
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.weightx = 0.5;
//        constraints.gridx = 3;
//        constraints.gridy = 3;
//        pane.add(button, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Button 2");
        constraints.weightx = 0;
        constraints.ipadx = 50;
        constraints.ipady = 15;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        pane.add(button, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Button 3");
        constraints.weightx = 0;
        constraints.ipadx = 50;
        constraints.ipady = 15;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        pane.add(button, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Button 4");
        constraints.weightx = 0;
        constraints.ipadx = 50;
        constraints.ipady = 15;
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        pane.add(button, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Button 5");
        constraints.weightx = 0;
        constraints.ipadx = 50;
        constraints.ipady = 15;
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        pane.add(button, constraints);

        try {
            GraphTimeCurve curve = new GraphTimeCurve("myGraph1", GraphParam.getTableFromLink("http://archive.stsci.edu/missions/hlsp/k2sff/c05/211300000/11380/hlsp_k2sff_k2_lightcurve_211311380-c05_kepler_v1_llc-default-aper.txt"));
            jPanel = curve.getPanel();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.ipady = 100;  //make this component tall
            //constraints.ipadx = 1700;
            constraints.weightx = 0.1;
            constraints.weighty = 1.0;
            constraints.gridwidth = 3;
            constraints.gridheight = 2;
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.insets = new Insets(10, 0, 10, 10);
            pane.add(jPanel, constraints);

        } catch (Exception e) {
            e.printStackTrace();
        }

        constraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel label = new JLabel("CAMPAIGNS");
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 100;
        constraints.ipady = 20;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets.left = 10;
        pane.add(label, constraints);

        ScrollPane sp = new ScrollPane();
        List l = new List();
        String[] list = new String[]{"a", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g", "b", "c", "d", "e", "f", "g"};
        for (String i : list) {
            l.add(i);
        }

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        //constraints.ipady = 700;  //make this component tall
        constraints.ipadx = 0;
        constraints.weightx = 0;
        constraints.weighty = 0.9;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 10, 10, 10);
        sp.add(l, GridBagConstraints.NORTH, 0);
        pane.add(sp, constraints);


    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
