package model;

public class Phone {

	private String phoneNumber;
	private Long customerNo;
	private String type;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}