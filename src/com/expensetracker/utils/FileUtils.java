package com.expensetracker.utils;

import com.expensetracker.model.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<Transaction> loadFromFile(String filePath) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0].trim();
                String category = parts[1].trim();
                double amount = Double.parseDouble(parts[2].trim());
                LocalDate date = LocalDate.parse(parts[3].trim(), formatter);
                transactions.add(new Transaction(type, category, amount, date));
            }
            System.out.println(" Data loaded from file.");
        } catch (IOException e) {
            System.out.println(" Error reading file: " + e.getMessage());
        }

        return transactions;
    }

    public static void saveToFile(String filePath, List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("type,category,amount,date");
            for (Transaction t : transactions) {
                writer.println(t.toString());
            }
            System.out.println(" Data Saved To File.");
        } catch (IOException e) {
            System.out.println(" Error writing to file: " + e.getMessage());
        }
    }
}
