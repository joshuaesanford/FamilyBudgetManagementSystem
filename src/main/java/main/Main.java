package main;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import checkingcreditaccounts.CheckingCreditAccounts;
import debtmanagment.DebtManagement;
import reports.Report;
import savingsinvestmentaccounts.SavingsInvestmentAccounts;
import transactions.TransactionsTable;
import viewaccounts.ViewAccounts;

public class Main 
{ static public JFrame frame;
  static public JLabel label_view_accounts;
  static public JLabel label_transactions;
  static public JLabel label_generate_reports;
  static public GridBagConstraints gbc;
  static public TransactionsTable transactions_table;
  
  static final public Font labelFont = new Font("Courier New", Font.BOLD, 30);
  static final public Font titleFont = new Font("Courier New", Font.ITALIC, 40);
  static final public Font backLinkFont = new Font("Courier New", Font.BOLD, 24);
	private static ViewAccounts view_accounts_table;
	private static DebtManagement debt_management_table;
	private static CheckingCreditAccounts checking_credit_accounts_table;
	private static SavingsInvestmentAccounts savings_investment_accounts_table;
	private static Report report;
	
	public static enum State
  { // Indentation represents path.
	  	HOMEPAGE,
			    VIEW_ACCOUNTS,
				  		DEBT_MANAGEMENT,
				  		CHECKING_CREDIT_ACCOUNTS,
				  		SAVINGS_INVESTMENT_ACCOUNTS,
			  	TRANSACTIONS,
			  	GENERATE_REPORTS,
			  	EXITING
  }
  
  public static enum Label
  { TITLE,
  	LABEL,
  	BACKLINK,
  	SPACER
  }
  public static State current_state;
	
  private static JLabel label_debt_management;
	private static JLabel label_checking_credit_accounts;
	private static JLabel label_savings_investment_accounts;
	private static JLabel label_title;
	private static JLabel label_back_to_view_accounts;
	private static JLabel label_back_to_homepage;
	protected static JLabel label_spacer;
	
	public static MouseAdapter click_back_to_homepage()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.HOMEPAGE;
			  build_current_state();
			}
		};
		return output_click_event;
	}
	
	public static MouseAdapter click_back_to_view_accounts()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.VIEW_ACCOUNTS;
			  build_current_state();
			}
		};
		return output_click_event;
	}
	
	public static MouseAdapter click_checking_credit_accounts()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.CHECKING_CREDIT_ACCOUNTS;
			  build_current_state();
			}
		};
		return output_click_event;
	}
	
	public static MouseAdapter click_debt_management()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.DEBT_MANAGEMENT;
			  build_current_state();
			}
		};
		return output_click_event;
	}
	
	public static MouseAdapter click_generate_reports()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.GENERATE_REPORTS;
			  build_current_state();
			}
		};
		return output_click_event;
	}
	
	public static MouseAdapter click_savings_investment_accounts()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.SAVINGS_INVESTMENT_ACCOUNTS;
			  build_current_state();
			}
		};
		return output_click_event;
	}
	
	public static MouseAdapter click_transactions()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.TRANSACTIONS;
			  build_current_state();
			}
		};
		return output_click_event;
	}
	
	public static MouseAdapter click_view_accounts()
	{ MouseAdapter output_click_event = new MouseAdapter()
		{ @Override
			public void mouseClicked(MouseEvent e)
			{ disassemble_current_state();
			  current_state = State.VIEW_ACCOUNTS;
			  build_current_state();
			}
		};
		return output_click_event;
	}

  public static void main(String[] args) 
  { current_state = State.HOMEPAGE;
    generate_window();
    build_current_state();
  } // end of main()
  
  public static void build_current_state()
  { switch (current_state)
	  { case HOMEPAGE: 
	    { frame.setTitle("FAMILY BUDGET MANAGEMENT SYSTEM");
	    	// Generate Title Label
	    	String s = "<HTML>Family Budget Management System</HTML>";
	  	  label_title = build_label(s, Label.TITLE, null);
	  	  // Generate spacer label
	  	  label_spacer = build_label("", Label.SPACER, null);
	  	  // Generate View Accounts, Transactions, and Generate Reports labels
	  	  s = "<HTML>View Accounts</HTML>";
	      label_view_accounts = build_label(s, Label.LABEL, click_view_accounts());
	      s = "<HTML>Transactions</HTML>";
	      label_transactions = build_label(s, Label.LABEL, click_transactions());
	      s = "<HTML>Generate Reports</HTML>";
	      label_generate_reports = build_label(s, Label.LABEL, click_generate_reports());
	      
	      gbc.gridx = 0;
	      gbc.gridy = 0;
	      frame.add(label_title, gbc);
	  		gbc.gridy = 1;
	  		frame.add(label_spacer, gbc);
	      gbc.gridy = 2;
	      frame.add(label_view_accounts, gbc);
	      gbc.gridy = 3;
	      frame.add(label_transactions, gbc);
	      gbc.gridy = 4;
	      frame.add(label_generate_reports, gbc);
	      frame.revalidate();
	      frame.repaint();
	      break;
	    }
	    case VIEW_ACCOUNTS:
	    { // Set window title
	    	frame.setTitle("FAMILY BUDGET MANAGEMENT SYSTEM => VIEW ACCOUNTS");
				view_accounts_table = new ViewAccounts();
    	  String s = "<HTML>View Accounts</HTML>";
  	    label_title = build_label(s, Label.TITLE, null);
	  	  label_spacer = build_label("", Label.SPACER, null);
	  	  s = "<HTML>Debt Management</HTML>";
	      label_debt_management = build_label(s, Label.LABEL, click_debt_management());
	      s = "<HTML>Checking/Credit Accounts</HTML>";
	      label_checking_credit_accounts = build_label(s, Label.LABEL, click_checking_credit_accounts());
	      s = "<HTML>Savings & Investment Accounts</HTML>";
	      label_savings_investment_accounts = build_label(s, Label.LABEL, click_savings_investment_accounts());
	      // Generate `Back to Home` label
	      s = "<HTML>Back to Homepage</HTML>";
	      label_back_to_homepage = build_label(s, Label.BACKLINK, click_back_to_homepage());
	      // Traverse grid bag coordinates and add labels.
	      gbc.gridx = 0;
	      gbc.gridy = 0;
	      frame.add(label_title, gbc);
	  		gbc.gridy = 1;
	  		frame.add(label_spacer, gbc);
	      gbc.gridy = 4;
	      frame.add(label_debt_management, gbc);
	      gbc.gridy = 5;
	      frame.add(label_checking_credit_accounts, gbc);
	      gbc.gridy = 6;
	      frame.add(label_savings_investment_accounts, gbc);
	      gbc.gridy = 7;
	      frame.add(label_spacer, gbc);
	      gbc.gridy = 8;
	      frame.add(label_back_to_homepage, gbc);
	      // MUST revalidate and repaint.
	      frame.revalidate();
	      frame.repaint();
	      break;
	    }
	    case DEBT_MANAGEMENT:
	    { // Set window title
	    	frame.setTitle("FAMILY BUDGET MANAGEMENT SYSTEM => VIEW ACCOUNTS => DEBT MANAGEMENT");
				debt_management_table = new DebtManagement();
				String s = "<HTML>Debt Management</HTML>";
				label_title = build_label(s, Label.TITLE, null);
				label_spacer = build_label("", Label.SPACER, null);
				s = "<HTML>Back to View Accounts</HTML>";
				label_back_to_view_accounts = build_label(
					s, Label.BACKLINK, click_back_to_view_accounts());
				// Generate `Back to Home` label
				s = "<HTML>Back to Homepage</HTML>";
				label_back_to_homepage = build_label(s, Label.BACKLINK, click_back_to_homepage());
				// Traverse grid bag coordinates and add labels.
				gbc.gridx = 0;
				gbc.gridy = 0;
				frame.add(label_title, gbc);
				gbc.gridy = 1;
				frame.add(label_spacer, gbc);
				gbc.gridy = 3;
				frame.add(label_spacer, gbc);
				gbc.gridy = 4;
				frame.add(label_back_to_view_accounts, gbc);
				gbc.gridy = 5;
				frame.add(label_back_to_homepage, gbc);
				// MUST revalidate and repaint.
				frame.revalidate();
				frame.repaint();
				break;
	    }
	    case CHECKING_CREDIT_ACCOUNTS:
	    { // Set window title
				frame.setTitle("FAMILY BUDGET MANAGEMENT SYSTEM => VIEW ACCOUNTS => CHECKING/CREDIT ACCOUNTS");
				checking_credit_accounts_table = new CheckingCreditAccounts();
				String s = "<HTML>Checking/Credit Accounts</HTML>";
				label_title = build_label(s, Label.TITLE, null);
				label_spacer = build_label("", Label.SPACER, null);
				s = "<HTML>Back to View Accounts</HTML>";
				label_back_to_view_accounts = build_label(
					s, Label.BACKLINK, click_back_to_view_accounts());
				// Generate `Back to Home` label
				s = "<HTML>Back to Homepage</HTML>";
				label_back_to_homepage = build_label(s, Label.BACKLINK, click_back_to_homepage());
				// Traverse grid bag coordinates and add labels.
				gbc.gridx = 0;
				gbc.gridy = 0;
				frame.add(label_title, gbc);
				gbc.gridy = 1;
				frame.add(label_spacer, gbc);
				gbc.gridy = 3;
				frame.add(label_spacer, gbc);
				gbc.gridy = 4;
				frame.add(label_back_to_view_accounts, gbc);
				gbc.gridy = 5;
				frame.add(label_back_to_homepage, gbc);
				// MUST revalidate and repaint.
				frame.revalidate();
				frame.repaint();
				break;
	    }
	    case SAVINGS_INVESTMENT_ACCOUNTS:
	    { // Set window title
	    	frame.setTitle("FAMILY BUDGET MANAGEMENT SYSTEM => VIEW ACCOUNTS => SAVINGS & INVESTMENT ACCOUNTS");
				savings_investment_accounts_table = new SavingsInvestmentAccounts();
				String s = "<HTML>Savings & Investment Accounts</HTML>";
				label_title = build_label(s, Label.TITLE, null);
				label_spacer = build_label("", Label.SPACER, null);
				s = "<HTML>Back to View Accounts</HTML>";
				label_back_to_view_accounts = build_label(
					s, Label.BACKLINK, click_back_to_view_accounts());
				// Generate `Back to Home` label
				s = "<HTML>Back to Homepage</HTML>";
				label_back_to_homepage = build_label(s, Label.BACKLINK, click_back_to_homepage());
				// Traverse grid bag coordinates and add labels.
				gbc.gridx = 0;
				gbc.gridy = 0;
				frame.add(label_title, gbc);
				gbc.gridy = 1;
				frame.add(label_spacer, gbc);
				gbc.gridy = 3;
				frame.add(label_spacer, gbc);
				gbc.gridy = 4;
				frame.add(label_back_to_view_accounts, gbc);
				gbc.gridy = 5;
				frame.add(label_back_to_homepage, gbc);
				// MUST revalidate and repaint.
				frame.revalidate();
				frame.repaint();
				break;
	    }
	    case TRANSACTIONS:
	    { // Set window title
	    	frame.setTitle("FAMILY BUDGET MANAGEMENT SYSTEM => TRANSACTIONS");
	    	// Generate transactions table
	    	transactions_table = new TransactionsTable();
	      gbc.gridx = 0;
	      gbc.gridy = 4;
	    	label_spacer = new JLabel();
	    	label_spacer.setPreferredSize(new Dimension(1,15));
	    	frame.add(label_spacer, gbc);
	    	String s = "<HTML>Back to Homepage</HTML>";
	      label_back_to_homepage = build_label(s, Label.BACKLINK, click_back_to_homepage());
	      // Traverse grid bag coordinates and add labels.
	      gbc.gridy = 5;
	      frame.add(label_back_to_homepage, gbc);
	    	// MUST revalidate and repaint.
	      frame.revalidate();
	      frame.repaint();
	    	break;	
	    }
	    case GENERATE_REPORTS:
	    { // Set window title
	    	frame.setTitle("FAMILY BUDGET MANAGEMENT SYSTEM => GENERATE REPORTS");
 			  report = new Report();
	    	String s = "<HTML>Back to Homepage</HTML>";
	      label_back_to_homepage = build_label(s, Label.BACKLINK, click_back_to_homepage());
				s = "<HTML>Reports</HTML>";
				label_title = build_label(s, Label.TITLE, null);
				label_spacer = build_label("", Label.SPACER, null);
	      // Traverse grid bag coordinates and add labels.
	      gbc.gridx = 0;
	      gbc.gridy = 0;
				frame.add(label_title, gbc);
				gbc.gridy = 1;
				frame.add(label_spacer,gbc);
				gbc.gridy = 5;
				frame.add(label_spacer, gbc);
				gbc.gridy = 6;
	      frame.add(label_back_to_homepage, gbc);
	    	// MUST revalidate and repaint.
	      frame.revalidate();
	      frame.repaint();
	    	break;	
	    }
	    case EXITING:
	    {	return;
	    }	
    } 
  }
  
  public static void disassemble_current_state()
  { switch (current_state)
	  { case HOMEPAGE: 
	    { frame.remove(label_title);
	    	frame.remove(label_view_accounts);
	      frame.remove(label_transactions);
	      frame.remove(label_generate_reports);
	      label_title = null;
	      label_view_accounts = null;
	      label_transactions = null;
	      label_generate_reports = null;
	      break;
	    }
	    case VIEW_ACCOUNTS:
	    { frame.remove(label_title);
	      frame.remove(label_debt_management);
	      frame.remove(label_checking_credit_accounts);
	      frame.remove(label_savings_investment_accounts);
	      frame.remove(label_back_to_homepage);
				frame.remove(label_spacer);
				frame.remove(label_spacer);
				view_accounts_table.disassemble();
				view_accounts_table = null;
	      label_title = null;
	      label_debt_management = null;
	      label_checking_credit_accounts = null;
	      label_savings_investment_accounts = null;
	      label_back_to_homepage = null;
				label_spacer = null;
	    	break;
	    }
	    case DEBT_MANAGEMENT:
	    { frame.remove(label_back_to_homepage);
	      frame.remove(label_back_to_view_accounts);
				frame.remove(label_title);
				frame.remove(label_spacer);
				frame.remove(label_spacer);
				debt_management_table.disassemble();
				debt_management_table = null;
	      label_back_to_homepage = null;
	      label_back_to_view_accounts = null;
				label_spacer = null;
	    	break;	
	    }
	    case CHECKING_CREDIT_ACCOUNTS:
	    { frame.remove(label_back_to_homepage);
				frame.remove(label_back_to_view_accounts);
				frame.remove(label_title);
				frame.remove(label_spacer);
				frame.remove(label_spacer);
				checking_credit_accounts_table.disassemble();
				checking_credit_accounts_table = null;
				label_back_to_homepage = null;
				label_back_to_view_accounts = null;
				label_spacer = null;
				break;
			}
	    case SAVINGS_INVESTMENT_ACCOUNTS:
	    { frame.remove(label_back_to_homepage);
				frame.remove(label_back_to_view_accounts);
				frame.remove(label_title);
				frame.remove(label_spacer);
				frame.remove(label_spacer);
				savings_investment_accounts_table.disassemble();
				savings_investment_accounts_table = null;
				label_back_to_homepage = null;
				label_back_to_view_accounts = null;
				label_spacer = null;
				break;
	    }
	    case TRANSACTIONS:
	    { frame.remove(label_back_to_homepage);
	      frame.remove(label_spacer);
	      transactions_table.disassemble();
	      transactions_table = null;
	      label_back_to_homepage = null;
	    	break;	
	    }
	    case GENERATE_REPORTS:
	    { frame.remove(label_back_to_homepage);
				frame.remove(label_title);
				report.disassemble();
				report = null;
				label_title = null;
        label_back_to_homepage = null;
	    	break;	
	    }
	    case EXITING:
	    {	return;
	    }	
    } 
  }

  public static JLabel build_label(String s, Label type, MouseAdapter listener)
  { JLabel output_label = new JLabel(s);
    switch (type)
    { case LABEL:
    	  output_label.setFont(labelFont);
    	  break;
      case TITLE:
      	output_label.setFont(titleFont);
    	  break;
      case BACKLINK:
      	output_label.setFont(backLinkFont);
    	  break;
      case SPACER:
      	output_label.setPreferredSize(new Dimension(1,50));
      	break;
    }
    output_label.addMouseListener(listener);
    return output_label;
  }
  
  // only called once towards the beginning of main(). sets the window up
  // for manipulation by other methods.
  public static void generate_window()
  { frame = new JFrame();
    frame.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
  }
}