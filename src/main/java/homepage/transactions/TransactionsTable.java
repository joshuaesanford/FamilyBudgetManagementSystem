package homepage.transactions;

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
import java.util.Arrays;
import java.util.List;
import java.sql.*;

import javax.swing.*;

public class TransactionsTable extends Main
{
  public static final String columns[] =
    {"Date", "Description:", "Amount:", "Type:", "Details:"};
  public static final String types[] =
    {"", "Unavoidable Expense", "Luxury Expense", "Deposit",
      "Withdrawal"};
  public static JComboBox<String> typeComboBox = new JComboBox<>();
  public static DefaultTableModel model = new DefaultTableModel(columns, 0);
  public static JTable table = new JTable(model);
  public static JScrollPane scroll_pane = new JScrollPane(table);
  public static GridBagConstraints gbc = new GridBagConstraints();
  private JButton addButton;
  private JButton removeButton;
  private JButton saveButton;
  private JButton loadButton;
  private static final String URL = "jdbc:mysql://localhost:3306/transactions_database";
  private static final String USER = "root";
  private static final String PASSWORD = "root";
  private static final String SELECT_QUERY = "SELECT * FROM transactions_table";
  private static final String INSERT_QUERY = "INSERT INTO transactions_table (Date, Description, Amount, Type, Details) VALUES (?, ?, ?, ?, ?)";
  private static final String TRUNCATE_QUERY = "TRUNCATE TABLE transactions_table";
  public TransactionsTable()
  { addButton = new JButton("+");
    removeButton = new JButton("-");
    saveButton = new JButton("Save");
    loadButton = new JButton("Load");
    
    table.getTableHeader().setReorderingAllowed(false);
    table.setPreferredScrollableViewportSize(new Dimension(750, 525));
    
    if (typeComboBox.getItemCount() < 4)
    { for (String s : types)
      { typeComboBox.addItem(s);
      }
      table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(typeComboBox));
    }
    
    addButton.addActionListener(new ActionListener()
    { @Override
      public void actionPerformed(ActionEvent e)
      { model.addRow(new Object[]{"MM.DD.YY", "[Add Description]", "[Enter Amount]", "", ""});
      }
    });
    
    removeButton.addActionListener(new ActionListener()
    { @Override
      public void actionPerformed(ActionEvent e)
      { model.removeRow(table.getSelectedRow());
      }
    });
    
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
    
    typeComboBox.addItemListener(new ItemListener()
    { @Override
      public void itemStateChanged(ItemEvent e)
      { if (e.getStateChange() == ItemEvent.SELECTED)
        { String selectedItem = (String) e.getItem();
          int selectedRow = table.getSelectedRow();
          int selectedColumn = table.getSelectedColumn();
          if (selectedItem == "")
          { table.getModel().setValueAt("", selectedRow, selectedColumn + 1);
          }
          if (selectedItem == "Unavoidable Expense")
          { table.getModel().setValueAt("SUBCATEGORY_NAME", selectedRow, selectedColumn + 1);
          }
          else if (selectedItem == "Luxury Expense")
          { table.getModel().setValueAt("SUBCATEGORY_NAME", selectedRow, selectedColumn + 1);
          }
          else if (selectedItem == "Deposit")
          { table.getModel().setValueAt("ACCOUNT_NAME", selectedRow, selectedColumn + 1);
          }
          else if (selectedItem == "Withdrawal")
          { table.getModel().setValueAt("ACCOUNT_NAME", selectedRow, selectedColumn + 1);
          }
          else
          { table.getModel().setValueAt("SUBCATEGORY_NAME", selectedRow, selectedColumn + 1);
          }
        }
      }
    });
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    frame.add(scroll_pane, gbc);
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.WEST;
    frame.add(addButton, gbc);
    gbc.anchor = GridBagConstraints.EAST;
    frame.add(removeButton, gbc);
    gbc.anchor = GridBagConstraints.CENTER;
    frame.add(loadButton, gbc);
    gbc.gridy = 2;
    frame.add(saveButton, gbc);
  }
  
  public void add_row(String date, String description, String amount, String type, String classification)
  { model.addRow(new Object[]{date, description, amount, type, classification});
  }
  
  private void load_table()
  { try (Connection conn = this.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SELECT_QUERY))
    { // Clear the model
      model.setRowCount(0);
      // Load the data into the model
      while (rs.next())
      { String date = rs.getString("Date");
        String description = rs.getString("Description");
        double amount = rs.getDouble("Amount");
        String type = rs.getString("Type");
        String details = rs.getString("Details");
        model.addRow(new Object[]{date, description, amount, type, details});
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
        double pushVal = str_to_double(model.getValueAt(i, 2).toString());
        pstmt.setDouble(3, pushVal);
        pstmt.setString(4, (String) model.getValueAt(i, 3));
        pstmt.setString(5, (String) model.getValueAt(i, 4));
        pstmt.executeUpdate();
      }
    }
    catch (SQLException ex)
    { ex.printStackTrace();
    }
  }
  
  private Connection getConnection() throws SQLException
  { return DriverManager.getConnection(URL, USER, PASSWORD);
  }
  
  
  public static double str_to_double(String str)
  { try
    { return Double.parseDouble(str);
    }
    catch (NumberFormatException e)
    { System.out.println("Invalid string for conversion to double: " + str);
      return 0.0;
    }
  }
  
  public void disassemble()
  {
    frame.remove(scroll_pane);
    frame.remove(addButton);
    frame.remove(removeButton);
    frame.remove(saveButton);
    frame.remove(loadButton);
  }
  }
