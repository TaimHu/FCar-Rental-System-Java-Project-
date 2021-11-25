package model.rental;

import java.io.Serializable;
import java.time.LocalDate;

import model.rental.car.*;
import model.rental.customer.*;
import model.rental.invoice.*;

/**
 * Represents a car rental from the rental system. Includes all details of the
 * rental such as Car, Invoice
 * 
 * @version 1.0
 * @since 2020-10-12
 * 
 */

public class Rental implements Serializable {

	private int rentalNo;
	private Customer customer;
	private Car car;
	private LocalDate startDate;
	private LocalDate endDate;
	private double deposit;
	private Invoice invoice;

	public Rental(int rentalNo, Customer customer, Car car, LocalDate startDate, LocalDate endDate, double deposit,
			Invoice invoice) {
		this.rentalNo = rentalNo;
		this.customer = customer;
		this.car = car;
		this.startDate = startDate;
		this.endDate = endDate;
		this.deposit = deposit;
		this.invoice = invoice;
	}

	/**
	 * Gets the rental number
	 * 
	 * @return Returns the rental number of data type integer
	 */
	public int getRentalNo() {
		return rentalNo;
	}

	/**
	 * Sets the rental number
	 * 
	 * @param rentalNo New rental number of the rental
	 */
	public void setRentalNo(int rentalNo) {
		this.rentalNo = rentalNo;
	}

	/**
	 * Gets the customer who made this rental.
	 * 
	 * @return Returns customer.
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Sets the customer for rental.
	 * 
	 * @param customer New customer of the rental
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Gets the car being rented in this rental.
	 * 
	 * @return Returns a car.
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * Sets a car for rental by taking the data as parameter.
	 * 
	 * @param car New car of the rental
	 */
	public void setCar(Car car) {
		this.car = car;
	}

	/**
	 * Gets the start date for renting a car
	 * 
	 * @return Returns the start date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date for renting a car by taking the date as parameter.
	 * 
	 * @param startDate New start date of the rental
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date for renting a car.
	 * 
	 * @return Returns the end date
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date for renting a car.
	 * 
	 * @param endDate New end date of the rental
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the deposit amount for a rental.
	 * 
	 * @return Returns the deposit amount for the rental
	 */
	public double getDeposit() {
		return deposit;
	}

	/**
	 * Sets the deposit for a rental.
	 * 
	 * @param deposit New deposit of the rental
	 */
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	/**
	 * Gets the invoice for a rental.
	 * 
	 * @return Returns invoice
	 */
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * Sets the invoice for a rental.
	 * 
	 * @param invoice New invoice of the rental
	 */
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "[RENTAL] RentalNumber: " + rentalNo + ", StartDate: " + startDate + ", EndDate: " + endDate
				+ ", Deposit: " + deposit;
	}
}
