import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
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
    private JPanel newExpensePanel, expensesList;
    private CustomTextPane amountTextPane, descriptionTextPane;
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

        setGlobalUIFont(new Font("Century Gothic", Font.PLAIN, 14));

        // new expense panel
        newExpensePanel = new JPanel();
        newExpensePanel.setLayout(null);
        newExpensePanel.setBounds((int) (CommonConstants.GUI_SIZE.width * 0.1),
                (CommonConstants.GUI_SIZE.height - CommonConstants.ADDEXPENSE_SIZE.height - 50), CommonConstants.ADDEXPENSE_SIZE.width, CommonConstants.ADDEXPENSE_SIZE.height);
        newExpensePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // amount field
        amountTextPane = new CustomTextPane("Amount");
//        amountTextPane = new JTextPane();
        amountTextPane.setPreferredSize(new Dimension(100, 25));
        amountTextPane.setBorder(BorderFactory.createLineBorder(Color.black));
        amountTextPane.setBounds(10, 10, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);

        // description field
        descriptionTextPane = new CustomTextPane("Description");
//        descriptionTextPane = new JTextPane();
        descriptionTextPane.setPreferredSize(new Dimension(100, 25));
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
        addExpenseButton.setBounds(0, CommonConstants.ADDEXPENSE_SIZE.height - 30, (int) (CommonConstants.ADDEXPENSE_SIZE.width), 30);
        addExpenseButton.addActionListener(this);
        addExpenseButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // previousMonthButton and nextMonthButton
        previousMonthButton = new JButton("Previous Month");
        previousMonthButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        previousMonthButton.setBounds(20, 20, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);
        previousMonthButton.addActionListener(this);
        nextMonthButton = new JButton("Next Month");
        nextMonthButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextMonthButton.setBounds(220, 20, CommonConstants.TEXTPANE_SIZE.width, CommonConstants.TEXTPANE_SIZE.height);
        nextMonthButton.addActionListener(this);


        // add total expense counter label
        totalExpenseTextPane = new JLabel("Total expenses: " + DatabaseTransactions.getSumByMonth(LocalDate.now()));
        totalExpenseTextPane.setBounds(180, 500, CommonConstants.TOTAL_EXPENSES_LABEL_SIZE.width, CommonConstants.TOTAL_EXPENSES_LABEL_SIZE.height);


        // add expenses list panel
        expensesList = new JPanel();
        expensesList.setLayout(new BoxLayout(expensesList, BoxLayout.Y_AXIS));
//        expensesList.setPreferredSize(new Dimension(CommonConstants.EXPENSES_LIST_SIZE.width, 500));
        expensesList.setPreferredSize(null);
        expensesList.setAlignmentX(Component.LEFT_ALIGNMENT);


        expensesList.revalidate();
        expensesList.repaint();

        // make it scrollable
        JScrollPane scrollPane = new JScrollPane(expensesList);
        scrollPane.setBounds((int) (CommonConstants.GUI_SIZE.width * 0.55), 20,
                CommonConstants.EXPENSES_LIST_SIZE.width, CommonConstants.EXPENSES_LIST_SIZE.height);

        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
//        scrollPane.setMaximumSize(CommonConstants.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // speed up the scroll bar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);


        newExpensePanel.add(categoryDropDown);
        newExpensePanel.add(amountTextPane);
        newExpensePanel.add(descriptionTextPane);
        newExpensePanel.add(addExpenseButton);
        getContentPane().add(scrollPane);

        expensePieChartPanel = new ExpensePieChartPanel();

        expensePieChartPanel.setBounds(20, 60, CommonConstants.CHART_SIZE.width, CommonConstants.CHART_SIZE.height);
        add(expensePieChartPanel);
        add(totalExpenseTextPane);
        add(previousMonthButton);
        add(nextMonthButton);
        add(scrollPane);

        this.getList(LocalDate.now());


        expensesList.revalidate();
        expensesList.repaint();


        this.getContentPane().add(newExpensePanel, BorderLayout.CENTER);


    }


    public static void setGlobalUIFont(Font font) {
        UIManager.getLookAndFeelDefaults().entrySet().forEach(entry -> {
            if (entry.getKey().toString().toLowerCase().contains("font")) {
                UIManager.put(entry.getKey(), font);
            }
        });
    }


    public void updateTotalLabel(LocalDate neededDate) throws SQLException {
        totalExpenseTextPane.setText("Total expenses: " + DatabaseTransactions.getSumByMonth(neededDate));
    }


    public void getList(LocalDate date) throws SQLException {
        ExpenseComponent[] expc = DatabaseTransactions.getExListByMonth(date);
        System.out.println(expc.length);
        expensesList.removeAll();
        for (ExpenseComponent exp : expc) {
            JPanel rowPanel = new JPanel();
            JButton deleteButtob = new JButton("X");
            rowPanel.add(deleteButtob);
            rowPanel.add(new JLabel(exp.toString()));
            rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));

            // lambda for deletion
            deleteButtob.addActionListener(e -> {
                try {
                    DatabaseTransactions.deleteExpense(exp.getId());
                    expensesList.remove(rowPanel); // Remove this row from the UI
                    expensesList.revalidate();
                    expensesList.repaint();
                    updateTotalLabel(date);
                    expensePieChartPanel.refreshChart(currentMonth);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }) ;

            expensesList.add(rowPanel);
            // adding distance between records

        }
        expensesList.revalidate();
        expensesList.repaint();
    }

    public void updateInterface() {
        amountTextPane.setText("");
        amountTextPane.resetToPlaceholder();
        descriptionTextPane.setText("");
        descriptionTextPane.resetToPlaceholder();
        categoryDropDown.setSelectedIndex(0);
        expensePieChartPanel.refreshChart(LocalDate.now());
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
            updateInterface();
            try {
                updateTotalLabel(LocalDate.now());
                getList(date);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        // previous/next month buttons change the diagram, displayed expenses and total expenditure label
        if (e.getSource() == previousMonthButton) {
            currentMonth = currentMonth.minusMonths(1);
            System.out.println("Previous month: " + currentMonth);

            try {
                updateTotalLabel(currentMonth);
                getList(currentMonth);
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
                getList(currentMonth);
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
