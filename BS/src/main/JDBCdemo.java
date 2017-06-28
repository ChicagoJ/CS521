package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbconnection.MakeConnection;
import model.*;
import main.MainClass;


public class JDBCdemo {
	
	public static void main(String args[]) {
		Connection conn =null;
		conn = MakeConnection.getDafaultConnection();
		if (conn != null){
			 JDBCdemo jdbCdemo = new JDBCdemo();
			  conn = MakeConnection.getConnection("BS");
			  
			  //create new home phone number 
			  jdbCdemo.insertIntophoneNumberTable(conn);
			  //read book table where the bookType = hard copy
			  jdbCdemo.readFromBookTable(conn);
			  //update customer zip code
			  jdbCdemo.updateIntoCustomerTable(conn);
			  //delete membership of memberId 1
			  jdbCdemo.deleteValuesFromMemberShipTable(conn);
			  
			  
		}else {
			System.out.println("Driver is not connected");
		}
		
	}
	//delete
	
	public void deleteValuesFromTable(Connection conn) {
		this.deleteValuesFromMemberShipTable(conn);
		
	}
	
	private void deleteValuesFromMemberShipTable(Connection conn) {
		
		PreparedStatement pst = null;
		String sql = "delete from memberShip where memberId = 1;";
		
		try {
			pst = conn.prepareStatement(sql);
			
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//create or insert
	private List<Phone> getPhoneNumberObjects() {
		List<Phone> phones = new ArrayList<Phone>();

		Phone phone1 = new Phone();
		phone1.setCustomerNo(10000082L);
		phone1.setPhoneNumber("5432");
		phone1.setType("Home");

		Phone phone2 = new Phone();
		phone2.setCustomerNo(10000083L);
		phone2.setPhoneNumber("6543");
		phone2.setType("Home");

		Phone phone3 = new Phone();
		phone3.setCustomerNo(10000084L);
		phone3.setPhoneNumber("7654");
		phone3.setType("Home");

		phones.add(phone1);
		phones.add(phone2);
		phones.add(phone3);

		return phones;

	}
	
	
	public void insertValusesIntoTable(Connection conn) {
		this.insertIntophoneNumberTable(conn);
	}
	
	private void insertIntophoneNumberTable(Connection conn) {
		PreparedStatement pst = null;
		String sql = "insert into phoneNumber (phoneNumber,customerNo,type) VALUES (?,?,?);";
		
		try {
			for(Phone phone : this.getPhoneNumberObjects()){
				pst = conn.prepareStatement(sql);
				pst.setString(1, phone.getPhoneNumber());
				pst.setLong(2, phone.getCustomerNo());
				pst.setString(3, phone.getType());

				pst.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
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
			e.printStackTrace();
		}
	}
	
	// read
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
