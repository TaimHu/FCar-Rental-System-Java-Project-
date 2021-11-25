package model.rental.invoice;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Invoice class contains methods to add, modify or delete payments stores along
 * with their dates
 * 
 * @version 1.0
 * @since 2020-10-12
 * 
 */

public class Invoice implements Serializable {

	private int invoiceNo = 1;
	private LocalDate invoiceDate;
	private ArrayList<Payment> payments = new ArrayList<Payment>();

	public Invoice(int invoiceNo, LocalDate invoiceDate) {
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
	}

	public Invoice(int invoiceNo, LocalDate invoiceDate, ArrayList<Payment> payments) {
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.payments = payments;
	}

	/**
	 * Gets the invoice number
	 * 
	 * @return Returns the invoice number
	 */
	public int getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * Sets the invoice number
	 * 
	 * @param invoiceNo New invoice number of the invoice
	 */
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;

	}

	/**
	 * Gets the invoice date
	 * 
	 * @return Returns the invoice date
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * Sets the the date for the invoice
	 * 
	 * @param invoiceDate New invoice date of the invoice
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * Adds the payment to the invoice.
	 * 
	 * @param payment The payment to be added to the invoice.
	 * @return Returns a string statement.
	 */
	public String addPayment(Payment payment) {
		payments.add(payment);
		return "Added payment with ID " + payment.getPaymentId() + " successfully.";
	}

	/**
	 * @param payment The payment that has already been made is replaced by new
	 *                payment with same paymentId.
	 * 
	 * @return Returns a string to inform the user about the modification otherwise
	 *         returns a string with error message.
	 */
	public String modifyPayment(Payment payment) {
		for (int i = 0; i < payments.size(); i++) {
			if (payments.get(i).getPaymentId() == payment.getPaymentId()) {
				payments.set(i, payment);
				return "Updated payment successfully.";
			}
		}
		return "Payment with the given ID not found.";
	}

	/**
	 * @param paymentId The ID of the payment that has to be deleted from the
	 *                  invoice.
	 * 
	 * @return Returns a string to inform the user about the modification otherwise
	 *         returns a string with error message.
	 */
	public String deletePayment(int paymentId) {
		for (int i = 0; i < payments.size(); i++) {
			if (payments.get(i).getPaymentId() == paymentId) {
				payments.remove(i);
				return "Deleted payment successfully.";
			}
		}

		return "Payment does not exist.";
	}

	/**
	 * 
	 * @param paymentId The ID of the payment to be fetched from the invoice.
	 * @return Returns the payment with the same paymentId, otherwise returns null
	 */
	public Payment getPayment(int paymentId) {
		for (Payment payment : payments)
			if (payment.getPaymentId() == paymentId)
				return payment;

		return null;

	}

	/**
	 * The methods calculates the total payment for the invoice
	 * 
	 * @return Returns the total payment amount with data type, double
	 */
	public double calculateTotalPayment() {
		double total = 0;
		for (int i = 0; i < payments.size(); i++)
			total += payments.get(i).getPrice();
		return total;
	}

	public double getTotal() {
		return this.calculateTotalPayment();
	}

	@Override
	public String toString() {
		return "[INVOICE] InvoiceNumber: " + invoiceNo + ", Date: " + invoiceDate + ", TotalPayment: "
				+ calculateTotalPayment();
	}
}
