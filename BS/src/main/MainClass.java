package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbconnection.MakeConnection;
import model.*;




public class MainClass {

	public static void main(String[] args) {
		
		Connection conn = null;
		conn = MakeConnection.getDafaultConnection();
		if (conn != null) {

			MainClass mainClass = new MainClass();

			// create database and tablespace

			mainClass.createTablespace(conn);

			conn = MakeConnection.getConnection("BS");
			
			mainClass.createTables(conn);
			
			mainClass.createUsers(conn);

			// create users

			// grant permissions

			mainClass.grantPermission(conn);


		}else {
			System.out.println("Driver is not connected");
		}
		
		
	}
	
	public void createTablespace(Connection conn) {

		// query
		String createDatabaseQuery = "CREATE DATABASE BS";
		String useDB = "USE BS";
		String createTablespaceCommand = "CREATE TABLESPACE OC ADD DATAFILE 'BS.ibd';";
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(createDatabaseQuery);
			statement.executeUpdate(useDB);
			// uncomment below line during first execution
			// statement.executeUpdate(createTablespaceCommand);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void createTables(Connection conn){
		Statement statement = null;
		try{
			statement = conn.createStatement();
			String createTableBookQuery = "CREATE TABLE book (bookNo INT(6) NOT NULL, bookName VARCHAR(40) NOT NULL, bookType VARCHAR(10) NOT NULL, bookCategory VARCHAR(10) NOT NULL, "
					+ "CONSTRAINT bookPK PRIMARY KEY (bookNo))";
			
			statement.executeUpdate(createTableBookQuery);



			String createProductOrderQuery = "CREATE TABLE productOrder (orderNo INT(12) NOT NULL, "
					+ "bookNo INT(6) NOT NULL, dateReservationMade DATE, datePaid DATE,"
					+ "CONSTRAINT orderPK PRIMARY KEY (orderNo), "
					+ "CONSTRAINT orderbookFK FOREIGN KEY (bookNo) " + "REFERENCES book (bookNo));";

			statement.executeUpdate(createProductOrderQuery);


			String createCustomerTableQuery = "CREATE TABLE customer (customerNo INT(12) NOT NULL,"
					+ "firstName VARCHAR(30) NOT NULL, lastName VARCHAR(40) NOT NULL, email VARCHAR(40), "
					+ "street VARCHAR(40), city VARCHAR(40), state CHAR(2), zip CHAR(5), creditCardNo VARCHAR(16),"
					+ "CONSTRAINT customerPK PRIMARY KEY (customerNo));";

			statement.executeUpdate(createCustomerTableQuery);

			String createTablePhoneNumberQuery = "CREATE TABLE phoneNumber (phoneNumber VARCHAR(10) NOT NULL, "
					+ "customerNo INT(12) NOT NULL, type VARCHAR(12) NOT NULL, "
					+ "CONSTRAINT phonePK PRIMARY KEY (phoneNumber), "
					+ "CONSTRAINT phoneCustomerFK FOREIGN KEY (customerNo) REFERENCES customer (customerNo),"
					+ "CHECK (type IN ('Mobile' , 'Home', 'Work', 'Other')));";

			statement.executeUpdate(createTablePhoneNumberQuery);

			String createCustomerOrderTableQuery = "CREATE TABLE customerOrder (customerNo INT(12) NOT NULL,"
					+ "orderNo INT(12) NOT NULL, "
					+ "CONSTRAINT customerResPK PRIMARY KEY (customerNo , orderNo), "
					+ "CONSTRAINT customerFK FOREIGN KEY (customerNo) REFERENCES customer (customerNo),"
					+ "CONSTRAINT orderFK FOREIGN KEY (orderNo) REFERENCES productOrder (orderNo));";

			statement.executeUpdate(createCustomerOrderTableQuery);

			

		
		} catch (Exception e){
			e.printStackTrace(); 
		}
	}
	
	public void createUsers(Connection conn) {
		// users query

		Statement statement = null;

		try {
			statement = conn.createStatement();

			String createDbAdminUserQuery = "CREATE USER 'Joey'@'localhost' IDENTIFIED BY 'root';";

			String grantDbAdminQuery = "GRANT ALL PRIVILEGES ON * . * TO 'Joey'@'localhost'";

			statement.executeUpdate(createDbAdminUserQuery);
			statement.executeUpdate(grantDbAdminQuery);

			// Creating manager users
			statement.executeUpdate(this.formCreateUserQuery("Tom", "Tom"));
			statement.executeUpdate(this.formCreateUserQuery("Joe", "Joe"));

			// creating customer support
			statement.executeUpdate(this.formCreateUserQuery("Kevin", "Kevin"));
			statement.executeUpdate(this.formCreateUserQuery("Victoria", "Victoria"));


			// create courier user
//			statement.executeUpdate(this.formCreateUserQuery("Sue", "Sue"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// statements to execute
	}
	
	
	public void grantPermission(Connection conn) {
		// grants queries

		Statement statement = null;
		try {

			statement = conn.createStatement();
			// grant manager

			statement.executeUpdate("GRANT CREATE ON TABLE BS.* TO 'Tom'@'localhost'");
			statement.executeUpdate("GRANT DELETE ON TABLE BS.* TO 'Tom'@'localhost'");
			statement.executeUpdate("GRANT DROP ON TABLE BS.* TO 'Tom'@'localhost'");

			statement.executeUpdate("GRANT CREATE ON TABLE BS.* TO 'Joe'@'localhost'");
			statement.executeUpdate("GRANT DELETE ON TABLE BS.* TO 'Joe'@'localhost'");
			statement.executeUpdate("GRANT DROP ON TABLE BS.* TO 'Joe'@'localhost'");

			// grant customer support

			List<String> SupportTableList = new ArrayList<String>();
			SupportTableList.add("productOrder");
			SupportTableList.add("customer");

			List<String> SupportUsersList = new ArrayList<String>();
			SupportUsersList.add("Kevin");
			SupportUsersList.add("Victoria");

			List<String> operationList = new ArrayList<String>();
			operationList.add("INSERT");
			operationList.add("DELETE");
			operationList.add("UPDATE");

			this.grantPermission(conn, SupportTableList, SupportUsersList, operationList);



		} catch (Exception e) {
			e.printStackTrace();
		}
		// satements
	}

	
	
	
	
	String formGrantPermissionToDbDesigneQuery(String userName) {
		return "GRANT CREATE ON TABLE OC.* TO '" + userName + "'@'localhost';" + "GRANT ALTER ON TABLE OC.* TO '"
				+ userName + "'@'localhost;" + "GRANT DROP ON TABLE OC.* TO '" + userName + "'@'localhost;";
	}

	String formCreateUserQuery(String userName, String password) {
		return "CREATE USER '" + userName + "'@'localhost' IDENTIFIED BY '" + password + "'";
	}

	String formGrantInsertPermissionQuery(String tableName, String user) {
		return "GRANT INSERT ON " + tableName + " TO '" + user + "'@'localhost'";
	}

	String formGrantUpdatePermissionQuery(String tableName, String user) {
		return "GRANT UPDATE ON " + tableName + " TO '" + user + "'@'localhost'";
	}

	String formGrantDeletePermissionQuery(String tableName, String user) {
		return "GRANT DELETE ON " + tableName + " TO '" + user + "'@'localhost'";
	}

	String formGrantSelectPermissionQuery(String tableName, String user) {
		return "GRANT SELECT ON " + tableName + " TO '" + user + "'@'localhost'";
	}
	
	void grantPermission(Connection conn, List<String> tables, List<String> users, List<String> operations) {

		for (String user : users) {
			for (String table : tables) {
				for (String operation : operations)
					try {
						System.out.println("user" + user + " table :" + table + " operation :" + operation);
						Statement statement = conn.createStatement();
						statement.executeUpdate(
								"GRANT " + operation + " ON " + table + " TO '" + user + "'@'localhost'");
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}

}
