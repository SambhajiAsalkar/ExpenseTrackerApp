package com.expensetracker.service;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.expensetracker.model.Transaction;

public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void viewMonthlySummary(YearMonth month) {
        double totalIncome = 0, totalExpense = 0;
        Map<String, Double> incomeBreakdown = new HashMap<>();
        Map<String, Double> expenseBreakdown = new HashMap<>();

        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(month)) {
                if (t.getType().equalsIgnoreCase("income")) {
                    totalIncome += t.getAmount();
                    incomeBreakdown.merge(t.getCategory(), t.getAmount(), Double::sum);
                } else {
                    totalExpense += t.getAmount();
                    expenseBreakdown.merge(t.getCategory(), t.getAmount(), Double::sum);
                }
            }
        }

        System.out.println("\n--- Monthly Summary For " + month + " ---");
        System.out.println("Total Income: $" + totalIncome);
        incomeBreakdown.forEach((k, v) -> System.out.println("  " + k + ": $" + v));
        System.out.println("Total Expense: $" + totalExpense);
        expenseBreakdown.forEach((k, v) -> System.out.println("  " + k + ": $" + v));
    }
}
