import java.util.ArrayList;
import java.util.Scanner;

public class ToDOList_App {
    private ArrayList<Task> tasks;
    private Scanner scanner;
    
    
    public ToDOList_App() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        ToDOList_App app = new ToDOList_App();
        app.run();
    }
    
    /**
     * Main application loop
     */
    public void run() {
        boolean running = true;
        
        System.out.println("Welcome to ToDo List Application!");
        
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    markTaskAsComplete();
                    break;
                case 4:
                    displayTasks();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using ToDo List App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display the main menu options
     */
    private void displayMenu() {
        System.out.println("\n===== ToDo List Menu =====");
        System.out.println("1. Add a new task");
        System.out.println("2. Remove a task");
        System.out.println("3. Mark a task as complete");
        System.out.println("4. Display all tasks");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }
    
    /**
     * Get user's menu choice
     */
    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
    
    /**
     * Add a new task to the list
     */
    private void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        
        if (description.trim().isEmpty()) {
            System.out.println("Task description cannot be empty.");
            return;
        }
        
        System.out.print("Enter priority (High/Medium/Low): ");
        String priority = scanner.nextLine();
        
        if (!priority.equalsIgnoreCase("High") && 
            !priority.equalsIgnoreCase("Medium") && 
            !priority.equalsIgnoreCase("Low")) {
            priority = "Medium"; // Default priority
            System.out.println("Invalid priority. Setting to Medium.");
        }
        
        Task newTask = new Task(description, priority);
        tasks.add(newTask);
        
        System.out.println("Task added successfully!");
    }
    
    /**
     * Remove a task from the list
     */
    private void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }
        
        displayTasks();
        System.out.print("Enter the number of the task to remove: ");
        
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine());
            
            if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                Task removedTask = tasks.remove(taskNumber - 1);
                System.out.println("Task removed: " + removedTask.getDescription());
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    /**
     * Mark a task as complete
     */
    private void markTaskAsComplete() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to mark as complete.");
            return;
        }
        
        displayTasks();
        System.out.print("Enter the number of the task to mark as complete: ");
        
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine());
            
            if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                Task task = tasks.get(taskNumber - 1);
                task.setComplete(true);
                System.out.println("Task marked as complete: " + task.getDescription());
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    /**
     * Display all tasks in the list
     */
    private void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
            return;
        }
        
        System.out.println("\n===== Your Tasks =====");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isComplete() ? "[✓]" : "[ ]";
            System.out.printf("%d. %s %s (Priority: %s)%n", 
                             i + 1, 
                             status, 
                             task.getDescription(),
                             task.getPriority());
        }
    }
    
    /**
     * Inner class to represent a Task
     */
    private class Task {
        private String description;
        private String priority;
        private boolean complete;
        
        /**
         * Constructor for creating a new task
         */
        public Task(String description, String priority) {
            this.description = description;
            this.priority = priority;
            this.complete = false;
        }
        
        /**
         * Get the task description
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * Get the task priority
         */
        public String getPriority() {
            return priority;
        }
        
        /**
         * Check if the task is complete
         */
        public boolean isComplete() {
            return complete;
        }
        
        /**
         * Set the completion status of the task
         */
        public void setComplete(boolean complete) {
            this.complete = complete;
        }
    }
}
