import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseTrackerGui extends JFrame implements ActionListener {


    private JPanel newExpensePanel;
    private JTextPane amountTextPane, descriptionTextPane;
    private JCheckBox categoryCheckBox;


    public ExpenseTrackerGui() {
        super("Exprense Tracker Pro Max");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(CommonConstants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        addGuiCompenents();
    }

    public void addGuiCompenents() {

        // new expense panel
        newExpensePanel = new JPanel();
        newExpensePanel.setLayout(new BoxLayout(newExpensePanel, BoxLayout.Y_AXIS));
        newExpensePanel.setBounds((int)(CommonConstants.GUI_SIZE.width * 0.03),
                (CommonConstants.GUI_SIZE.height - CommonConstants.ADDEXPENSE_SIZE.height - 50), CommonConstants.ADDEXPENSE_SIZE.width, CommonConstants.ADDEXPENSE_SIZE.height);
        newExpensePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // amount field
        amountTextPane = new JTextPane();
        amountTextPane.setPreferredSize(new Dimension(100,25));
        amountTextPane.setBorder(BorderFactory.createLineBorder(Color.black));


        // description field
        descriptionTextPane = new JTextPane();
        descriptionTextPane.setPreferredSize(new Dimension(100,25));
        descriptionTextPane.setBorder(BorderFactory.createLineBorder(Color.black));

        // checkbox
        categoryCheckBox = new JCheckBox();
        categoryCheckBox.setPreferredSize(null);
        categoryCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        categoryCheckBox.addActionListener(this);


        newExpensePanel.add(amountTextPane);
        newExpensePanel.add(descriptionTextPane);
        newExpensePanel.add(categoryCheckBox);


        this.getContentPane().add(newExpensePanel, BorderLayout.CENTER);
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
