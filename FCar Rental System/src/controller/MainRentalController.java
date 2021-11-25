package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.FCarsRentalSystem;
import model.FileProcessing;
import model.rental.Rental;
import model.rental.car.Car;
import model.rental.customer.Customer;
import model.rental.invoice.Invoice;

/**
 * Controller class for the main rental window. Allows to make a new rental
 * booking and also see all existing rentals.
 * 
 * 
 * @version 1.0
 * @since 2020-11-15
 */

public class MainRentalController implements Initializable {

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private Button rentBTN;

	@FXML
	private ComboBox<Integer> custCombo;

	@FXML
	private ComboBox<String> carCombo;

	@FXML
	private Button backBtn;

	@FXML
	private Button refresh;

	@FXML
	private TableView<Rental> rentalsTableView;

	@FXML
	private TableColumn<Rental, String> customerIdCol;

	@FXML
	private TableColumn<Rental, String> plateNoCol;

	@FXML
	private TableColumn<Rental, String> carModelCol;

	@FXML
	private TableColumn<Rental, LocalDate> startDateCol;

	@FXML
	private TableColumn<Rental, LocalDate> endDateCol;

	@FXML
	private TableColumn<Rental, Double> depositCol;

	@FXML
	private TableColumn<Rental, String> invoiceNoCol;

	@FXML
	private TableColumn<Rental, String> invoiceDateCol;

	@FXML
	private TableColumn<Rental, String> totalCol;

	ObservableList<Rental> customerRentals;

	private void setRentalTableValues(ObservableList<Rental> rentals) {

		// Separate method to add values for the table. Done because when a specific
		// customer is selected, the table needs to be changed to show only that
		// customer's rentals. Code reduction.

		startDateCol.setCellValueFactory(new PropertyValueFactory<Rental, LocalDate>("startDate"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<Rental, LocalDate>("endDate"));
		depositCol.setCellValueFactory(new PropertyValueFactory<Rental, Double>("deposit"));

		invoiceNoCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getInvoice().getInvoiceNo() + ""));
		invoiceDateCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getInvoice().getInvoiceDate() + ""));
		totalCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getInvoice().calculateTotalPayment() + ""));

		customerIdCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerId() + ""));

		plateNoCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCar().getPlateNo() + ""));

		carModelCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCar().getModel() + ""));

		rentalsTableView.setItems(rentals);
	}

	@FXML
	void handleBack(ActionEvent event) {
		try {
			Stage stage = (Stage) backBtn.getScene().getWindow();
			Parent mainUIRoot = FXMLLoader.load(getClass().getResource("../view/MainUIView.fxml"));
			stage.setTitle("FCar Rental System");
			stage.setScene(new Scene(mainUIRoot, 600, 500));
		} catch (IOException e) {
			System.out.println("Main UI view not found.");
		}
	}

	@FXML
	void handleCustCombo(ActionEvent event) {
		if (custCombo.getValue() == null) {
			setRentalTableValues(FCarsRentalSystem.getRentals());
		} else {
			customerRentals = FXCollections.observableArrayList();
			int custID = custCombo.getValue();

			for (Rental rental : FCarsRentalSystem.getRentals()) {
				if (rental.getCustomer().getCustomerId() == custID) {
					customerRentals.add(rental);
				}
			}
			setRentalTableValues(customerRentals);
		}
	}

	@FXML
	void handleRefresh(ActionEvent event) {

		// Checks if the customer's data has been deleted, and if so, it deletes the
		// rental made by such customer.
		if ((FCarsRentalSystem.getRentals().isEmpty() == false || FileProcessing.loadCustomerDB().isEmpty() == false)) {

			for (int i = 0; i < FCarsRentalSystem.getRentals().size(); i++) {
				if ((FCarsRentalSystem.getRentals().get(i).getCustomer().getCustomerId() != FCarsRentalSystem
						.getCustomers().get(i).getCustomerId())) {
					FCarsRentalSystem.getCars().get(i).setIsAvailable(true);
					FCarsRentalSystem.getRentals().remove(FCarsRentalSystem.getRentals().get(i));
					FileProcessing.writeRentalDB(FCarsRentalSystem.getRentals());
					FileProcessing.writeCarDB(FCarsRentalSystem.getCars());
				}
			}
		}
	}

	@FXML
	void handleRent(ActionEvent event) {
		LocalDate startDate = startDatePicker.getValue();
		LocalDate endDate = endDatePicker.getValue();

		if (startDate == null || endDate == null || custCombo.getValue() == null || carCombo.getValue() == null) {
			Alert message = new Alert(Alert.AlertType.ERROR, "Incomplete rental details. Please try again.");
			message.setTitle("Book Rental");
			message.show();
			return;
		}

		if (endDate.compareTo(startDate) < 0) {
			Alert message = new Alert(Alert.AlertType.ERROR, "End date cannot be before start date. Please try again.");
			message.setTitle("Book Rental");
			message.show();
			return;
		}

		int rentalno = FCarsRentalSystem.getRentals().size() + 1;
		int custID = custCombo.getValue();
		String plateNo = carCombo.getValue();

		Car selectedCar = FCarsRentalSystem.findCar(plateNo);

		for (Car car : FCarsRentalSystem.getAvailableCarByDate(startDate)) {
			if (car == selectedCar) {
				// If the selected car is available for the specified start date.
				Invoice inv = new Invoice(rentalno, endDate);
				Rental rental = new Rental(rentalno, FCarsRentalSystem.findCustomer(custID), selectedCar, startDate,
						endDate, 2000, inv);

				FCarsRentalSystem.bookCarRental(rental);
				FileProcessing.writeRentalDB(FCarsRentalSystem.getRentals());
				FileProcessing.writeCarDB(FCarsRentalSystem.getCars());

				// Adding to this list only for displaying the new rental in the customer
				// specific table values.
				customerRentals.add(rental);

				Alert message = new Alert(Alert.AlertType.INFORMATION, "Rental Added Successfully");
				message.setTitle("Book Rental");
				message.show();
				return;
			}
		}
		// If the selected car is NOT in the list of availableCars for the date.
		Alert message = new Alert(Alert.AlertType.ERROR,
				"The selected car is not available for booking on the dates specified.");
		message.getDialogPane().setMinSize(200, 200);
		message.setTitle("Book Rental");
		message.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Adding first item in the customer combo box as null in order to show all
		// customers rentals when selected.
		custCombo.getItems().add(null);
		for (Customer cust : FCarsRentalSystem.getCustomers()) {
			custCombo.getItems().add(cust.getCustomerId());
		}

		for (Car car : FCarsRentalSystem.getCars()) {
			carCombo.getItems().add(car.getPlateNo());
		}

		setRentalTableValues(FCarsRentalSystem.getRentals());

	}
}
