import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseTransactions {

    public static final String DB_URL = "jdbc:sqlite:src/main/resources/data/expenses.db";

    public static String[] getCategories() throws SQLException {
        String getSQL = "SELECT name FROM categories";
        List<String> categoryList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys=ON");

            try (ResultSet rs = stmt.executeQuery(getSQL)) {
                while (rs.next()) {
                    categoryList.add(rs.getString("name"));
                }
            }

        }
        String[] categories = new String[categoryList.size()];
        categoryList.toArray(categories);
        return categories;
    }

    public static void insertExpense(double amount, String description, int categoryId, String date) {
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


    public static int getCategoryId(String categoryName) {
        String sql = "SELECT id FROM categories WHERE name = ?";
        Integer categoryId = null;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    categoryId = rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving category ID: " + e.getMessage());
        }

        return categoryId;
    }


}
