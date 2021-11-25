package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import model.rental.Rental;
import model.rental.car.Car;
import model.rental.customer.Customer;

/**
 * Unified file processing class that allows to separate the file processing
 * methods from the main system classes. At runtime, the FCarRentalSystem class
 * uses this class to read data from the DB and all controller classes also use
 * this class to write data to the DB.
 * 
 * Contains methods to read and write data from the customer, car and rental
 * database.
 * 
 * @version 1.0
 * @since 2020-10-15
 */

public class FileProcessing {

	public static List<Customer> loadCustomerDB() {
		List<Customer> custList = new ArrayList<Customer>();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("customerDB.dat"));
			custList = Arrays.asList((Customer[]) in.readObject());
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Customer database file not found.");
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println("Error when processing customer database.");
		} catch (ClassNotFoundException e) {
			System.out.println("No customer data in database.");
		}
		return custList;
	}

	public static void writeCustomerDB(ObservableList<Customer> customers) {
		try {
			Customer[] customersArray = customers.toArray(new Customer[customers.size()]);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("customerDB.dat"));
			out.writeObject(customersArray);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Customer database file not found.");
		} catch (IOException e) {
			System.out.println("Error when processing customer database.");
		}
	}

	public static List<Car> loadCarDB() {
		List<Car> carList = new ArrayList<Car>();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("carDB.dat"));
			carList = Arrays.asList((Car[]) in.readObject());
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Car database file not found.");
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println("Error when processing car database.\n" + e);
		} catch (ClassNotFoundException e) {
			System.out.println("No car data in database.");
		}
		return carList;
	}

	public static void writeCarDB(ObservableList<Car> cars) {
		try {
			Car[] carsArray = cars.toArray(new Car[cars.size()]);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("carDB.dat"));
			out.writeObject(carsArray);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Car database file not found.");
		} catch (IOException e) {
			System.out.println("Error when processing car database.");
		}
	}

	public static List<Rental> loadRentalDB() {
		List<Rental> rentalList = new ArrayList<Rental>();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("rentalDB.dat"));
			rentalList = Arrays.asList((Rental[]) in.readObject());
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Rental database file not found.");
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println("Error when processing rental database.\n" + e);
		} catch (ClassNotFoundException e) {
			System.out.println("No rental data in database.");
		}
		return rentalList;
	}

	public static void writeRentalDB(ObservableList<Rental> rentals) {
		try {
			Rental[] rentalsArray = rentals.toArray(new Rental[rentals.size()]);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("rentalDB.dat"));
			out.writeObject(rentalsArray);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Rental database file not found.");
		} catch (IOException e) {
			System.out.println("Error when processing rental database.");
		}
	}
}
