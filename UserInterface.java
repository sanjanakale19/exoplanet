import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    private static final ArrayList<DataObj> campaigns = new ArrayList<>();
    private int campaignNum;
    private ArrayList<DataObj> data;
    private int dataNum;
    private boolean isTimeGraph;
    private boolean isGregorian;
    private JPanel chartPanel;
    private JPanel buttonPanel;
    private JLabel contentLabel;
    private JScrollPane campaignDataSP;
    private final Container pane;
    private boolean ranAlg;
    private double accuracy;    //reads from a file that is updated when algorithm tests; value from 0 to 1

//    void setCampaignNum(int myCampaignNum, final boolean startAtBeginning) {
//        if (myCampaignNum < 0 || myCampaignNum >= 22) {
//            myCampaignNum = 0;
//        }
//        campaignNum = myCampaignNum;
//
//        SwingWorker sw1 = new SwingWorker() {
//
//            @Override
//            protected String doInBackground() throws Exception {
//                // define what thread will do here
//                data = campaigns.get(campaignNum).readURL();
//                dataNum = startAtBeginning ? 0 : data.size() - 1;
//                return "Finished";
//            }
//
//            @Override
//            protected void done() {
//                // this method is called when the background thread finishes execution
//                setScrollPane();
//                graph();
//                namePanel();
//            }
//        };
//        // executes the swingworker on worker thread
//        sw1.execute();
//    }

    void setCampaignNum(int myCampaignNum, boolean startAtBeginning) {
        if (myCampaignNum < 0 || myCampaignNum >= 22) {
            myCampaignNum = 0;
        }
        campaignNum = myCampaignNum;
        data = campaigns.get(campaignNum).readURL();
        dataNum = startAtBeginning ? 0 : data.size() - 1;
        setScrollPane();
        graph();
        namePanel();
    }

    void setDataNum(int num) {
        if (num < data.size()) {
            dataNum = num;
            graph();
            namePanel();
        } else if (num >= data.size()) {
            setCampaignNum(campaignNum + 1, true);
        } else if (num < 0 && campaignNum > 0) {
            setCampaignNum(campaignNum - 1, false);
        }
    }

    public UserInterface(Container pane) {
        this.pane = pane;
        pane.setLayout(new GridBagLayout());
        initializeCampaigns();
        isTimeGraph = true;
        isGregorian = true;
        initializeTime();
        dataNum = 0;
        setCampaignNum(0, true);
        ranAlg = false;
    }

    private void setScrollPane() {
        if (campaignDataSP != null) {
            pane.remove(campaignDataSP);
        }
        GridBagConstraints constraints = new GridBagConstraints();

        String[] list = new String[data.size()];
        int count = 1;
        for (DataObj i : data) {
            list[count - 1] = ("Set " + count + ": " + i.getName());
            count++;
        }
        JList<String> l = new JList<>(list);
        campaignDataSP = new JScrollPane(l);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 0;  //make this component tall
        constraints.ipadx = 0;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        pane.add(campaignDataSP, constraints);

        l.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                setDataNum(l.getSelectedIndex());
            }
        });
    }


    private void graph() {
        if (chartPanel != null) {
            pane.remove(chartPanel);
        }

        GridBagConstraints constraints = new GridBagConstraints();
        try {
            DataObj obj = data.get(dataNum);
            ArrayList<GraphParam> params = GraphParam.getTable(obj);

            if (isTimeGraph) {
                if (isGregorian) {
                    GraphTimeCurve curve = new GraphTimeCurve("Graph", params);
                    chartPanel = curve.getPanel();
                } else {
                    GraphLineCurve curve = new GraphLineCurve("Graph", params);
                    chartPanel = curve.getPanel();
                }
            } else {
                List<Double> domain = new ArrayList<>();
                for (double x = 0; x < 3197; x += 1) {
                    domain.add(x/(3197 * 30));
                }
                GraphComplexCurve curve =
                        new GraphComplexCurve("Graph", domain, GraphLineCurve.toComplexList(params));
                chartPanel = curve.getPanel();
            }

            constraints.fill = GridBagConstraints.BOTH;
            constraints.ipady = 100;
            constraints.weightx = 0.1;
            constraints.weighty = 1.0;
            constraints.gridwidth = 1;
            constraints.gridheight = 3;
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.insets = new Insets(10, 0, 10, 10);
            pane.add(chartPanel, constraints);
            chartPanel.updateUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeTime() {
        if (buttonPanel != null) {
            pane.remove(buttonPanel);
        }

        buttonPanel = new JPanel();

        GridBagConstraints constraints = new GridBagConstraints();

        JButton button;
        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("To Frequency Domain");
        constraints.weightx = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isTimeGraph = false;
                initializeFreq();
                graph();
            }
        });

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Run Algorithm");
        constraints.weightx = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Switch Time Domain");
        constraints.weightx = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isGregorian = !isGregorian;
                graph();
            }
        });

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Run all processes");
        constraints.weightx = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("<— Previous Graph");
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setDataNum(dataNum - 1);
            }
        });

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Next Graph —>");
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.ipadx = 50;
        constraints.ipady = 15;
        constraints.gridx = 6;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setDataNum(dataNum + 1);
            }
        });

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        pane.add(buttonPanel, constraints);

    }
// for now has saved buttons and constraints of initializeTime in case u fuk up // nvm u didn't fuk up // nvm u did
    private void initializeFreq() {
        if (buttonPanel != null) {
            pane.remove(buttonPanel);
        }

        buttonPanel = new JPanel();

        GridBagConstraints constraints = new GridBagConstraints();

        JButton button;
        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("To Time Domain");
        constraints.weightx = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isTimeGraph = true;
                isGregorian = true;
                initializeTime();
                graph();
            }
        });

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("<— Previous Graph");
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setDataNum(dataNum - 1);
            }
        });

        constraints.fill = GridBagConstraints.BOTH;
        button = new JButton("Next Graph —>");
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.ipadx = 50;
        constraints.ipady = 15;
        constraints.gridx = 6;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        buttonPanel.add(button, constraints);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setDataNum(dataNum + 1);
            }
        });

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0;
        constraints.ipadx = 40;
        constraints.ipady = 15;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        pane.add(buttonPanel, constraints);
    }

    private void resultPanel() {
        if (!ranAlg) {

        }
    }

    private void namePanel() {
        String text = "<html><div style='text-align:center'>Campaign " + campaigns.get(campaignNum).getName().substring(1) + "<br><br>Dataset " + (dataNum + 1) + "</div></html>";
        if (contentLabel == null) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;

            contentLabel =
                    new JLabel(text);
            contentLabel.setVerticalAlignment(JLabel.TOP);
            contentLabel.setHorizontalAlignment(JLabel.CENTER);
            contentLabel.setBackground(Color.CYAN);
            constraints.weightx = 0;
            constraints.weighty = 0;
            constraints.ipadx = 80;
            constraints.ipady = 0;
            constraints.gridx = 7;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 2;
            constraints.anchor = GridBagConstraints.PAGE_START;
            constraints.insets = new Insets(20, 0, 10, 0);
            pane.add(contentLabel, constraints);
        } else {
            contentLabel.setText(text);
        }
    }

    public static void runProgram() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("UIPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        Container pane = frame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        UserInterface myUI = new UserInterface(pane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        runProgram();
    }

    private void initializeCampaigns() {
        // hard-code each link to campaigns
        campaigns.add(new DataObj("c0", "https://www.cfa.harvard.edu/~avanderb/C0asciiwget.sh"));
        campaigns.add(new DataObj("c1", "https://www.cfa.harvard.edu/~avanderb/C1asciiwget.sh"));
        campaigns.add(new DataObj("c2", "https://www.cfa.harvard.edu/~avanderb/C2asciiwget.sh"));
        campaigns.add(new DataObj("c3 - test data", "https://www.cfa.harvard.edu/~avanderb/C3asciiwget.sh"));
        campaigns.add(new DataObj("c4", "https://www.cfa.harvard.edu/~avanderb/C4asciiwget.sh"));
        campaigns.add(new DataObj("c5", "https://www.cfa.harvard.edu/~avanderb/C5asciiwget.sh"));
        campaigns.add(new DataObj("c6", "https://www.cfa.harvard.edu/~avanderb/C6asciiwget.sh"));
        campaigns.add(new DataObj("c7", "https://www.cfa.harvard.edu/~avanderb/C7asciiwget.sh"));
        campaigns.add(new DataObj("c8", "https://www.cfa.harvard.edu/~avanderb/C8asciiwget.sh"));
        campaigns.add(new DataObj("c9a", "https://www.cfa.harvard.edu/~avanderb/C91asciiwget.sh"));
        campaigns.add(new DataObj("c9b", "https://www.cfa.harvard.edu/~avanderb/C92asciiwget.sh"));
        campaigns.add(new DataObj("c10", "https://www.cfa.harvard.edu/~avanderb/C10asciiwget.sh"));
        campaigns.add(new DataObj("c11a", "https://www.cfa.harvard.edu/~avanderb/C111asciiwget.sh"));
        campaigns.add(new DataObj("c11b", "https://www.cfa.harvard.edu/~avanderb/C112asciiwget.sh"));
        campaigns.add(new DataObj("c12", "https://www.cfa.harvard.edu/~avanderb/C12asciiwget.sh"));
        campaigns.add(new DataObj("c13", "https://www.cfa.harvard.edu/~avanderb/C13asciiwget.sh"));
        campaigns.add(new DataObj("c14", "https://www.cfa.harvard.edu/~avanderb/C14asciiwget.sh"));
        campaigns.add(new DataObj("c15", "https://www.cfa.harvard.edu/~avanderb/C15asciiwget.sh"));
        campaigns.add(new DataObj("c16", "https://www.cfa.harvard.edu/~avanderb/C16asciiwget.sh"));
        campaigns.add(new DataObj("c17", "https://www.cfa.harvard.edu/~avanderb/C17asciiwget.sh"));
        campaigns.add(new DataObj("c18", "https://www.cfa.harvard.edu/~avanderb/C18asciiwget.sh"));
        campaigns.add(new DataObj("c19", "https://www.cfa.harvard.edu/~avanderb/C19asciiwget.sh"));


        // add text block about the campaign scroll list
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel label = new JLabel("Campaigns");
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 80;
        constraints.ipady = 20;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets.left = 10;
        constraints.insets.top = 10;
        pane.add(label, constraints);


        // add scroll pane with the data from campaigns
        String[] list = new String[campaigns.size()];
        int count = 0;
        for (DataObj i : campaigns) {
            list[count] = (i.getName());
            count++;
        }

        JList<String> l = new JList<>(list);
        JScrollPane sp = new JScrollPane(l);
        sp.setMinimumSize(new Dimension(100, 200));
        //sp.add(l, GridBagConstraints.NORTH, 0);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 0;  //make this component tall
        constraints.ipadx = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(10, 10, 10, 10);
        pane.add(sp, constraints);

        l.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                setCampaignNum(l.getSelectedIndex(), true);
            }
        });


        // add text block about datasets within the selected campaign
        constraints.fill = GridBagConstraints.HORIZONTAL;
        label = new JLabel("Datasets in Campaign");
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 80;
        constraints.ipady = 0;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets.left = 10;
        constraints.insets.top = 10;
        pane.add(label, constraints);
    }

    private void campaignButtons() {

    }
}