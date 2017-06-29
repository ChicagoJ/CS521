package model;

public class Cabin {

	private int cabinNo;
	private String shipNo;
	private int floor;
	private float cabinPricePerNight;
	private int numberOfBeds;

	public int getCabinNo() {
		return cabinNo;
	}

	public void setCabinNo(int cabinNo) {
		this.cabinNo = cabinNo;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setshipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public float getCabinPricePerNight() {
		return cabinPricePerNight;
	}

	public void setCabinPricePerNight(float cabinPricePerNight) {
		this.cabinPricePerNight = cabinPricePerNight;
	}

	public int getNumberOfBeds() {
		return numberOfBeds;
	}

	public void setNumberOfBeds(int numberOfBeds) {
		this.numberOfBeds = numberOfBeds;
	}
}