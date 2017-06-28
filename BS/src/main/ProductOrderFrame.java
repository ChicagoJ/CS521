package main;

import java.awt.Button;
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
import model.ProductOrderModel;

public class ProductOrderFrame extends JFrame implements RowSetListener{

	Connection connection;
	JTable table;
	
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
	
	ProductOrderModel productOrderModel;
	
	public ProductOrderFrame() throws SQLException{
		// TODO Auto-generated constructor stub
		connection = MakeConnection.getConnection("BS");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.out.println("Window is closed..");
				
				try {
					connection.close();
				} catch (SQLException e2) {
					// TODO: handle exception
					printSQLException(e2);
				}
				System.exit(0);
			}
		});
	
	

	CachedRowSet myCachedRowSet = getContentsOfOrderTable();
	productOrderModel = new ProductOrderModel(myCachedRowSet);
	productOrderModel.addEventHandlersToRowSet(this);
	
	
	table = new JTable();
	table.setModel(productOrderModel);
	
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
	
	labelOrderNumber.setText("OrderNumber:");
	labelBookNumber.setText("BookNumber:");
	labelDateOfOrder.setText("DateOfOrder:");
	labelDatePaid.setText("DatePaid:");
	
	}
	
	
	
	
	
	
	private void createNewTableModel() throws SQLException {
		productOrderModel = new ProductOrderModel(getContentsOfOrderTable());
		productOrderModel.addEventHandlersToRowSet(this);
		table.setModel(productOrderModel);
	}

	private void displaySQLExceptionDialog(SQLException e) {

		// Display the SQLException in a dialog box
		JOptionPane.showMessageDialog(ProductOrderFrame.this,
				new String[] { e.getClass().getName() + ": ", e.getMessage() });
	}

	private CachedRowSet getContentsOfOrderTable() {

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

			crs.setUrl("jdbc:mysql://localhost:3306/OC?relaxAutoCommit=true");

			// Regardless of the query, fetch the contents of COFFEES

			crs.setCommand("select reservationNo, cruiseNo, dateReservationMade, datePaid from reservation");
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
	
	@Override
	public void rowSetChanged(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rowChanged(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cursorMoved(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

}
