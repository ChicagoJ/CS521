package model;

import java.util.List;

public class Customer {

	private int customerNo;
	private String firstName;
	private String lastName;
	private String email;
	private Address address;
	private CreditCard creditCard;
	private List<Phone> phoneList;

	public int getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCardNo(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhone(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}
}