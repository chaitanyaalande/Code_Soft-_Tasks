import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private String category;
    private double amount;
    private Date date;
    private String description;

    public Expense(String category, double amount, Date date, String description) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format("%-15s $%-10.2f %-12s %s", 
                category, amount, dateFormat.format(date), description);
    }
}

class ExpenseManager {
    private List<Expense> expenses;
    private static final String DATA_FILE = "expenses.dat";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    public void addExpense(String category, double amount, Date date, String description) {
        Expense expense = new Expense(category, amount, date, description);
        expenses.add(expense);
        saveExpenses();
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public List<Expense> getExpensesByCategory(String category) {
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }

    public double calculateTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public double calculateCategoryExpenses(String category) {
        double total = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public boolean deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            saveExpenses();
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            expenses = (List<Expense>) ois.readObject();
            System.out.println("Expenses loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("No previous expense data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }

    private void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }
}

public class Expense_Tracker_App {
    private static Scanner scanner = new Scanner(System.in);
    private static ExpenseManager expenseManager = new ExpenseManager();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        System.out.println("===== EXPENSE TRACKER APPLICATION =====");
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewAllExpenses();
                    break;
                case 3:
                    viewExpensesByCategory();
                    break;
                case 4:
                    viewTotalExpenses();
                    break;
                case 5:
                    deleteExpense();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using the Expense Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. View Expenses by Category");
        System.out.println("4. View Total Expenses");
        System.out.println("5. Delete Expense");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addExpense() {
        System.out.println("\n===== ADD EXPENSE =====");
        
        System.out.print("Enter category (e.g., Food, Transport, Utilities): ");
        String category = scanner.nextLine();
        
        double amount = 0;
        while (true) {
            System.out.print("Enter amount: $");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
        
        Date date = null;
        while (date == null) {
            System.out.print("Enter date (dd-MM-yyyy): ");
            String dateStr = scanner.nextLine();
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            }
        }
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        expenseManager.addExpense(category, amount, date, description);
        System.out.println("Expense added successfully!");
    }

    private static void viewAllExpenses() {
        System.out.println("\n===== ALL EXPENSES =====");
        List<Expense> allExpenses = expenseManager.getAllExpenses();
        
        if (allExpenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        
        displayExpenseHeader();
        for (int i = 0; i < allExpenses.size(); i++) {
            System.out.println((i + 1) + ". " + allExpenses.get(i));
        }
        
        System.out.printf("\nTotal Expenses: $%.2f\n", expenseManager.calculateTotalExpenses());
    }

    private static void viewExpensesByCategory() {
        System.out.println("\n===== EXPENSES BY CATEGORY =====");
        System.out.print("Enter category to filter: ");
        String category = scanner.nextLine();
        
        List<Expense> filteredExpenses = expenseManager.getExpensesByCategory(category);
        
        if (filteredExpenses.isEmpty()) {
            System.out.println("No expenses found for category: " + category);
            return;
        }
        
        displayExpenseHeader();
        for (int i = 0; i < filteredExpenses.size(); i++) {
            System.out.println((i + 1) + ". " + filteredExpenses.get(i));
        }
        
        System.out.printf("\nTotal %s Expenses: $%.2f\n", 
                category, expenseManager.calculateCategoryExpenses(category));
    }

    private static void viewTotalExpenses() {
        System.out.println("\n===== TOTAL EXPENSES =====");
        double total = expenseManager.calculateTotalExpenses();
        System.out.printf("Total Expenses: $%.2f\n", total);
        
        // Show breakdown by category
        System.out.println("\nBreakdown by Category:");
        Map<String, Double> categoryTotals = new HashMap<>();
        
        for (Expense expense : expenseManager.getAllExpenses()) {
            String category = expense.getCategory();
            double amount = expense.getAmount();
            categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
        }
        
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.printf("%-15s: $%.2f (%.1f%%)\n", 
                    entry.getKey(), 
                    entry.getValue(), 
                    (entry.getValue() / total) * 100);
        }
    }

    private static void deleteExpense() {
        System.out.println("\n===== DELETE EXPENSE =====");
        List<Expense> allExpenses = expenseManager.getAllExpenses();
        
        if (allExpenses.isEmpty()) {
            System.out.println("No expenses to delete.");
            return;
        }
        
        displayExpenseHeader();
        for (int i = 0; i < allExpenses.size(); i++) {
            System.out.println((i + 1) + ". " + allExpenses.get(i));
        }
        
        System.out.print("\nEnter the number of the expense to delete (1-" + allExpenses.size() + "): ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (expenseManager.deleteExpense(index)) {
                System.out.println("Expense deleted successfully!");
            } else {
                System.out.println("Invalid expense number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void displayExpenseHeader() {
        System.out.println("\nCategory        Amount      Date         Description");
        System.out.println("----------------------------------------------------------");
    }
}
