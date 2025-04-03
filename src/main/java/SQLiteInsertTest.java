import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteInsertTest {

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/data/expenses.db";

    public static void main(String[] args) {
        insertExpense(27.99, "Shoes", 1, "2025-04-03");
    }

    private static void insertExpense(double amount, String description, int categoryId, String date) {
        String insertSQL = "INSERT INTO expenses (amount, description, category_id, date) VALUES (?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // 🔐 Enable foreign key constraint enforcement
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Set values for the INSERT
            pstmt.setDouble(1, amount);
            pstmt.setString(2, description);
            pstmt.setInt(3, categoryId); // Must exist in categories table
            pstmt.setString(4, date);

            pstmt.executeUpdate();
            System.out.println("✅ Expense inserted successfully.");
        } catch (SQLException e) {
            System.err.println("❌ Error inserting expense: " + e.getMessage());
        }
    }
}
