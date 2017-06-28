package model;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.sun.rowset.internal.InsertRow;


public class ProductOrderModel implements TableModel{
	
	public CachedRowSet OrderRowSet;
	
	ResultSetMetaData metaData;
	
	int numcols,numrows;
	
	public CachedRowSet getCoffeesRowSet(){
		return OrderRowSet;
	}

	
	public ProductOrderModel(CachedRowSet myCachedRowSet) throws SQLException {
		this.OrderRowSet = myCachedRowSet;
		this.metaData = this.OrderRowSet.getMetaData();
		numcols = metaData.getColumnCount();
		
		this.OrderRowSet.beforeFirst();
		this.numrows =0;
		while(this.OrderRowSet.next()){
			this.numrows++;
		}
		this.OrderRowSet.beforeFirst();
	}
	
	public void addEventHandlersToRowSet(RowSetListener listener){
		this.OrderRowSet.addRowSetListener(listener);
	}
	public void InsertRow(int orderNo, int bookNo, java.sql.Date dateOfOrder, java.sql.Date l ) throws SQLException{
		try {
			this.OrderRowSet.moveToInsertRow();
			this.OrderRowSet.updateInt("orderNo", orderNo);
			this.OrderRowSet.updateInt("bookNo", bookNo);
			this.OrderRowSet.updateDate("dateOrderMade", dateOfOrder);
			this.OrderRowSet.updateDate("datePaid", l);
			
			this.OrderRowSet.insertRow();
			this.OrderRowSet.moveToCurrentRow();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("SQL Exception: " + e.getMessage());
		}
	}
	
	
	public void close(){
		try {
			OrderRowSet.getStatement().close();
		} catch (SQLException e) {
			// TODO: handle exception
			printSQLException(e);
		}
	}
	
	private void printSQLException(SQLException ex) {
		// TODO Auto-generated method stub
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

	private boolean ignoreSQLException(String sqlState) {
		// TODO Auto-generated method stub
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
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}
}
