package main;


import java.sql.Connection;
import java.sql.Date;
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
			mainClass.insertValuesToTables(conn);



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
					+ "CONSTRAINT orderPK PRIMARY KEY (orderNo)); ";
//					+ "CONSTRAINT orderbookFK FOREIGN KEY (bookNo) " + "REFERENCES book (bookNo));";

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
			
			String createTableMemberShipQuery = "CREATE table memberShip (memberId INT(12) NOT NULL, customerNo INT(12) NOT NULL, startDate DATE, endDate DATE, constraint membershipPK primary key (memberId)); ";
			statement.executeUpdate(createTableMemberShipQuery);
			
			String createTableCustomerOrderQuery = "CREATE table customerOrder (orderNo INT(12) NOT NULL, customerNo INT (12) NOT NULL, constraint customerOrderPK primary key(orderNo));";
			statement.executeUpdate(createTableCustomerOrderQuery);

		
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

	// Function to create customer objects and return list of those objects
	private List<Customer> getCustomerObjects() {
		List<Customer> customerList = new ArrayList<Customer>();
		Customer customerObject1 = new Customer();

		Address address1 = new Address();
		address1.setStreet("123 Example St");
		address1.setCity("Cleveland");
		address1.setState("OH");
		address1.setZip("75244");

		CreditCard cc1 = new CreditCard();
		cc1.setCreditCardNo("1200456898651234");

		customerObject1.setFirstName("Rob");
		customerObject1.setLastName("McDonald");
		customerObject1.setCustomerNo(10000082);
		customerObject1.setEmail("rob.mcd@gmail.com");
		customerObject1.setAddress(address1);
		customerObject1.setCreditCardNo(cc1);

		Customer customerObject2 = new Customer();

		Address address2 = new Address();
		address2.setStreet("123 Example St");
		address2.setCity("San diago");
		address2.setState("CA");
		address2.setZip("96502");

		CreditCard cc2 = new CreditCard();
		cc1.setCreditCardNo("1200456898654321");

		customerObject2.setFirstName("Mike");
		customerObject2.setLastName("Colins");
		customerObject2.setCustomerNo(10000083);
		customerObject2.setEmail("mike.colins@gmail.com");
		customerObject2.setAddress(address2);
		customerObject2.setCreditCardNo(cc2);

		Customer customerObject3 = new Customer();

		Address address3 = new Address();
		address3.setStreet("123 Example St");
		address3.setCity("Chicago");
		address3.setState("IL");
		address3.setZip("00964");

		CreditCard cc3 = new CreditCard();
		cc1.setCreditCardNo("1500456898651234");

		customerObject3.setFirstName("Pat");
		customerObject3.setLastName("Long");
		customerObject3.setCustomerNo(10000084);
		customerObject3.setEmail("pat.long43@gmail.com");
		customerObject3.setAddress(address3);
		customerObject3.setCreditCardNo(cc3);

		customerList.add(customerObject1);
		customerList.add(customerObject2);
		customerList.add(customerObject3);

		return customerList;

	}
	
	private List<Phone> getPhoneNumberObjects() {
		List<Phone> phones = new ArrayList<Phone>();

		Phone phone1 = new Phone();
		phone1.setCustomerNo(10000082);
		phone1.setPhoneNumber("1234");
		phone1.setType("Mobile");

		Phone phone2 = new Phone();
		phone2.setCustomerNo(10000083);
		phone2.setPhoneNumber("2345");
		phone2.setType("Mobile");

		Phone phone3 = new Phone();
		phone3.setCustomerNo(10000084);
		phone3.setPhoneNumber("5678");
		phone3.setType("Mobile");

		phones.add(phone1);
		phones.add(phone2);
		phones.add(phone3);

		return phones;

	}
	
	private List<Book> getBookObjects(){
		List<Book> bookOjectList = new ArrayList<Book>();
		
		Book bookObject1 = new Book();
		bookObject1.setBookNo(0001);
		bookObject1.setBookName("Java");
		bookObject1.setBookType("Hard Copy");
		bookObject1.setBookCategory("Textbook");
		
		Book bookObject2 = new Book();
		bookObject2.setBookNo(0002);
		bookObject2.setBookName("Nature");
		bookObject2.setBookType("eBook");
		bookObject2.setBookCategory("Magazine");
		
		Book bookObject3 = new Book();
		bookObject3.setBookNo(0003);
		bookObject3.setBookName("Game of Thrones");
		bookObject3.setBookType("eBook");
		bookObject3.setBookCategory("Novel");
		
		bookOjectList.add(bookObject1);
		bookOjectList.add(bookObject2);
		bookOjectList.add(bookObject3);
		
		
		
		return bookOjectList;
	}
	
	private List<Reservation> getProductOrderObjects() {
		List<Reservation> reservationObjectList = new ArrayList<Reservation>();

		Reservation reservationObject1 = new Reservation();
		reservationObject1.setReservationNo(1111);
		reservationObject1.setCruiseNo(1);
		reservationObject1.setDateReservationMade("02/21/2017");
		reservationObject1.setDatePaid("02/21/2017");

		Reservation reservationObject2 = new Reservation();
		reservationObject2.setReservationNo(1112);
		reservationObject2.setCruiseNo(2);
		reservationObject2.setDateReservationMade("04/25/2017");
		reservationObject2.setDatePaid("04/25/2017");

		Reservation reservationObject3 = new Reservation();
		reservationObject3.setReservationNo(1113);
		reservationObject3.setCruiseNo(2);
		reservationObject3.setDateReservationMade("03/16/2017");
		reservationObject3.setDatePaid("03/16/2017");

		Reservation reservationObject4 = new Reservation();
		reservationObject4.setReservationNo(1114);
		reservationObject4.setCruiseNo(3);
		reservationObject4.setDateReservationMade("04/21/2017");
		reservationObject4.setDatePaid("04/21/2017");

		reservationObjectList.add(reservationObject1);
		reservationObjectList.add(reservationObject2);
		reservationObjectList.add(reservationObject3);
		reservationObjectList.add(reservationObject4);

		return reservationObjectList;

	}
	
	private List<CustomerOrder> getCustomerOrderObjects() {
		
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
		
		CustomerOrder customerOrder1 = new CustomerOrder();
		customerOrder1.setCustomerNo(1111);
		customerOrder1.setorderNo(10000081);
		
		CustomerOrder customerOrder2 = new CustomerOrder();
		customerOrder2.setCustomerNo(1112);
		customerOrder2.setorderNo(10000082);
		
		CustomerOrder customerOrder3 = new CustomerOrder();
		customerOrder3.setCustomerNo(1113);
		customerOrder3.setorderNo(10000061);
		
		CustomerOrder customerOrder4 = new CustomerOrder();
		customerOrder4.setCustomerNo(1114);
		customerOrder4.setorderNo(10000084);
		
		customerOrders.add(customerOrder1);
		customerOrders.add(customerOrder2);
		customerOrders.add(customerOrder3);
		customerOrders.add(customerOrder4);
		
		
		
		return customerOrders;
		
	}
	
	private List<MemberShip> getMemberShipObjects() {
		List<MemberShip> memberShipsList = new ArrayList<MemberShip>();
		MemberShip memberShip1 = new MemberShip();
		memberShip1.setmemberId(00001);
		memberShip1.setcustomerNo(10000081);
		memberShip1.setStartDate("06/24/2017");
		memberShip1.setEndDate("06/23/2018");
		
		MemberShip memberShip2 = new MemberShip();
		memberShip2.setmemberId(00002);
		memberShip2.setcustomerNo(10000082);
		memberShip2.setStartDate("06/30/2016");
		memberShip2.setEndDate("06/29/2017");
		
		MemberShip memberShip3 = new MemberShip();
		memberShip3.setmemberId(00003);
		memberShip3.setcustomerNo(10000084);
		memberShip3.setStartDate("05/30/2017");
		memberShip3.setEndDate("05/29/2018");
		
		memberShipsList.add(memberShip1);
		memberShipsList.add(memberShip2);
		memberShipsList.add(memberShip3);
//		System.out.println(memberShip1);
		return memberShipsList;
	}
	
	
	public void insertValuesToTables(Connection conn) {
		// insert queries


		this.insertIntoBookTable(conn);


		this.insertIntoCustomerTable(conn);


		this.insertIntoPhoneNumberTable(conn);
		
		this.insertIntoOrderTable(conn);
		
		this.insertIntoCustomerOrderTable(conn);
		
		this.insertIntoMemberShipTable(conn);




	}
	
	private void insertIntoCustomerTable(Connection conn) {
		// String insertIntoCustomerTableQuery1 = "INSERT INTO customer VALUES "
		// +
		// "(000000000002, 'Tom', 'Rinka', 'trink@gmail.com', '123 ExampleSt',
		// 'Cleveland', 'OH', "
		// + "'75244', '1200456898651234');";
		String insertIntoCustomerTableQuery = "INSERT INTO customer VALUES " + "(?, ?, ?,?,?,?,?,?,?);";

		// loop the list fo ship objects and exceute insert query

		System.out.println("Size : " + this.getCustomerObjects());
		PreparedStatement pst = null;
		for (Customer customerObeject : this.getCustomerObjects()) {

			try {
				pst = conn.prepareStatement(insertIntoCustomerTableQuery);

				pst.setLong(1, customerObeject.getCustomerNo());
				pst.setString(2, customerObeject.getFirstName());
				pst.setString(3, customerObeject.getLastName());
				pst.setString(4, customerObeject.getEmail());

				pst.setString(5, customerObeject.getAddress().getStreet());
				pst.setString(6, customerObeject.getAddress().getCity());
				pst.setString(7, customerObeject.getAddress().getState());
				pst.setString(8, customerObeject.getAddress().getZip());
				pst.setString(9, customerObeject.getCreditCard().getCreditCardNo());

				pst.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	private void insertIntoPhoneNumberTable(Connection conn) {

		String insertIntoPhoneNumberQuery = "INSERT INTO phoneNumber VALUES " + "(?,?, ?);";
		PreparedStatement pst = null;

		try {

			for (Phone phone : this.getPhoneNumberObjects()) {
				pst = conn.prepareStatement(insertIntoPhoneNumberQuery);
				pst.setString(1, phone.getPhoneNumber());
				pst.setLong(2, phone.getCustomerNo());
				pst.setString(3, phone.getType());

				pst.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	private void insertIntoBookTable(Connection conn){
		
		String insertIntoBookQuery = "INSERT INTO book VALUES " + "(?, ?,?,?);";
		PreparedStatement pst = null;
		
		try {
			
			for (Book book : this.getBookObjects()){
				pst = conn.prepareStatement(insertIntoBookQuery);
				pst.setInt(1, book.getBookNo());
				pst.setString(2, book.getBookName());
				pst.setString(3, book.getBookType());
				pst.setString(4, book.getBookCategory());
				
				pst.executeUpdate();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void insertIntoOrderTable(Connection conn) {
		
		String insertIntoOrderTableQuery = "INSERT INTO productOrder VALUES (?, ?, "
				+ "STR_TO_DATE(?,'%m/%d/%Y'), STR_TO_DATE(?,'%m/%d/%Y'));";
		PreparedStatement pst = null;
		
		try {
			
			for (Reservation reservation : this.getProductOrderObjects()) {
				pst = conn.prepareStatement(insertIntoOrderTableQuery);

				pst.setInt(1, reservation.getReservationNo());
				pst.setInt(2, reservation.getCruiseNo());
				pst.setString(3, reservation.getDateReservationMade());
				pst.setString(4, reservation.getDatePaid());

				pst.execute();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	private void insertIntoCustomerOrderTable(Connection conn) {
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrder VALUES(?,?)";
		
		PreparedStatement pst = null;
		
		try {
			
			for (CustomerOrder customerOrder : this.getCustomerOrderObjects()){
				pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
				
				pst.setLong(1, customerOrder.getCustomerNo());
				pst.setInt(2, customerOrder.getorderNo());
				
				pst.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	private void insertIntoMemberShipTable(Connection conn) {
		String insertIntoMemberShipQuery = "INSERT INTO memberShip VALUES"
				+ "(?, ?, STR_TO_DATE(?,'%m/%d/%Y'),STR_TO_DATE(?,'%m/%d/%Y'));";
		
		PreparedStatement pst = null;
		
		try {
			for (MemberShip memberShip:this.getMemberShipObjects()){
				pst = conn.prepareStatement(insertIntoMemberShipQuery);
				pst.setInt(1, memberShip.getMemberId());
				pst.setLong(2, memberShip.getcustomerNo());
				pst.setString(3, memberShip.getStartDate());
				pst.setString(4, memberShip.getEndDate());
				
				pst.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
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
