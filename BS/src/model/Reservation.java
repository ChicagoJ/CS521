package model;

import java.sql.Date;

public class Reservation {

	private int reservationNo;
	private int cruiseNo;
	private String dateReservationMade;
	private String datePaid;

	public int getReservationNo() {
		return reservationNo;
	}

	public void setReservationNo(int reservationNo) {
		this.reservationNo = reservationNo;
	}

	public int getCruiseNo() {
		return cruiseNo;
	}

	public void setCruiseNo(int cruiseNo) {
		this.cruiseNo = cruiseNo;
	}

	public String getDateReservationMade() {
		return dateReservationMade;
	}

	public void setDateReservationMade(String string) {
		this.dateReservationMade = string;
	}

	public String getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}


}