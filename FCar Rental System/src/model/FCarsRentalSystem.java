package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.rental.Rental;
import model.rental.car.*;
import model.rental.customer.*;
import model.rental.invoice.*;

/**
 * The main application class for the rental system. The app is started from
 * this class during runtime.
 * 
 * All data from the database is loaded into this class at initial runtime of
 * the application and all controllers can access the data needed for them using
 * the methods in this class. This class also provides methods to edit data of
 * the cars, customers and rentals collected from the DB.
 * 
 * @version 1.0
 * @since 2020-10-15
 */

public class FCarsRentalSystem extends Application {
	private static ObservableList<Customer> customers;
	private static ObservableList<Car> cars;
	private static ObservableList<Rental> rentals;

	/**
	 * Gets a list of all the customers in the system.
	 * 
	 * @return ObservableList of all customers in the system.
	 */
	public static ObservableList<Customer> getCustomers() {
		return customers;
	}

	/**
	 * Gets a list of all the cars in the system.
	 * 
	 * @return ObservableList of all cars in the system.
	 */
	public static ObservableList<Car> getCars() {
		return cars;
	}

	/**
	 * Gets a list of all the rentals in the system.
	 * 
	 * @return ObservableList of all rentals in the system.
	 */
	public static ObservableList<Rental> getRentals() {
		return rentals;
	}

	/**
	 * Adds a car to the rental system.
	 * 
	 * @param car The car object that needs to be added.
	 * @return Confirmation message of addition of car.
	 */
	public static String addCar(Car car) {
		cars.add(car);
		return "Added car successfully.";
	}

	/**
	 * Finds a car using its plate number.
	 * 
	 * @param plateNo Plate number of the car to be found.
	 * @return Car object of the car found.
	 */
	public static Car findCar(String plateNo) {
		for (Car car : cars) {
			if (car.getPlateNo().equals(plateNo))
				return car;
		}
		return null;
	}

	/**
	 * Deletes a car using its plate number.
	 * 
	 * @param plateNo Plate number of the car to be deleted.
	 * @return Confirmation message of deletion of car.
	 */
	public static String deleteCar(String plateNo) {
		for (int i = 0; i < rentals.size(); i++) {
			if (rentals.get(i).getCar().getPlateNo().equals(plateNo)) {
				rentals.remove(i);
				break;
			}
		}
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).getPlateNo().equals(plateNo)) {
				cars.remove(i);
				return "Deleted car successfully.";
			}
		}
		return "Car with given plate number not found.";
	}

	/**
	 * Adds a customer to the rental system.
	 * 
	 * @param customer Customer object of the customer that needs to be added.
	 * @return Confirmation message of addition of customer.
	 */
	public static String addCustomer(Customer customer) {
		customers.add(customer);
		return "Added customer successfully.";
	}

	/**
	 * Finds a customer using their customer ID.
	 * 
	 * @param customerId The ID of the customer to be found.
	 * @return Customer object of the customer found.
	 */
	public static Customer findCustomer(int customerId) {
		for (Customer customer : customers) {
			if (customer.getCustomerId() == customerId)
				return customer;
		}
		return null;
	}

	/**
	 * Deletes a customer using their ID.
	 * 
	 * @param customerId The ID of the customer to be deleted.
	 * @return Confirmation message of deletion of customer.
	 */
	public static String deleteCustomer(int customerId) {
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getCustomerId() == customerId) {
				customers.remove(i);
				return "Deleted customer successfully.";
			}
		}
		return "Customer with ID " + customerId + " not found.";
	}

	/**
	 * Gets a list of cars based on whether their availability.
	 * 
	 * @param available The availability of the cars to be found. true for
	 *                  available, false for not available.
	 * @return List of the cars fetched based on the specified availability. null if
	 *         no cars have the specified availability.
	 */
	public List<Car> getCarByAvailability(boolean available) {
		List<Car> carsByAvailablity = new ArrayList<Car>();
		for (Car car : cars) {
			if (available) {
				if (car.isAvailable())
					carsByAvailablity.add(car);
			} else {
				if (!(car.isAvailable()))
					carsByAvailablity.add(car);
			}
		}

		if (carsByAvailablity.size() > 0)
			return carsByAvailablity;
		else
			return null;
	}

	/**
	 * Books a car for rent from the rental system.
	 * 
	 * @param rental Rental object of the car rental to be made.
	 */
	public static void bookCarRental(Rental rental) {
		rentals.add(rental);

		for (Car car : cars) {
			if (car.getPlateNo().equals(rental.getCar().getPlateNo())) {
				car.setIsAvailable(false);
				break;
			}
		}
	}

	/**
	 * Return a rental car and get its invoice.
	 * 
	 * @param plateNo The plate number of the car being returned.
	 * @return Invoice object of the rented car being returned.
	 */
	public static Invoice returnCar(String plateNo) {
		for (Rental rental : rentals) {
			Car car = rental.getCar();

			if (car.getPlateNo().equals(plateNo) && !car.isAvailable()) {
				car.setIsAvailable(true);
				return rental.getInvoice();
			}
		}
		return null;
	}

	/**
	 * Gets a list of all the rentals by a customer using their ID.
	 * 
	 * @param customerId The ID of the customer whose rentals should be retrieved.
	 * @return List of all the rentals by the specified customer.
	 */
	public static List<Rental> findCarRentalByCustomerId(int customerId) {
		List<Rental> customerRentals = new ArrayList<Rental>();
		for (Rental rental : rentals) {
			if (rental.getCustomer().getCustomerId() == customerId)
				customerRentals.add(rental);
		}
		if (customerRentals.size() > 0)
			return customerRentals;
		else
			return null;
	}

	/**
	 * Deletes the latest active rental by a customer using their ID.
	 * 
	 * @param customerId The ID of the customer whose rental should be deleted.
	 */
	public static void deleteCarRental(int customerId) {
		boolean deleted = false;

		for (int i = 0; i < rentals.size(); i++) {
			Rental rental = rentals.get(i);

			if (rental.getCustomer().getCustomerId() == customerId) {
				if (!(rental.getCar().isAvailable())) {
					rentals.remove(i);
					rental.getCar().setIsAvailable(true);
					deleted = true;
					break;
				}
			}
		}
		if (deleted)
			System.out.println("Rental for customer with ID " + customerId + " has been deleted successfully.\n");
		else
			System.out.println("No active rentals found for customer with ID " + customerId + ".\n");
	}

	/**
	 * Gets a list of cars available by the specified date.
	 * 
	 * @param date The date by which the cars should be available by.
	 * @return List of all cars available by the specified date.
	 */
	public static List<Car> getAvailableCarByDate(LocalDate date) {
		List<Car> availableCars = new ArrayList<Car>();

		for (Car car : cars)
			if (car.isAvailable())
				availableCars.add(car);

		for (Rental rental : rentals) {
			if (!(rental.getCar().isAvailable())) {
				if (rental.getEndDate().isBefore(date))
					availableCars.add(rental.getCar());
			}
		}

		return availableCars;

	}

	// Starting the rental system application...

	public static void main(String[] args) {
		cars = FXCollections.observableArrayList(FileProcessing.loadCarDB());
		customers = FXCollections.observableArrayList(FileProcessing.loadCustomerDB());
		rentals = FXCollections.observableArrayList(FileProcessing.loadRentalDB());

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../view/MainUIView.fxml"));
		stage.setTitle("FCar Rental System");
		stage.setScene(new Scene(root, 600, 550));
		stage.show();
	}
}
