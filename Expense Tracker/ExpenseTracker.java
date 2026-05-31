import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

// Expense class
class Expense implements Serializable {
    double amount;
    String category;
    LocalDate date;

    Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String toString() {
        return "Amount: " + amount +
                ", Category: " + category +
                ", Date: " + date;
    }
}

// Main class
public class ExpenseTracker {

    static ArrayList<Expense> expenses = new ArrayList<>();
    static final String FILE_NAME = "expenses.dat";

    // Add expense
    static void addExpense(double amount, String category, LocalDate date) {
        expenses.add(new Expense(amount, category, date));
    }

    // Display all expenses
    static void displayExpenses() {
        System.out.println("\nAll Expenses:");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    // Monthly report
    static void monthlyReport(Month month) {
        double total = 0;

        System.out.println("\nMonthly Report for " + month);

        for (Expense e : expenses) {
            if (e.date.getMonth() == month) {
                System.out.println(e);
                total += e.amount;
            }
        }

        System.out.println("Total Expense: " + total);
    }

    // Highest expense category
    static void highestCategory() {
        HashMap<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {
            map.put(e.category,
                    map.getOrDefault(e.category, 0.0) + e.amount);
        }

        String maxCategory = "";
        double maxAmount = 0;

        for (String category : map.keySet()) {
            if (map.get(category) > maxAmount) {
                maxAmount = map.get(category);
                maxCategory = category;
            }
        }

        System.out.println("\nHighest Expense Category: "
                + maxCategory + " = " + maxAmount);
    }

    // Save data to file
    static void saveData() {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            out.writeObject(expenses);
            out.close();

            System.out.println("\nData Saved Successfully.");
        } catch (Exception e) {
            System.out.println("Error Saving File.");
        }
    }

    // Load data from file
    static void loadData() {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            expenses = (ArrayList<Expense>)in.readObject();
            in.close();

            System.out.println("Data Loaded Successfully.");
        } catch (Exception e) {
            System.out.println("No Previous Data Found.");
        }
    }

    // Main method
    public static void main(String[] args) {

        // Load old data
        loadData();

        // Adding multiple entries
        addExpense(5000, "Food", LocalDate.of(2026, 5, 10));
        addExpense(1500, "Shopping", LocalDate.of(2026, 5, 12));
        addExpense(1000, "Travel", LocalDate.of(2026, 5, 15));
        addExpense(1500, "Food", LocalDate.of(2026, 5, 18));

        // Display expenses
        displayExpenses();

        // Monthly report
        monthlyReport(Month.MAY);

        // Highest expense category
        highestCategory();

        // Save data
        saveData();
    }
}