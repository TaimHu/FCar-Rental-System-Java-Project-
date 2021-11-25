package model.rental.invoice;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Payment class contains getters and setters for the provided instance
 * variables that are used by the invoice class
 * 
 * @version 1.0
 * @since 2020-10-12
 * 
 */

public class Payment implements Serializable {
	private int paymentId;
	private String paymentDescription;
	private double price;
	private LocalDate paymentDate;

	public Payment(int paymentId, String paymentDescription, double price, LocalDate paymentDate) {
		this.paymentDescription = paymentDescription;
		this.paymentId = paymentId;
		this.price = price;
		this.paymentDate = paymentDate;
	}

	/**
	 * Gets the payment ID
	 * 
	 * @return Returns an integer value of the payment ID
	 */
	public int getPaymentId() {
		return paymentId;
	}

	/**
	 * Sets the payment ID
	 * 
	 * @param paymentId New payment ID of the payment
	 */
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * Gets the price for the payment
	 * 
	 * @return Returns the value of the price of data type double
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price of the payment of the data type double
	 * 
	 * @param price New price of the payment
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets the payment date
	 * 
	 * @return Returns the payment date
	 */
	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Sets the payment date
	 * 
	 * @param paymentDate New payment date of the payment
	 */
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return "[PAYMENT] PaymentID: " + paymentId + ", PaymentDesc: " + paymentDescription + ", Price: " + price
				+ ", PaymentDate: " + paymentDate;
	}

}
