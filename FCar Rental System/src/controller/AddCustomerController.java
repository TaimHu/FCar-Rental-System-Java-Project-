package controller;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.FCarsRentalSystem;
import model.FileProcessing;
import model.rental.customer.Customer;
import model.rental.customer.Resident;
import model.rental.customer.Visitor;

/**
 * Controller class for the add customer window.
 * 
 * @version 1.0
 * @since 2020-11-15
 */

public class AddCustomerController {

	@FXML
	private TextField customerTf;

	@FXML
	private TextField nameTf;

	@FXML
	private TextField phoneTf;

	@FXML
	private TextField addressTf;

	@FXML
	private TextField nationalityTf;

	@FXML
	private TextField drivingTf;

	@FXML
	private TextField passportTf;

	@FXML
	private DatePicker entryDateTf;

	@FXML
	private DatePicker expiryDateTf;

	@FXML
	private TextField qatarIdTf;

	@FXML
	private Button addBtn;

	@FXML
	private Button cancelBtn;

	@FXML
	private RadioButton isResident;

	@FXML
	private ToggleGroup custType;

	@FXML
	private RadioButton isVisitor;

	/**
	 * @param event
	 */

	@FXML
	void handleAdd(ActionEvent event) {
		boolean flag = false;

		if (isResident.isSelected()) {
			// checking if the user enters a value of type string to an integer variable
			if ((MainCustomerController.isInteger(customerTf.getText()) == false
					|| MainCustomerController.isInteger(qatarIdTf.getText()) == false)) {
				Alert message = new Alert(Alert.AlertType.ERROR,
						"Cannot accept non-numerical values for Customer ID or Qatar ID. Please make sure to enter digits.");
				message.getDialogPane().setMinSize(200, 200);
				message.setTitle("Unexpected Error");
				message.show();
				return;
			}
			// Checking if fields are empty or not
			if (nameTf.getText().isBlank() || phoneTf.getText().isBlank() || addressTf.getText().isBlank()
					|| nationalityTf.getText().isBlank() || drivingTf.getText().isBlank()) {
				Alert message = new Alert(Alert.AlertType.ERROR,
						"Incomplete customer details. Please make sure to fill every detail.");
				message.getDialogPane().setMinSize(200, 200);
				message.setTitle("Add Customer");
				message.show();
				return;
			}

			int customerid = Integer.parseInt(customerTf.getText());
			String name = nameTf.getText();
			String phone = phoneTf.getText();
			String address = addressTf.getText();
			String nationality = nationalityTf.getText();
			String drivingLicense = drivingTf.getText();
			int qatarId = Integer.parseInt(qatarIdTf.getText());

			for (Customer cust : FCarsRentalSystem.getCustomers()) {
				if (customerid == cust.getCustomerId() || phone.equals(cust.getPhone()))
					flag = true;
				if (cust instanceof Resident)
					if (qatarId == ((Resident) cust).getQatarId())
						flag = true;
			}
			if (flag) {
				Alert message = new Alert(Alert.AlertType.ERROR, "Customer ID or Qatar ID duplicates exist in DB.");
				message.getDialogPane().setMinSize(200, 200);
				message.setTitle("Add Customer");
				message.show();
				return;
			}

			Customer resident = new Resident(customerid, name, phone, address, nationality, drivingLicense, qatarId);
			FCarsRentalSystem.addCustomer(resident);
			FileProcessing.writeCustomerDB(FCarsRentalSystem.getCustomers());
			Alert message = new Alert(Alert.AlertType.INFORMATION, "Resident added succesfully.");
			message.setTitle("Add Customer");
			message.show();

		} else if (isVisitor.isSelected()) {

			if ((MainCustomerController.isInteger(customerTf.getText()) == false
					|| MainCustomerController.isInteger(passportTf.getText()) == false)) {
				Alert message = new Alert(Alert.AlertType.ERROR,
						"Cannot accept a non-numerical value for Customer ID or Passport. Please make sure to enter digits.");
				message.getDialogPane().setMinSize(200, 200);
				message.setTitle("Unexpected Error");
				message.show();
				return;
			}
			// Checking if fields are empty or not
			if (nameTf.getText().isBlank() || phoneTf.getText().isBlank() || addressTf.getText().isBlank()
					|| nationalityTf.getText().isBlank() || drivingTf.getText().isBlank()
					|| entryDateTf.getValue() == null || expiryDateTf.getValue() == null) {
				Alert message = new Alert(Alert.AlertType.ERROR,
						"Incomplete customer details. Please make sure to fill every detail.");
				message.getDialogPane().setMinSize(200, 200);
				message.setTitle("Add Customer");
				message.show();
				return;
			}
			// checks if the expiry date is set before the entry date
			if (expiryDateTf.getValue().compareTo(entryDateTf.getValue()) < 0) {
				Alert message = new Alert(Alert.AlertType.ERROR,
						"Visa expiry date cannot be before entry date. Please try again.");
				message.setTitle("Date Error");
				message.show();
				return;
			}

			int customerid = Integer.parseInt(customerTf.getText());
			String name = nameTf.getText();
			String phone = phoneTf.getText();
			String address = addressTf.getText();
			String nationality = nationalityTf.getText();
			String drivingLicense = drivingTf.getText();
			int passport = Integer.parseInt(passportTf.getText());
			LocalDate entryDate = entryDateTf.getValue();
			LocalDate expiryDate = expiryDateTf.getValue();

			// Duplicate checking
			for (Customer cust : FCarsRentalSystem.getCustomers()) {
				if (customerid == cust.getCustomerId() || phone.equals(cust.getPhone()))
					flag = true;
				if (cust instanceof Visitor)
					if (passport == ((Visitor) cust).getPassportNo())
						flag = true;
			}

			if (flag) {
				Alert message = new Alert(Alert.AlertType.ERROR, "Customer ID or Phone Number duplicates exist in DB.");
				message.getDialogPane().setMinSize(200, 200);
				message.setTitle("Add Customer");
				message.show();
				return;
			}

			Customer visitor = new Visitor(customerid, name, phone, address, nationality, drivingLicense, passport,
					entryDate, expiryDate);
			FCarsRentalSystem.addCustomer(visitor);
			FileProcessing.writeCustomerDB(FCarsRentalSystem.getCustomers());
			Alert message = new Alert(Alert.AlertType.INFORMATION, "Visitor added succesfully.");
			message.setTitle("Add Customer");
			message.show();
		}

		Stage addCustomerStage = (Stage) addBtn.getScene().getWindow();
		addCustomerStage.close();
	}

	@FXML
	void handleCancel(ActionEvent event) {
		Stage addCustStage = (Stage) cancelBtn.getScene().getWindow();
		addCustStage.close();

	}

	@FXML
	void initialize() {
		passportTf.disableProperty().bind(isResident.selectedProperty());
		entryDateTf.disableProperty().bind(isResident.selectedProperty());
		expiryDateTf.disableProperty().bind(isResident.selectedProperty());
		qatarIdTf.disableProperty().bind(isVisitor.selectedProperty());

	}
}
