package model;

import java.sql.Date;

public class Order {

	private int orderNo;
	private int bookNo;
	private String dateOrderMade;
	private String datePaid;

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getDateOrderMade() {
		return dateOrderMade;
	}

	public void setDateOrderMade(String dateOrderMade) {
		this.dateOrderMade = dateOrderMade;
	}

	public String getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}


}
