package model.rental.customer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This Class inherits from the Customer Class, It holds Data for Customers who
 * are visitors to the Country.
 * 
 * @version 1.0
 * @since 2020-10-17
 * 
 */

public class Visitor extends Customer implements Serializable {

	private int passportNo;
	private LocalDate entryDate;
	private LocalDate visaExpiryDate;

	public Visitor(int customerId, String name, String phone, String address, String nationality, String drivingLicence,
			int passportNo, LocalDate entryDate, LocalDate visaExpiryDate) {
		super(customerId, name, phone, address, nationality, drivingLicence);
		this.passportNo = passportNo;
		this.entryDate = entryDate;
		this.visaExpiryDate = visaExpiryDate;
	}

	/**
	 * Returns the Passport Number of the Customer
	 * 
	 * @return String - Passport Number of the Customer
	 */
	public int getPassportNo() {
		return passportNo;
	}

	/**
	 * Sets the Passport Number of the Customer
	 * 
	 * @param passportNo New passport number of the customer
	 */
	public void setPassportNo(int passportNo) {
		this.passportNo = passportNo;
	}

	/**
	 * Returns the Entry Date of the Customer
	 * 
	 * @return LocalDate - Entry Date of the Customer
	 */
	public LocalDate getEntryDate() {
		return entryDate;
	}

	/**
	 * 
	 * Sets the Entry Date of the Customer's
	 * 
	 * @param entryDate New entry date of the customer
	 */

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * Returns the Expiry Date of the Customer's visa
	 * 
	 * @return LocalDate - Expiry Date of the Customer's visa
	 */
	public LocalDate getVisaExpiryDate() {
		return visaExpiryDate;
	}

	/**
	 * Sets the Expiry Date of the Customer's visa
	 * 
	 * @param visaExpiryDate New visa expiry date of the customer
	 */

	public void setVisaExpiryDate(LocalDate visaExpiryDate) {
		this.visaExpiryDate = visaExpiryDate;
	}

	@Override
	public String toString() {
		return super.toString() + ", PassportNo: " + passportNo + ", EntryDate: " + entryDate + ", VisaExpiryDate: "
				+ visaExpiryDate;
	}

}
