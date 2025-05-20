package com.expensetracker;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.expensetracker.utils.FileUtils;
import com.expensetracker.model.Transaction;
import com.expensetracker.service.TransactionService;

public class ExpenseTrackerApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final TransactionService service = new TransactionService();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Expense Tracker ===");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Load from File");
            System.out.println("5. Save to File");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1 -> addTransaction("income");
                case 2 -> addTransaction("expense");
                case 3 -> viewSummary();
                case 4 -> loadFromFile();
                case 5 -> saveToFile();
                case 6 -> {
                    System.out.println(" Thank You !");
                    return;
                }
                default -> System.out.println("Invalid Choice.");
            }
        }
    }

    private static void addTransaction(String type) {
        System.out.print("Enter category (" +
                (type.equals("income") ? "salary/business" : "food/rent/travel") + "): ");
        String category = sc.nextLine();
        
        System.out.print(" Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine(), formatter);

        service.addTransaction(new Transaction(type, category, amount, date));
        System.out.println("Transaction Added Successfully.");
    }

    private static void viewSummary() {
        System.out.print("Enter Month (yyyy-MM): ");
        YearMonth month = YearMonth.parse(sc.nextLine());
        service.viewMonthlySummary(month);
    }

    private static void loadFromFile() {
        System.out.print("Enter File Path: ");
        String filePath = sc.nextLine();
        List<Transaction> loaded = FileUtils.loadFromFile(filePath);
        service.setTransactions(loaded);
    }

    private static void saveToFile() {
        System.out.print("Enter File Path To Save: ");
        String filePath = sc.nextLine();
        FileUtils.saveToFile(filePath, service.getAllTransactions());
    }
}
