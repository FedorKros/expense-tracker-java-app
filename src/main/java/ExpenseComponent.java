import java.time.LocalDate;

public class ExpenseComponent {
    // id will be handled by the SQLite
    private int id;

    private double amount;
    private String description;
    public String category;
    private LocalDate date;

    // Constructor for creating an expense instance
    public ExpenseComponent(double amount, String description, String category) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = LocalDate.now();
    }

    // Constructor for loading from the db
    public ExpenseComponent(int id, double amount, String description, String category, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    // Getters
    public int getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public LocalDate getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "[" + amount + " " + description + " " + category + " " + date + "]";
    }

}
