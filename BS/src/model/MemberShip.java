package model;

public class MemberShip {

	private int memberId;
	private long customerNo;
	private String startDate;
	private String endDate;
	
	public int getMemberId(){
		return memberId;
	}
	
	public void setmemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public long getcustomerNo() {
		return customerNo;
	}
	
	public void setcustomerNo(long customerNo) {
		this.customerNo = customerNo;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
