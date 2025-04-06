import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpensePieChartPanel extends JPanel {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/data/expenses.db";


    public ExpensePieChartPanel() {
        drawChart(LocalDate.now());
    }


    public void refreshChart(LocalDate selectedDate) {
        removeAll();
        drawChart(selectedDate);
        revalidate();
        repaint();
    }

    private void drawChart(LocalDate selectedDate) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.name, SUM(e.amount) AS total " +
                             "FROM expenses e " +
                             "JOIN categories c ON e.category_id = c.id " +
                             "WHERE strftime('%Y-%m', e.date) = ? " +
                             "GROUP BY c.name")) {

            String yearMonth = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            stmt.setString(1, yearMonth);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dataset.setValue(rs.getString("name"), rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String title = "Expenses by Category (" + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM")) + ")";

        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataset,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(CommonConstants.CHART_SIZE);

        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
        chart.setBackgroundPaint(CommonConstants.BACKGROUND_COLOR);
        chart.getPlot().setBackgroundPaint(CommonConstants.BACKGROUND_COLOR);
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);

        plot.setShadowPaint(null);



    }


}
