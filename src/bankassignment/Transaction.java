package bankassignment;

import java.io.Serializable;

/**
 *
 * @author Liam
 */
public class Transaction implements Serializable {
    private int transactionNumber;
    private String date;
    private String type;
    private double amount;
    
    private static int transactionCounter = 0;
    
    public Transaction(String date, String type, double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        
        // The use of the prefix increment operator here means that the first
        // transactionNumber will be 1, as the transactionnCounter is
        // incremented before being assigned to transactionNumber.
        transactionNumber = ++transactionCounter;
    }
    
    @Override
    public String toString() {
        // I am using a C-style format specifier here with the static format
        // method String to display a floating-point number ("f") with only
        // the first two digits after the decimal place (".2"). This is the
        // same as how values of money are usually displayed in real life.
        return transactionNumber + ". " + date + "\t" + type + ": " + String.format("%.2f", amount);
    }
}
