package bank;

import java.sql.*;
import java.util.ArrayList;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
    private ArrayList<Account> accounts = new ArrayList<>();    
            
    private DBManager dbManager;
    
    public User(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public User(DBManager dbManager, int id) {
        this.dbManager = dbManager;
        this.id = id;
        this.fetchDataByUserId(id);
    }
    
    public User(DBManager dbManager, int id, String username, String password, String firstname, String lastname, String role) {
        this.dbManager = dbManager;
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getFirstname() {
        return this.firstname;
    }
    
    public String getLastname() {
        return this.lastname;
    }
    
    public String getRole() {
        return this.role;
    }
    
    public Boolean isManager() {
        return this.role.equals("manager");
    }
    
    public Boolean isBanker() {
        return this.role.equals("banker");
    }
    
    public Boolean isCustomer() {
        return this.role.equals("customer");
    }
    
    public Boolean login(String inputUsername, String inputPassword) {
        try {
            String query = "SELECT id FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setString(1, inputUsername);
            statement.setString(2, inputPassword);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int userId = resultSet.getInt("id");
                this.fetchDataByUserId(userId);
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public Boolean fetchDataByUserId(int userId) {
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                this.id = resultSet.getInt("id");
                this.username = resultSet.getString("username").trim();
                this.password = resultSet.getString("password").trim();
                this.firstname = resultSet.getString("firstname").trim();
                this.lastname = resultSet.getString("lastname").trim();
                this.role = resultSet.getString("role").trim();
                this.fetchAccounts();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public int fetchAccounts() {
        this.accounts.clear();
        int accountCount = 0;
        try {
            String query = "SELECT * FROM accounts WHERE user_id = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int accountId = resultSet.getInt("id");
                Account account = new Account(this.dbManager, this, accountId);
                this.accounts.add(account);
                accountCount++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return accountCount;
    }
    
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    
    public Account getAccountById(int accountId) {
        for (Account account : this.accounts) {
            if(account.getId() == accountId) {
                return account;
            }
        }
        return null;
    }
    
    public Boolean createAccount(String name) {
        try {
            String query = "INSERT INTO accounts (user_id, name) VALUES (?,?)";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setInt(1, this.id);
            statement.setString(2, name);
            
            int effectedRowCount = statement.executeUpdate();
            if(effectedRowCount == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    private Boolean insertUser() {
        try {
            String query = "INSERT INTO users (username, password, firstname, lastname, role) VALUES (?,?,?,?,?)";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.username);
            statement.setString(2, this.password);
            statement.setString(3, this.firstname);
            statement.setString(4, this.lastname);
            statement.setString(5, this.role);
            
            int effectedRowCount = statement.executeUpdate();
            if(effectedRowCount == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    private Boolean updateUser() {
        try {
            String query = "UPDATE users SET username = ?, password = ?, firstname = ?, lastname = ?, role = ? WHERE id = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setString(1, this.username);
            statement.setString(2, this.password);
            statement.setString(3, this.firstname);
            statement.setString(4, this.lastname);
            statement.setString(5, this.role);
            statement.setInt(6, this.id);
            
            int effectedRowCount = statement.executeUpdate();
            if(effectedRowCount == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public Boolean save() {
        if (this.id > 0) {
            return this.updateUser();
        }
        return this.insertUser();
    }
    
    public Boolean delete() {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = this.dbManager.connection.prepareStatement(query);
            statement.setInt(1, this.id);

            int effectedRowCount = statement.executeUpdate();
            if(effectedRowCount == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
