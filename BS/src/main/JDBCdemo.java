package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbconnection.MakeConnection;
import model.*;


public class JDBCdemo {
	
	public static void main(String args[]) {
		Connection conn =null;
		conn = MakeConnection.getDafaultConnection();
		if (conn != null){
			 JDBCdemo jdbCdemo = new JDBCdemo();
			  conn = MakeConnection.getConnection("BS");
			  
			  
			  
			  //read book table where the bookType = hard copy
			  jdbCdemo.readFromBookTable(conn);
			  //update customer zip code
			  jdbCdemo.updateIntoCustomerTable(conn);
			  //
			  
			  
		}else {
			System.out.println("Driver is not connected");
		}
		
	}
	//update
	public void updateValuesToTable(Connection conn){
		this.updateIntoCustomerTable(conn);
	}
	
	
	private void updateIntoCustomerTable(Connection conn) {
		PreparedStatement pst = null;
		
		String sql = "update customer set zip = 60616 where customerNo = 10000084;";
		try {
			pst = conn.prepareStatement(sql);
			
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// query or read
	public void  readValuesToTable(Connection conn) {
		this.readFromBookTable(conn);
	}
	
	
	private void readFromBookTable(Connection conn) {

		PreparedStatement pst = null;
		String sql = "SELECT * FROM book where bookType = \"Hard Copy\"";
		
		System.out.println("bookNo\t\tbookName\tbookType\tbokkCategory");
		
		try {
		
			pst = conn.prepareStatement(sql);
			ResultSet pResultSet = pst.executeQuery();
			
			while (pResultSet.next()){
				long bNo = pResultSet.getLong(1);
				String bName = pResultSet.getString(2);
				String bT = pResultSet.getString(3);
				String bC = pResultSet.getString(4);
				
				System.out.println(bNo + "\t\t" + bName + "\t\t" + bT + "\t" + bC);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		
	}

}
