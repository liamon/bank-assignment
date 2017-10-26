package bankassignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/**
 *
 * @author Liam
 */
public class BankAssignment {
    // I decided to put the filenames in static variables here. This would be
    // preferable in a real world situation. This is because if you needed to
    // change a filename, you would only have to do it in one place.
    private static final String TRANSACTION_FILE = "transactions.bin";
    private static final String ACCOUNT_FILE = "accountdetails.bin";
    private static final String OVERDRAFT_FILE = "overdraft.txt";
    
    public static void main(String[] args) {
        // I am allowed to use a trailing comma at the end of the list of array
        // elements, which makes adding more elements on at the end easier.
        Transaction[] transactions = {
            new Transaction("16/08/2017", "Open Account", 100),
            new Transaction("22/08/2017", "Withdraw", 50),
            new Transaction("23/09/2017", "Deposit", 100),
        };
        
        serializeToFile(transactions, TRANSACTION_FILE);
        Transaction[] reconstructedArray = deserializeFromFile(TRANSACTION_FILE);
        System.out.print(arrayToString(reconstructedArray));
        
        BankAccount bankAccount = new BankAccount("Aoife Jones", "16/08/2017", 100);
        bankAccount.withdraw("22/08/2017", 200);
        bankAccount.deposit("23/08/2017", 100);
        bankAccount.withdraw("01/09/2017", 50);
        
        serializeToFile(bankAccount, ACCOUNT_FILE);
        BankAccount reconstructedAccount = deserializeFromFile(ACCOUNT_FILE);
        System.out.println(reconstructedAccount);
        System.out.print(arrayToString(reconstructedAccount.getTransactionDetail()));
        
        askToIncreaseOverdraft();
    }
    
    // My original implementation of the following two methods only
    // worked for Transaction[]. However, I made the
    // methods to serialize and deserialize from files generic so
    // they will work with both Transaction[] and BankAccount.
    
    private static <T> void serializeToFile(T object, String filename) {
        ObjectOutputStream file;
        try {
            file = new ObjectOutputStream(new FileOutputStream(filename));
            file.writeObject(object);
            file.flush();
            file.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private static <T> T deserializeFromFile(String filename) {
        T object = null;
        ObjectInputStream input;
        try {
            input = new ObjectInputStream(new FileInputStream(filename));
            object = (T) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return object;
    }
    
    // Although this does not strictly need to be, I decided to make this method
    // generic as well, so I can potentially reuse it in another program.
    private static <T> String arrayToString(T[] array) {
        StringBuilder sb = new StringBuilder();
        for (T element : array) {
            sb.append(element);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private static void askToIncreaseOverdraft() {
        RandomAccessFile overdraftQuestion;
        try {
            // Originally in this line I was incorrectly thinking in terms of
            // C and put in just "w" as the mode.
            overdraftQuestion = new RandomAccessFile(OVERDRAFT_FILE, "rw");
            
            // Note that this code assumes that all English characters are
            // being stored in one byte, which holds for character encodings like
            // ASCII and UTF-8 without Byte-Order Mark.
            
            // This seek will put us at the very end of the file.
            overdraftQuestion.seek(overdraftQuestion.length());
            overdraftQuestion.writeBytes("No");
            
            overdraftQuestion.seek(0);
            System.out.println(overdraftQuestion.readLine());
            overdraftQuestion.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
