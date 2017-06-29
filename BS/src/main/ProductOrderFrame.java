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

import org.omg.PortableServer.ServantActivator;

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
	
	textFieldBookNumber.setText("enter book number here");
	textFiledOrderNumber.setText("0");
	textFieldDateOfOrder.setText("12/30/2000");
	textFieldDatePaid.setText("12/30/2000");
	
	button_ADD_ROW.setText("Add row to table");
	button_UPDATE_DATABASE.setText("Update database");
	button_DISCARD_CHANGES.setText("Discard changes");

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

	c.fill = GridBagConstraints.HORIZONTAL;
	c.anchor = GridBagConstraints.LINE_START;
	c.weightx = 0.25;
	c.weighty = 0;
	c.gridx = 0;
	c.gridy = 3;
	c.gridwidth = 1;
	contentPane.add(labelDateOfOrder, c);

	c.fill = GridBagConstraints.HORIZONTAL;
	c.anchor = GridBagConstraints.LINE_END;
	c.weightx = 0.75;
	c.weighty = 0;
	c.gridx = 1;
	c.gridy = 3;
	c.gridwidth = 1;
	contentPane.add(textFieldDateOfOrder, c);

	c.fill = GridBagConstraints.HORIZONTAL;
	c.anchor = GridBagConstraints.LINE_START;
	c.weightx = 0.25;
	c.weighty = 0;
	c.gridx = 0;
	c.gridy = 4;
	c.gridwidth = 1;
	contentPane.add(labelDatePaid, c);

	c.fill = GridBagConstraints.HORIZONTAL;
	c.anchor = GridBagConstraints.LINE_END;
	c.weightx = 0.75;
	c.weighty = 0;
	c.gridx = 1;
	c.gridy = 4;
	c.gridwidth = 1;
	contentPane.add(textFieldDatePaid, c);

	c.fill = GridBagConstraints.HORIZONTAL;
	c.anchor = GridBagConstraints.LINE_START;
	c.weightx = 0.5;
	c.weighty = 0;
	c.gridx = 0;
	c.gridy = 6;
	c.gridwidth = 1;
	contentPane.add(button_ADD_ROW, c);

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
	c.gridy = 7;
	c.gridwidth = 1;
	contentPane.add(button_DISCARD_CHANGES, c);

	button_ADD_ROW.addActionListener(new ActionListener() {

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {

			JOptionPane.showMessageDialog(ProductOrderFrame.this,
					new String[] { "Adding the following row:",
							"Order Number: [" + textFiledOrderNumber.getText() + "]",
							"Book Number: [" + textFieldBookNumber.getText() + "]",
							"DateOfOrder: [" + textFieldDateOfOrder.getText() + "]",
							"DatePaid: [" + textFieldDatePaid.getText() + "]" });


			try {

				productOrderModel.insertRow(textFiledOrderNumber.getText().trim(),
						Integer.parseInt(textFieldBookNumber.getText().trim()),
						new Date(Date.parse(textFieldDateOfOrder.getText().trim())),
						new Date(Date.parse(textFieldDatePaid.getText().trim())));
			} catch (SQLException sqle) {
				displaySQLExceptionDialog(sqle);
			}
		}

	});

	button_UPDATE_DATABASE.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			try {
//				ProductOrderModel.OrderRowSet.acceptChanges();
				productOrderModel.OrderRowSet.acceptChanges();
				// Update customer table as well
				// Assuming customer id is 10000082
				// This section can be improvised to bring dynamic customer
				// id.


			} catch (SQLException sqle) {
				displaySQLExceptionDialog(sqle);
				// Now revert back changes
				try {
					createNewTableModel();
				} catch (SQLException sqle2) {
					displaySQLExceptionDialog(sqle2);
				}
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

			crs.setUrl("jdbc:mysql://localhost:3306/BS?relaxAutoCommit=true");

			// Regardless of the query, fetch the contents of COFFEES

			crs.setCommand("select orderNo, bookNo, dateReservationMade, datePaid from productOrder");
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
	

	public static void main(String args[]){
		try {
			ProductOrderFrame productOrderFrame = new ProductOrderFrame();
			productOrderFrame.setTitle("Product Order");
			productOrderFrame.setSize(600, 600);
			productOrderFrame.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	
	
	
	
	
	@Override
	public void rowSetChanged(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rowChanged(RowSetEvent event) {
		// TODO Auto-generated method stub
		CachedRowSet currentRowSet = this.productOrderModel.OrderRowSet;

		try {
			currentRowSet.moveToCurrentRow();
			productOrderModel = new ProductOrderModel(productOrderModel.getCoffeesRowSet());
			table.setModel(productOrderModel);

		} catch (SQLException ex) {

			printSQLException(ex);

			// Display the error in a dialog box.

			JOptionPane.showMessageDialog(ProductOrderFrame.this, new String[] { // Display
																				// a
																				// 2-line
																				// message
					ex.getClass().getName() + ": ", ex.getMessage() });
		}

		
	}

	@Override
	public void cursorMoved(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

}
