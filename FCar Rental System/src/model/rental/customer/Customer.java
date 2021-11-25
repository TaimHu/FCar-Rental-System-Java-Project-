package model.rental.customer;

import java.io.Serializable;

/**
 * This Class Holds Data for Customers who are renting a Car, its also inherits
 * it's data to Classes Resident and Visitor.
 * 
 * @version 1.0
 * @since 2020-10-17
 * 
 */

public class Customer implements Serializable {
	private int customerId;
	private String name;
	private String phone;
	private String address;
	private String nationality;
	private String drivingLicence;

	public Customer(int customerId, String name, String phone, String address, String nationality,
			String drivingLicence) {
		this.customerId = customerId;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.nationality = nationality;
		this.drivingLicence = drivingLicence;
	}

	/**
	 * Returns the ID number of the customer
	 * 
	 * @return Int - ID of the customer
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the ID number of the customer
	 * 
	 * @param customerId New ID of the customer
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * Returns the Name of the Customer
	 * 
	 * @return String - Name of the Customer
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Name of the Customer
	 * 
	 * @param name The new name of the customer
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the Phone number of the Customer
	 * 
	 * @return String - Phone number of the Customer
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the Phone number of the Customer
	 * 
	 * @param phone New phone of the customer.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the Address of the Customer
	 * 
	 * @return String - Address of the Customer
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the Address of the Customer
	 * 
	 * @param address New address of the customer
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the nationality of the Customer
	 * 
	 * @return String - Nationality of the Customer
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Sets the Nationality of the Customer
	 * 
	 * @param nationality New nationality of the customer
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Returns the Driving License of the Customer
	 * 
	 * @return String - Driving License of the Customer
	 */
	public String getDrivingLicence() {
		return drivingLicence;
	}

	/**
	 * Sets the Driving License of the Customer
	 * 
	 * @param drivingLicence New driving license of the customer
	 */
	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	@Override
	public String toString() {
		return "[CUSTOMER] CustomerID: " + customerId + ", Name: " + name + ", Phone: " + phone + ", Address: "
				+ address + ", Nationality: " + nationality + ", DrivingLicence: " + drivingLicence;
	}
}
