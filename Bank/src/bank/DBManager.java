package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBManager {
    
    public Connection connection;
    
    public DBManager() {
        String connectionString = "jdbc:sqlserver://localhost;Database=bank;integratedSecurity=true;encrypt=false";
        try {
            connection = DriverManager.getConnection(connectionString);
            if (connection != null) {
                System.out.println("DB Connected.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Boolean isUsernameExists(String username) {
        try {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<User> getUsers(String role) {
        ArrayList<User> users = new ArrayList<>();    
        try {
            String query = "SELECT id FROM users WHERE role = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int userId = resultSet.getInt("id");
                User user = new User(this, userId);
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
}
