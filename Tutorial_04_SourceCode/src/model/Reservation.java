package model;

import java.sql.Date;

public class Reservation {

	private int reservationNo;
	private int cruiseNo;
	private Date dateReservationMade;
	private Date datePaid;

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

	public Date getDateReservationMade() {
		return dateReservationMade;
	}

	public void setDateReservationMade(Date dateReservationMade) {
		this.dateReservationMade = dateReservationMade;
	}

	public Date getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

}