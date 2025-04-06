import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;

public class ExpenseTrackerGui extends JFrame implements ActionListener, ItemListener {

    private ExpensePieChartPanel expensePieChartPanel;
    private JPanel newExpensePanel;
    private JTextPane amountTextPane, descriptionTextPane;
    private JComboBox categoryDropDown;
    private JButton addExpenseButton, previousMonthButton, nextMonthButton;
    private JLabel totalExpenseTextPane;

    public static LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
    public static int year = LocalDate.now().getYear();


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
        newExpensePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

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
        categoryDropDown.setBounds(170, 10, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);

        // add task button
        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addExpenseButton.setBounds(0,CommonConstants.ADDEXPENSE_SIZE.height-30, (int)(CommonConstants.ADDEXPENSE_SIZE.width), 30);
        addExpenseButton.addActionListener(this);
        addExpenseButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // previousMonthButton and nextMonthButton
        previousMonthButton = new JButton("Previous Month");
        previousMonthButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        previousMonthButton.setBounds(20,20, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);
        previousMonthButton.addActionListener(this);
        nextMonthButton = new JButton("Next Month");
        nextMonthButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextMonthButton.setBounds(220, 20, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);
        nextMonthButton.addActionListener(this);


        // add total expense counter label
        totalExpenseTextPane = new JLabel("Total Expense: " + DatabaseTransactions.getSumByMonth(LocalDate.now()));
        totalExpenseTextPane.setBounds(180, 500, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);



        newExpensePanel.add(categoryDropDown);
        newExpensePanel.add(amountTextPane);
        newExpensePanel.add(descriptionTextPane);
        newExpensePanel.add(addExpenseButton);


        expensePieChartPanel= new ExpensePieChartPanel();

        expensePieChartPanel.setBounds(20,60, CommonConstants.CHART_SIZE.width, CommonConstants.CHART_SIZE.height);
        add(expensePieChartPanel);
        add(totalExpenseTextPane);
        add(previousMonthButton);
        add(nextMonthButton);
        this.getContentPane().add(newExpensePanel, BorderLayout.CENTER);
    }


    public void updateTotalLabel(LocalDate neededDate) throws SQLException {
        totalExpenseTextPane.setText("Total Expense: " + DatabaseTransactions.getSumByMonth(neededDate));
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addExpenseButton && !amountTextPane.getText().isEmpty() && !descriptionTextPane.getText().isEmpty()) {
        double amount = Double.parseDouble(amountTextPane.getText());
        String description = descriptionTextPane.getText();
        int category = DatabaseTransactions.getCategoryId(categoryDropDown.getSelectedItem().toString());
        LocalDate date = LocalDate.now();

        if (!amountTextPane.getText().isEmpty() && !description.isEmpty()) {
            DatabaseTransactions.insertExpense(amount, description, category, date.toString());
        }

        amountTextPane.setText("");
        descriptionTextPane.setText("");
        categoryDropDown.setSelectedIndex(0);
        expensePieChartPanel.refreshChart(LocalDate.now());
        try {
            updateTotalLabel(LocalDate.now());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

        if (e.getSource() == previousMonthButton) {
            currentMonth = currentMonth.minusMonths(1);
            System.out.println("Previous month: " + currentMonth);

            try {
                updateTotalLabel(currentMonth);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            expensePieChartPanel.refreshChart(currentMonth);
        } else if (e.getSource() == nextMonthButton) {
            // prevent going beyond current month
            LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);
            if (!currentMonth.isBefore(thisMonth)) return;

            currentMonth = currentMonth.plusMonths(1);
            System.out.println("Next month: " + currentMonth);

            try {
                updateTotalLabel(currentMonth);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            expensePieChartPanel.refreshChart(currentMonth);
        }




    }


    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
