package model.rental.customer;

import java.io.Serializable;

/**
 * This Class inherits from the Customer Class, It holds Data for Customers who
 * are Residents of the Country and With a Valid ID card.
 * 
 * @version 1.0
 * @since 2020-10-17
 * 
 */

public class Resident extends Customer implements Serializable {

	private int qatarId;

	public Resident(int customerId, String name, String phone, String address, String nationality,
			String drivingLicence, int qatarId) {
		super(customerId, name, phone, address, nationality, drivingLicence);
		this.qatarId = qatarId;
	}

	/**
	 * Returns the Qatar ID of the customer
	 * 
	 * @return Int - Qatar ID of the customer
	 */
	public int getQatarId() {
		return qatarId;
	}

	/**
	 * Sets the Qatar ID of the Customer
	 * 
	 * @param qatarId New ID of the custoemr
	 */
	public void setQatarId(int qatarId) {
		this.qatarId = qatarId;
	}

	@Override
	public String toString() {
		return super.toString() + ", QatarID: " + qatarId;
	}

}
