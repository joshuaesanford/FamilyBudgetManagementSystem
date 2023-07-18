package reports;

import main.Main;
import net.sf.jasperreports.engine.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Report extends Main
{ private static final String URL =
    "jdbc:mysql://localhost:3306/transactions_database";
  private static final String USER = System.getenv("DB_USERNAME");
  private static final String PASSWORD = System.getenv("DB_PASSWORD");
  private JComboBox<String> combo_box;
  private JButton gen_rep_button;
  private String[] options =
    {"" , "Transactions Report", "Debt Report", "Income and Expenses Report",
      "Savings & Investments Report"};

  // CONSTRUCTOR
  public Report()
  { combo_box = new JComboBox<>(options);
    label_spacer = build_label("", Label.SPACER, null);
    gbc.gridx = 0;
    gbc.gridy = 2;
    frame.add(combo_box, gbc);
    gbc.gridy = 3;
    frame.add(label_spacer, gbc);
    gbc.gridy = 4;
    gen_rep_button = new JButton("Generate Report");
    frame.add(gen_rep_button, gbc);
    gen_rep_button.addActionListener(new ActionListener()
    { @Override
      public void actionPerformed(ActionEvent e)
      { String selectedOption = (String) combo_box.getSelectedItem();
        // Handle every possible case for selection in the comboBox
        switch (selectedOption)
        { case "Transactions Report":
          { get_transactions_report();
            JOptionPane.showMessageDialog
              (null, "Transactions Report generated in reports directory.");
            break;
          }
          case "Debt Report":
          { get_debt_report();
            JOptionPane.showMessageDialog
              (null, "Debt Report generated in reports directory.");
            break;
          }
          case "Income and Expenses Report":
          { get_income_expense_report();
            JOptionPane.showMessageDialog
              (null, "Income and Expenses Report generated in reports directory.");
            break;
          }
          case "Savings & Investments Report":
          { get_savings_investment_report();
            JOptionPane.showMessageDialog
              (null, "Savings & Investments Report generated in reports directory.");
            break;
          }
          default:
          { break;
          }
        } // end of switch over comboBox selections.
      } // end of actionPerformed event override.
    }); // end of adding actionListener to gen_rep_button
  } // end of constructor
  
  public void get_transactions_report()
  { Connection conn = null;
    try
    { conn = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement stmt = conn.createStatement();
      String sql = "SELECT Date, Description, Amount, Type, Account_Name " +
        "FROM transactions_table " +
        "ORDER BY Account_Name, Date";
      ResultSet rs = stmt.executeQuery(sql);
      // Convert ResultSet to JRResultSetDataSource
      JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
      // Specify the location of the JasperReports template file
      String reportJrxml = "./src/main/java/reports/transactions_report.jrxml";
      // Compile the JasperReports template
      JasperReport jasperReport = JasperCompileManager.compileReport(reportJrxml);
      // Create a map to hold any parameters required by the report
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("ReportTitle", "Transactions Report");
      params.put("ReportDate", new java.util.Date());
      params.put("IncludeDetail", Boolean.TRUE);
      // Fill the report with data
      JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrRS);
      // Specify the output directory for the PDF report
      File outDir = new File("./src/main/java/reports/");
      outDir.mkdirs();
      // Export the report to a PDF file
      String dest_file_name = "./src/main/java/reports/TransactionsReport.pdf";
      JasperExportManager.exportReportToPdfFile(print, dest_file_name);
      System.out.println("Transactions report written to reports directory.");
    }
    catch (Exception e)
    { e.printStackTrace();
    }
    finally
    { // Close the database connection
      if (conn != null)
      { try
        { conn.close();
        }
        catch (SQLException ignore)
        {
        }
      }
    }
  }
  
  public void get_debt_report()
  { Connection conn = null;
    String sql = "";
    try
    { conn = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement stmt = conn.createStatement();
      sql = "SELECT t.Date, t.Description, t.Amount, t.Type, t.Account_Name " +
        "FROM transactions_table t " +
        "JOIN view_accounts_table v " +
        "ON t.Account_Name = v.Account_Name " +
        "WHERE v.Type IN ('Mortgage', 'Car Loan', 'Education Debt') " +
        "ORDER BY t.Account_Name, t.Date";
      ResultSet rs = stmt.executeQuery(sql);
      
      // Convert ResultSet to JRResultSetDataSource
      JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
      
      // Specify the location of the JasperReports template file
      String reportJrxml = "./src/main/java/reports/debt_report.jrxml";
      // Compile the JasperReports template
      JasperReport jasperReport = JasperCompileManager.compileReport(reportJrxml);
      
      // Create a map to hold any parameters required by the report
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("ReportTitle", "Debt Report");
      params.put("ReportDate", new java.util.Date());
      params.put("IncludeDetail", Boolean.TRUE);
      // Fill the report with data
      JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrRS);
      // Specify the output directory for the PDF report
      File outDir = new File("./src/main/java/reports/");
      outDir.mkdirs();
      // Export the report to a PDF file
      String dest_file_name = "./src/main/java/reports/DebtReport.pdf";
      JasperExportManager.exportReportToPdfFile(print, dest_file_name);
      System.out.println("Debt report written to reports directory.");
    }
    catch (Exception e)
    { e.printStackTrace();
    }
    finally
    { // Close the database connection
      if (conn != null)
      { try
        { conn.close();
        }
        catch (SQLException ignore)
        {
        }
      }
    }
  }
  
  public void get_savings_investment_report()
  { Connection conn = null;
    String sql = "";
    try
    { conn = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement stmt = conn.createStatement();
      sql = "SELECT t.Date, t.Description, t.Amount, t.Type, t.Account_Name " +
        "FROM transactions_table t " +
        "JOIN view_accounts_table v " +
        "ON t.Account_Name = v.Account_Name " +
        "WHERE v.Type IN ('Savings', 'Investment') " +
        "ORDER BY t.Account_Name, t.Date";
      ResultSet rs = stmt.executeQuery(sql);
      // Convert ResultSet to JRResultSetDataSource
      JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
      // Specify the location of the JasperReports template file
      String reportJrxml =
        "./src/main/java/reports/savings_investments_report.jrxml";
      // Compile the JasperReports template
      JasperReport jasperReport = JasperCompileManager.compileReport(reportJrxml);
      // Create a map to hold any parameters required by the report
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("ReportTitle", "Debt Report");
      params.put("ReportDate", new java.util.Date());
      params.put("IncludeDetail", Boolean.TRUE);
      // Fill the report with data
      JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrRS);
      // Specify the output directory for the PDF report
      File outDir = new File("./src/main/java/reports/");
      outDir.mkdirs();
      // Export the report to a PDF file
      String dest_file_name = "./src/main/java/reports/SavingsInvestmentsReport.pdf";
      JasperExportManager.exportReportToPdfFile(print, dest_file_name);
      System.out.println
        ("Savings and investments report written to reports directory.");
    }
    catch (Exception e)
    { e.printStackTrace();
    }
    finally
    { // Close the database connection
      if (conn != null)
      { try
        { conn.close();
        }
        catch (SQLException ignore)
        {
        }
      }
    }
  }
  
  public void get_income_expense_report()
  { Connection conn = null;
    String sql = "";
    try
    { conn = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement stmt = conn.createStatement();
      sql = "SELECT t.Date, t.Description, t.Amount, t.Type, t.Account_Name " +
        "FROM transactions_table t " +
        "JOIN view_accounts_table v " +
        "ON t.Account_Name = v.Account_Name " +
        "WHERE v.Type IN ('Checking', 'Credit') " +
        "ORDER BY t.Account_Name, t.Date";
      ResultSet rs = stmt.executeQuery(sql);
      // Convert ResultSet to JRResultSetDataSource
      JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
      // Specify the location of the JasperReports template file
      String reportJrxml = "./src/main/java/reports/income_expenses_report.jrxml";
      // Compile the JasperReports template
      JasperReport jasperReport = JasperCompileManager.compileReport(reportJrxml);
      // Create a map to hold any parameters required by the report
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("ReportTitle", "Debt Report");
      params.put("ReportDate", new java.util.Date());
      params.put("IncludeDetail", Boolean.TRUE);
      // Fill the report with data
      JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrRS);
      // Specify the output directory for the PDF report
      File outDir = new File("./src/main/java/reports/");
      outDir.mkdirs();
      // Export the report to a PDF file
      String file_dest_name = "./src/main/java/reports/IncomeExpenseReport.pdf";
      JasperExportManager.exportReportToPdfFile(print, file_dest_name);
      System.out.println("Income and expenses report written to reports directory.");
    }
    catch (Exception e)
    { e.printStackTrace();
    }
    finally
    { // Close the database connection
      if (conn != null)
      { try
        { conn.close();
        }
        catch (SQLException ignore)
        {
        }
      }
    }
  }
  public void disassemble()
  { frame.remove(label_spacer);
    frame.remove(gen_rep_button);
    frame.remove(combo_box);
  }
}
