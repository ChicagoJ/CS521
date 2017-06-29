package model;

public class Ship {

	private String hullId;
	private String modelNo;
	private String shipName;
	private int capacity;
	private int yearBuilt;

	public String getHullId() {
		return hullId;
	}

	public void setHullId(String hullId) {
		this.hullId = hullId;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
}