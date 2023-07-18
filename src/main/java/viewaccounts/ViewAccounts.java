package viewaccounts;

import javax.swing.table.DefaultTableModel;
import com.sun.jdi.DoubleType;
import database.DatabaseConnection;
import main.Main;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

public class ViewAccounts extends Main
{ public static final String columns[] =
    {"Account Name", "Type"};
  public static final String types[] =
    {"", "Checking", "Credit", "Savings",
      "Investment", "Car Loan", "Mortgage", "Education Debt"};
  public static JComboBox<String> typeComboBox = new JComboBox<>();
  public static DefaultTableModel model = new DefaultTableModel(columns, 0);
  public static JTable table = new JTable(model);
  public static JScrollPane scroll_pane = new JScrollPane(table);
  public static GridBagConstraints gbc = new GridBagConstraints();
  private JButton saveButton;
  private JButton loadButton;
  private static final String URL =
    "jdbc:mysql://localhost:3306/transactions_database";
  private static final String USER = System.getenv("DB_USERNAME");
  private static final String PASSWORD = System.getenv("DB_USERNAME");
  private static final String SELECT_QUERY = "SELECT * FROM view_accounts_table";
  private static final String INSERT_QUERY =
    "INSERT INTO view_accounts_table (Account_Name, Type) VALUES (?, ?)";
  private static final String TRUNCATE_QUERY = "TRUNCATE TABLE view_accounts_table";

  // CONSTRUCTOR
  public ViewAccounts()
  { saveButton = new JButton("Save");
    loadButton = new JButton("Load");
    table.getTableHeader().setReorderingAllowed(false);
    table.setPreferredScrollableViewportSize(new Dimension(750, 300));
    table.setAutoCreateRowSorter(true);
    
    TableCellEditor nonEditableCellEditor = new DefaultCellEditor(new JTextField())
    { @Override
      public boolean isCellEditable(EventObject e)
      { return false;
      }
    };
    
    table.getColumnModel().getColumn(0).setCellEditor(nonEditableCellEditor);

    if (typeComboBox.getItemCount() < 4)
    { for (String s : types)
      { typeComboBox.addItem(s);
      }
      table.getColumnModel().getColumn(1).setCellEditor
        (new DefaultCellEditor(typeComboBox));
    }

    saveButton.addActionListener(new ActionListener()
    { @Override
      public void actionPerformed(ActionEvent e)
      { save_table();
      }
    });
    
    loadButton.addActionListener(new ActionListener()
    { @Override
      public void actionPerformed(ActionEvent e)
      { load_table();
      }
    });
    
    gbc.gridx = 0;
    gbc.gridy = 2;
    frame.add(scroll_pane, gbc);
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.WEST;
    frame.add(loadButton, gbc);
    gbc.anchor = GridBagConstraints.EAST;
    frame.add(saveButton, gbc);
    gbc.anchor = GridBagConstraints.CENTER;
  }
  
  // Queries the database to return a list containing the account names contained in
  // transactions_table that are NOT contained in our view accounts table.
  public static List<String> get_new_acc_names()
  { List<String> output_list = new ArrayList<>();
    try (Connection conn = getConnection();
         Statement st = conn.createStatement())
    { // select all account names from transactions table that are not in view
      // accounts table.
      String query = "SELECT t.Account_Name FROM transactions_table t " +
        "WHERE t.Account_Name " +
        "NOT IN (Select v.Account_name FROM view_accounts_table v)";
      ResultSet rs = st.executeQuery(query);
      while (rs.next())
      { output_list.add(rs.getString(1));
      }
    }
    catch (SQLException e)
    { throw new RuntimeException(e);
    }
    // get rid of duplicates by mapping to a set then back to a list. sets do not
    // contain duplicates by definition.
    Set<String> set = new HashSet<>(output_list);
    output_list = new ArrayList<>(set);
    return output_list;
  }
  
  // Queries the database to return a list containing the account names contained
  // in our view accounts table that are NOT contained in transactions_table.
  // These will need to be removed.
  public static List<String> get_names_to_remove()
  { List<String> output_list = new ArrayList<>();
    try (Connection conn = getConnection();
         Statement st = conn.createStatement())
    { // select all account names from transactions table that are not in view accounts table.
      String query = "SELECT Account_Name FROM view_accounts_table " +
        "WHERE Account_NAME NOT IN " +
        "(SELECT ACCOUNT_NAME FROM transactions_table)";
      ResultSet rs = st.executeQuery(query);
      while (rs.next())
      { output_list.add(rs.getString(1));
      }
    }
    catch (SQLException e)
    { throw new RuntimeException(e);
    }
    // get rid of duplicates by mapping to a set then back to a list. sets do not
    // contain duplicates by definition.
    Set<String> set = new HashSet<>(output_list);
    output_list = new ArrayList<>(set);
    return output_list;
  }
  
  private void load_table()
  { try (Connection conn = this.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SELECT_QUERY))
    { // Clear the model
      String acc_name = "";
      String type = "";
      model.setRowCount(0);
      while (rs.next())
      { acc_name = rs.getString("Account_Name");
        type = rs.getString("Type");
        // don't add a row if the account name is contained in the remove list.
        if (!get_names_to_remove().contains(acc_name))
        { model.addRow(new Object[]{acc_name, type});
        }
      }
      List<String> new_names = get_new_acc_names();
      for (int i = 0; i < new_names.size(); ++i)
      { model.addRow(new Object[]{new_names.get(i), ""});
      }
    }
    catch (SQLException ex)
    { ex.printStackTrace();
    }
  }
  
  private void save_table()
  { try (Connection conn = this.getConnection();
         Statement stmt = conn.createStatement();
         PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY))
    { stmt.executeUpdate(TRUNCATE_QUERY);
      for (int i = 0; i < model.getRowCount(); i++)
      { pstmt.setString(1, (String) model.getValueAt(i, 0));
        pstmt.setString(2, (String) model.getValueAt(i, 1));
        pstmt.executeUpdate();
      }
    }
    catch (SQLException ex)
    { ex.printStackTrace();
    }
  }
  
  public static Connection getConnection() throws SQLException
  { return DriverManager.getConnection(URL, USER, PASSWORD);
  }
  
  public void disassemble()
  { frame.remove(scroll_pane);
    frame.remove(saveButton);
    frame.remove(loadButton);
  }
}