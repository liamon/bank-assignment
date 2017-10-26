package bankassignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liam
 */
public class BankAccount implements Serializable {
    private String name;
    private String date;
    private int accountNumber;
    private double balance;
    private List<Transaction> transactions = new ArrayList();
    
    // The transient keyword means that this variable will not be serialized.
    private transient double overdraft;
    
    private static int accountNumberCounter = 1000;
    
    // An example of constructor overloading, using this() to call
    // another constructor of itself.
    public BankAccount(String name, String date, double balance) {
        this(name, date, balance, 0); // If overdraft is not specified, it is 0.
    }
    
    public BankAccount(String name, String date, double balance, double overdraft) {
        this.name = name;
        this.date = date;
        accountNumber = ++accountNumberCounter;
        this.balance = balance;
        transactions.add(new Transaction(date, "Open Account", balance));
        this.overdraft = overdraft;
    }
    
    public void deposit(String date, double amount) {
        balance += amount;
        transactions.add(new Transaction(date, "Deposit", amount));
    }
    
    public void withdraw(String date, double amount) {
        if (amount > balance) {
            // Semantically it makes more sense here to print to the
            // standard error stream instead of the standard output.
            System.err.println("Insufficient fund");
            System.err.println("Current balance: " + String.format("%.2f", balance));
            System.err.println("Tried to withdraw: " + String.format("%.2f", amount));
            return;
        }
        balance -= amount;
        transactions.add(new Transaction(date, "Withdraw", amount));
    }
    
    public Transaction[] getTransactionDetail() {
        // Passing an array of a certain type will make toArray return an array
        // of that same type. Since I am not using this array in the argument
        // again, I might as well make it as small as possible - size 0.
        return transactions.toArray(new Transaction[0]);
    }
    
    @Override
    public String toString() {
        return "Account Number: " + accountNumber + "  Name: " + name + "  Balance: " +
                String.format("%.2f", balance) + "  Overdraft: " + String.format("%.2f", overdraft);
    }
}
