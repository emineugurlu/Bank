package bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account {
    private int id;
    private int userId;
    private String name;
    private ArrayList<Transaction> transactions = new ArrayList<>();    
    
    private DBManager dbManager;
    private User user;
            
    public Account(DBManager dbManager, User user, int accountId) {
        this.dbManager = dbManager;
        this.user = user;
        this.fetchDataByAccountId(accountId);
    }
    
    public int getId() {
        return this.id;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }
    
    private Boolean createTransaction(String type, float amount, String status) {
        try {
            String query = "INSERT INTO transactions (account_id, type, amount, status) VALUES (?,?,?,?)";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setInt(1, this.id);
            statement.setString(2, type);
            statement.setFloat(3, amount);
            statement.setString(4, status);
            
            int effectedRowCount = statement.executeUpdate();
            if(effectedRowCount == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public Boolean deposit(float amount) {
        return this.createTransaction("deposit", amount, "approved");
    }
    
    public Boolean withdrawal(float amount) {
        return this.createTransaction("withdrawal", amount, "approved");
    }
    
    public Boolean requestCredit(float amount) {
        return this.createTransaction("credit", amount, "pending");
    }
    
    private Boolean updateCreditStatus(int transactionId, String status) {
         try {
            String query = "UPDATE transactions SET status = ? WHERE id = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setString(1, status);
            statement.setInt(2, transactionId);
            
            int effectedRowCount = statement.executeUpdate();
            if(effectedRowCount == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public Boolean approveCredit(int transactionId) {
        return this.updateCreditStatus(transactionId, "approved");
    }
    
    public Boolean rejectCredit(int transactionId) {
        return this.updateCreditStatus(transactionId, "rejected");
    }
    
    public float getBalance() {
        float balance = 0;
        for (Transaction transaction : this.transactions) {
            if(!transaction.isApproved()) {
                continue;
            }
            
            if(transaction.isDeposit() || transaction.isCredit()) {
                balance += transaction.getAmount();
            } else if (transaction.isWithdrawal()) {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }
    
    public ArrayList<Transaction> getCreditTransactions() {
        ArrayList<Transaction> creditTransactions = new ArrayList<>(); 
        for (Transaction transaction : this.transactions) {
            if(transaction.isCredit()) {
                creditTransactions.add(transaction);
            }
        }
        return creditTransactions;
    }
    
    public Transaction getTransactionById(int transactionId) {
        for (Transaction transaction : this.transactions) {
            if(transaction.getId() == transactionId) {
                return transaction;
            }
        }
        return null;
    }
    
    public Boolean fetchDataByAccountId(int accountId) {
        try {
            String query = "SELECT * FROM accounts WHERE id = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                this.id = resultSet.getInt("id");
                this.userId = resultSet.getInt("user_id");
                this.name = resultSet.getString("name").trim();
                this.fetchTransactions();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public Boolean fetchTransactions() {
        this.transactions.clear();
        try {
            String query = "SELECT * FROM transactions WHERE account_id = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int transactionId = resultSet.getInt("id");
                int accountId = resultSet.getInt("account_id");
                String type = resultSet.getString("type").trim();
            float amount = resultSet.getFloat("amount");
                String status = resultSet.getString("status").trim();
                
                Transaction transaction = new Transaction(this, transactionId, accountId, type, amount, status);
                this.transactions.add(transaction);
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
