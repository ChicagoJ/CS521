package model;

import java.sql.Date;

public class Cruise {

	private String cruiseNo;
	private Date startDate;
	private Date endDate;
	private String destination;
	private String shipNO;

	public String getCruiseNo() {
		return cruiseNo;
	}

	public void setCruiseNo(String cruiseNo) {
		this.cruiseNo = cruiseNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getShipNO() {
		return shipNO;
	}

	public void setShipNO(String shipNO) {
		this.shipNO = shipNO;
	}

}