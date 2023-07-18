package savingsinvestmentaccounts;

import main.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class SavingsInvestmentAccounts extends Main
{ public static final String columns[] =
    {"Date", "Description", "Amount", "Transaction Type", "Account Name"};
  public static DefaultTableModel model = new DefaultTableModel(columns, 0);
  public static JTable table = new JTable(model);
  public static JScrollPane scroll_pane = new JScrollPane(table);
  public static GridBagConstraints gbc = new GridBagConstraints();
  private static final String URL =
    "jdbc:mysql://localhost:3306/transactions_database";
  private static final String USER = System.getenv("DB_USERNAME");
  private static final String PASSWORD = System.getenv("DB_PASSWORD");

  // CONSTRUCTOR
  public SavingsInvestmentAccounts()
  { table.getTableHeader().setReorderingAllowed(false);
    table.setPreferredScrollableViewportSize(new Dimension(750, 350));
    table.setAutoCreateRowSorter(true);
    TableCellEditor nonEditableCellEditor = new DefaultCellEditor(new JTextField())
    { @Override
    public boolean isCellEditable(EventObject e)
    { return false;
    }
    };
    table.getColumnModel().getColumn(0).setCellEditor(nonEditableCellEditor);
    table.getColumnModel().getColumn(1).setCellEditor(nonEditableCellEditor);
    table.getColumnModel().getColumn(2).setCellEditor(nonEditableCellEditor);
    table.getColumnModel().getColumn(3).setCellEditor(nonEditableCellEditor);
    table.getColumnModel().getColumn(4).setCellEditor(nonEditableCellEditor);
    gbc.gridx = 0;
    gbc.gridy = 2;
    frame.add(scroll_pane, gbc);
    try
    { Connection conn = getConnection();
      load_transactions(conn, get_acc_names(conn));
    }
    catch (SQLException e)
    { throw new RuntimeException(e);
    }
  }
  
  // Returns a list of strings comprising all account names from view_accounts_table
  // where type is Savings and Investment.
  public List<String> get_acc_names(Connection conn)
  { List<String> accountNames = new ArrayList<>();
    String sql = "SELECT Account_Name FROM view_accounts_table " +
      "WHERE Type IN ('Savings', 'Investment')";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql))
    { while (rs.next())
      { accountNames.add(rs.getString("Account_Name"));
      }
    }
    catch (SQLException e)
    { System.out.println(e.getMessage());
    }
    return accountNames;
  }
  
  // Uses the list of strings provided by get_acc_names to load all transactions
  // from the transactions_table that relate to checking and savings and investment accounts.
  public void load_transactions(Connection conn, List<String> account_names)
  { if (account_names.size() == 0) return;
    String sql = "SELECT * FROM transactions_table WHERE Account_Name IN (";
    for (int i = 0; i < account_names.size(); i++)
    { sql += "'" + account_names.get(i) + "'";
      if (i < account_names.size() - 1)
      { sql += ", ";
      }
    }
    sql += ")";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql))
    { // Clear the model
      model.setRowCount(0);
      while (rs.next())
      { // retrieve the values of the needed columns from the ResultSet
        String date = rs.getString("Date");
        String description = rs.getString("Description");
        BigDecimal amount = rs.getBigDecimal("Amount");
        String type = rs.getString("Type");
        String acc_name = rs.getString("Account_Name");
        int id = rs.getInt("ID");
        model.addRow(new Object[]{date, description, amount, type, acc_name});
      }
    }
    catch (SQLException e)
    { System.out.println(e.getMessage());
    }
  }
  
  private Connection getConnection() throws SQLException
  { return DriverManager.getConnection(URL, USER, PASSWORD);
  }
  
  public void disassemble()
  { frame.remove(scroll_pane);
  }
}

