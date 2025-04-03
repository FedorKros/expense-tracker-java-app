package blueprints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseComponent extends JPanel implements ActionListener {
    private JTextPane amountTextPane, descriptionTextPane;
    private JCheckBox categoryCheckBox;
    private JPanel panel;

    public ExpenseComponent(JPanel panel) {
        this.panel = panel;

        amountTextPane = new JTextPane();
        amountTextPane.setBorder(BorderFactory.createLineBorder(Color.black));

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
