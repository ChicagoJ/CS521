package main;


import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sun.rowset.*;

import dbconnection.MakeConnection;
import main.CustomerOrderFrame;
import model.ProductOrderModel;
import model.ReservationModel;

public class CustomerOrderFrame extends JFrame implements RowSetListener {


	Connection connection;
	JTable table; // The table for displaying data

	JLabel labelOrderNumber;
	JLabel labelBookNumber;
	JLabel labelDateOfOrder;
	JLabel labelDatePaid;

	JTextField textFiledOrderNumber;
	JTextField textFieldBookNumber;
	JTextField textFieldDateOfOrder;
	JTextField textFieldDatePaid;

	JButton button_ADD_ROW;
	JButton button_UPDATE_DATABASE;
	JButton button_DISCARD_CHANGES;


	ReservationModel reservationModel;

	public CustomerOrderFrame() throws SQLException {

		// get the connection
		connection = MakeConnection.getConnection("BS");

		// event listen when window is closed
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Window is closed..");
				try {
					connection.close();
				} catch (SQLException ex) {
					printSQLException(ex);
				}
				System.exit(0);
			}
		});

		// create the frame components
		CachedRowSet myCachedRowSet = getContentsOfReservationTable();
		reservationModel = new ReservationModel(myCachedRowSet);
		reservationModel.addEventHandlersToRowSet(this);

		table = new JTable(); // Displays the table
		table.setModel(reservationModel);

		labelOrderNumber = new JLabel();
		labelBookNumber = new JLabel();
		labelDateOfOrder = new JLabel();
		labelDatePaid = new JLabel();
		
		textFiledOrderNumber = new JTextField(10);
		textFieldBookNumber = new JTextField(10);
		textFieldDateOfOrder = new JTextField(10);
		textFieldDatePaid = new JTextField(10);

		button_ADD_ROW = new JButton();
		button_UPDATE_DATABASE = new JButton();
		button_DISCARD_CHANGES = new JButton();

		labelOrderNumber.setText("orderNumber:");
		labelBookNumber.setText("customerNumber:");
		labelDateOfOrder.setText("DateOfOrder:");
		labelDatePaid.setText("DatePaid:");
		
		textFiledOrderNumber.setText("Enter Order Number here");
		textFieldBookNumber.setText("enter Customer number here");
		textFieldDateOfOrder.setText("Do not type here");
		textFieldDatePaid.setText("Do not type here");

		button_ADD_ROW.setText("Add row to table");
		button_UPDATE_DATABASE.setText("Update");
		button_DISCARD_CHANGES.setText("Show table");

		Container contentPane = getContentPane();
		contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		contentPane.add(new JScrollPane(table), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(labelOrderNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		contentPane.add(textFiledOrderNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.25;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(labelBookNumber, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.75;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		contentPane.add(textFieldBookNumber, c);

//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_START;
//		c.weightx = 0.25;
//		c.weighty = 0;
//		c.gridx = 0;
//		c.gridy = 3;
//		c.gridwidth = 1;
//		contentPane.add(labelDateOfOrder, c);
//
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_END;
//		c.weightx = 0.75;
//		c.weighty = 0;
//		c.gridx = 1;
//		c.gridy = 3;
//		c.gridwidth = 1;
//		contentPane.add(textFieldDateOfOrder, c);

//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_START;
//		c.weightx = 0.25;
//		c.weighty = 0;
//		c.gridx = 0;
//		c.gridy = 4;
//		c.gridwidth = 1;
//		contentPane.add(labelDatePaid, c);
//
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_END;
//		c.weightx = 0.75;
//		c.weighty = 0;
//		c.gridx = 1;
//		c.gridy = 4;
//		c.gridwidth = 1;
//		contentPane.add(textFieldDatePaid, c);

//
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.LINE_START;
//		c.weightx = 0.5;
//		c.weighty = 0;
//		c.gridx = 0;
//		c.gridy = 6;
//		c.gridwidth = 1;
//		contentPane.add(button_ADD_ROW, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_UPDATE_DATABASE, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		contentPane.add(button_DISCARD_CHANGES, c);

		button_ADD_ROW.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(CustomerOrderFrame.this,
						new String[] { "Check the query",
							textFiledOrderNumber.getText() ,
								});
//				System.out.println("ooxx");

				try {
//					
//					reservationModel.insertRow(textFiledOrderNumber.getText().trim(),
//							Integer.parseInt(textFieldBookNumber.getText().trim()),
//							new Date(Date.parse(textFieldDateOfOrder.getText().trim())),
//							new Date(Date.parse(textFieldDatePaid.getText().trim())));
					updateTableModel("select * from productOrder where bookNo = " + textFieldBookNumber.getText().trim());
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}

		});

		button_UPDATE_DATABASE.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(CustomerOrderFrame.this,
						new String[] { "Command Submitted",
								});
//				System.out.println("ooxx");

				try {
//					
//					reservationModel.insertRow(textFiledOrderNumber.getText().trim(),
//							Integer.parseInt(textFieldBookNumber.getText().trim()),
//							new Date(Date.parse(textFieldDateOfOrder.getText().trim())),
//							new Date(Date.parse(textFieldDatePaid.getText().trim())));
					String sql = "update customerOrder set customerNo = " + textFieldBookNumber.getText().trim() +" where orderNo = " + textFiledOrderNumber.getText().trim() + ";";
					updateTableModel(sql);
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}


		});

		button_DISCARD_CHANGES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createNewTableModel();
				} catch (SQLException sqle) {
					displaySQLExceptionDialog(sqle);
				}
			}
		});

	}

	private void createNewTableModel() throws SQLException {
		reservationModel = new ReservationModel(getContentsOfReservationTable());
		reservationModel.addEventHandlersToRowSet(this);
		table.setModel(reservationModel);
	}

	private void updateTableModel(String sql) throws SQLException{
		reservationModel = new ReservationModel(getContentsOfReservationTable1(sql));
		reservationModel.addEventHandlersToRowSet(this);
		table.setModel(reservationModel);
	}
	
	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(CustomerOrderFrame.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}

	private CachedRowSet getContentsOfReservationTable() {

		// write code to fetch the records from reservation table
		// setting for scroll option

		CachedRowSet crs = null;
		try {
			connection = MakeConnection.getConnection("BS");
			crs = new CachedRowSetImpl();
			crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
			crs.setUsername("root");
			crs.setPassword("nyit");

			// In MySQL, to disable auto-commit, set the property
			// relaxAutoCommit to
			// true in the connection URL.

			crs.setUrl("jdbc:mysql://localhost:3306/BS?relaxAutoCommit=true");

			// Regardless of the query, fetch the contents of COFFEES

			crs.setCommand("select * from customerOrder");
			crs.execute();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return crs;

	}
	
	private CachedRowSet getContentsOfReservationTable1( String sql) {

		// write code to fetch the records from reservation table
		// setting for scroll option

		CachedRowSet crs = null;
		try {
			connection = MakeConnection.getConnection("BS");
			crs = new CachedRowSetImpl();
			crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
			crs.setUsername("root");
			crs.setPassword("nyit");

			// In MySQL, to disable auto-commit, set the property
			// relaxAutoCommit to
			// true in the connection URL.

			crs.setUrl("jdbc:mysql://localhost:3306/BS?relaxAutoCommit=true");

			// Regardless of the query, fetch the contents of COFFEES

			crs.setCommand(sql);
			crs.execute();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return crs;

	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {
					e.printStackTrace(System.err);
					System.err.println("SQLState: " + ((SQLException) e).getSQLState());
					System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
					System.err.println("Message: " + e.getMessage());
					Throwable t = ex.getCause();
					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}

	private static boolean ignoreSQLException(String sqlState) {
		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}
		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;
		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;
		return false;
	}

//	private void updateCustomerReservationTable(int customerId, int reservationId) {
//
//		String insertIntoCustomerReservationQuery = "INSERT INTO customerReservation VALUES " + "(?, ?);";
//		try {
//			PreparedStatement pst = connection.prepareStatement(insertIntoCustomerReservationQuery);
//			pst.setInt(1, customerId);
//			pst.setInt(2, reservationId);
//			pst.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}

	public static void main(String args[]) {
		try {
			CustomerOrderFrame reservationFrame = new CustomerOrderFrame();
			reservationFrame.setTitle("CustomerOrder");
			reservationFrame.setSize(600, 600);
			reservationFrame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void rowSetChanged(RowSetEvent event) {

	}

	public void rowChanged(RowSetEvent event) {

		CachedRowSet currentRowSet = this.reservationModel.reservationRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			reservationModel = new ReservationModel(reservationModel.getCoffeesRowSet());
			table.setModel(reservationModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(CustomerOrderFrame.this, new String[] { // Display
																				// a
																				// 2-line
																				// message
					ex.getClass().getName() + ": ", ex.getMessage() });
		}
	}

	public void cursorMoved(RowSetEvent event) {

	}

	

}