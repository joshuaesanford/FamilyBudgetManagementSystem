package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{ public static Connection getConnection()
  { String url = "jdbc:mysql://localhost:3306/transactions_database?useSSL=false";
    final String USER = System.getenv("DB_USERNAME");
    final String PASSWORD = System.getenv("DB_USERNAME");
    System.out.println("Connecting to database...");
    try (Connection connection = DriverManager.getConnection(url, USER, PASSWORD))
    { System.out.println("Database connected!");
      return connection;
    }
    catch (SQLException e)
    { throw new IllegalStateException("Cannot connect to database!", e);
    }
  }
}