import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI.BasicHorizontalLayoutManager;

public class UIPanel extends JFrame {
    // ??? it doesn't do anything
    private static JButton nextButton = new JButton("next - doesn't do anything");
    private JPanel panel;

    void displayDefaultPanel() {
        ScrollPane pane = new ScrollPane();

    }

    public UIPanel(String name) {
        super(name);
        setSize(1500, 1700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        add(panel);
        setVisible(true);

    }

    public void addButton(String name, ActionListener aL) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(aL);
    }

    public void toFreqPanel() {
        //TODO : .invalidate(old panel), .validate(new panel w/copied data)
    }

    public void toTimePanel() {
        //TODO: copy over data from first panel somehow?? (maybe add more attributes)


        new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                //Here goes the action (method) you want to execute when clicked
                System.out.println("You clicked the button ");
            }
        };
    }

    public static void main(String[] args) {
        UIPanel timeDomain1 = new UIPanel("timeDomain");
        timeDomain1.addButton("next button that does nothing yet", null);
    }
}
