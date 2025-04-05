import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.time.LocalDate;

public class ExpenseTrackerGui extends JFrame implements ActionListener, ItemListener {


    private JPanel newExpensePanel;
    private JTextPane amountTextPane, descriptionTextPane;
    private JComboBox categoryDropDown;
    private JButton addExpenseButton;


    public ExpenseTrackerGui() throws SQLException {
        super("Exprense Tracker Pro Max");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(CommonConstants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        addGuiCompenents();
    }

    public void addGuiCompenents() throws SQLException {


        // new expense panel
        newExpensePanel = new JPanel();
        newExpensePanel.setLayout(null);
        newExpensePanel.setBounds((int)(CommonConstants.GUI_SIZE.width * 0.03),
                (CommonConstants.GUI_SIZE.height - CommonConstants.ADDEXPENSE_SIZE.height - 50), CommonConstants.ADDEXPENSE_SIZE.width, CommonConstants.ADDEXPENSE_SIZE.height);
        newExpensePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // amount field
        amountTextPane = new CustomTextPane("Amount");
//        amountTextPane = new JTextPane();
        amountTextPane.setPreferredSize(new Dimension(100,25));
        amountTextPane.setBorder(BorderFactory.createLineBorder(Color.black));
        amountTextPane.setBounds(10, 10, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);

        // description field
        descriptionTextPane = new CustomTextPane("Description");
//        descriptionTextPane = new JTextPane();
        descriptionTextPane.setPreferredSize(new Dimension(100,25));
        descriptionTextPane.setBorder(BorderFactory.createLineBorder(Color.black));
        descriptionTextPane.setBounds(10, 45, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);

        // take categories from the database
        String[] categories = DatabaseTransactions.getCategories();

        // category dropdown list
        categoryDropDown = new JComboBox<>(categories);
        categoryDropDown.setPreferredSize(null);
        categoryDropDown.addItemListener(this);
        categoryDropDown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        categoryDropDown.setBounds(120, 10, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);

        // add task button
        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addExpenseButton.setBounds(0,CommonConstants.ADDEXPENSE_SIZE.height-30, (int)(CommonConstants.ADDEXPENSE_SIZE.width), 30);
        addExpenseButton.addActionListener(this);

        newExpensePanel.add(categoryDropDown);
        newExpensePanel.add(amountTextPane);
        newExpensePanel.add(descriptionTextPane);
        newExpensePanel.add(addExpenseButton);


        this.getContentPane().add(newExpensePanel, BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        double amount = Double.parseDouble(amountTextPane.getText());
        String description = descriptionTextPane.getText();
        int category = DatabaseTransactions.getCategoryId(categoryDropDown.getSelectedItem().toString());
        LocalDate date = LocalDate.now();

        if (!amountTextPane.getText().equals("") && !description.equals("")) {
            DatabaseTransactions.insertExpense(amount, description, category, date.toString());
        }

        amountTextPane.setText("");
        descriptionTextPane.setText("");
        categoryDropDown.setSelectedIndex(0);

    }


    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
