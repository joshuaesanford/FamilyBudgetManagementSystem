package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/transactions_database?useSSL=false";
        String username = "root";
        String password = "root";

        System.out.println("Connecting to database...");

        try (Connection connection = DriverManager.getConnection(url, username, password))
        { System.out.println("Database connected!");
          return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }
}