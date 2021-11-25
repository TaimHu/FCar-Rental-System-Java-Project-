package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FCarsRentalSystem;
import model.FileProcessing;
import model.rental.customer.*;

/**
 * Controller class for the main customer window showing details of all the
 * customers and also buttons to add, update or delete a customer.
 * 
 * @version 1.0
 * @since 2020-11-15
 */

public class MainCustomerController implements Initializable {

	@FXML
	private Button deleteBtn;

	@FXML
	private Button addBtn;

	@FXML
	private Button updateBtn;

	@FXML
	private Button backBtn;

	@FXML
	private TableView<Customer> customerView;

	@FXML
	private TableColumn<Customer, Integer> id;

	@FXML
	private TableColumn<Customer, String> name;

	@FXML
	private TableColumn<Customer, String> phone;

	@FXML
	private TableColumn<Customer, String> address;

	@FXML
	private TableColumn<Customer, String> nationality;

	@FXML
	private TableColumn<Customer, String> drivingLicense;

	@FXML
	private TableColumn<Visitor, String> passportNo;

	@FXML
	private TableColumn<Visitor, String> entryDate;

	@FXML
	private TableColumn<Visitor, String> expiryDate;

	@FXML
	private TableColumn<Resident, String> qatarId;

	static int selectedTableindex = -1;

	public static boolean isInteger(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@FXML
	void handleAdd(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent addCustRoot = FXMLLoader.load(getClass().getResource("../view/AddCustomerView.fxml"));
			// forces the user to stay on the same window until he closes it.
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("FCar Rental System - Add Customer");
			stage.setScene(new Scene(addCustRoot, 800, 600));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			System.out.println("Add Customer view not found.");
		}
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
	void handleDelete(ActionEvent event) {

		// Validation check: if no customers are loaded to the table
		if (FCarsRentalSystem.getCustomers().isEmpty()) {
			Alert message = new Alert(Alert.AlertType.ERROR, "There are no customers to delete.");
			message.show();
			return;
		}

		if (selectedTableindex < 0) {
			Alert message = new Alert(Alert.AlertType.ERROR, "Please select a customer to delete.");
			message.show();
			return;
		}

		int id = FCarsRentalSystem.getCustomers().get(selectedTableindex).getCustomerId();
		String name = FCarsRentalSystem.getCustomers().get(selectedTableindex).getName();

		Alert message = new Alert(Alert.AlertType.CONFIRMATION,
				"Are you sure you want to delete customer with ID number: " + id + " Name: " + name + "?");
		message.getDialogPane().setMinSize(200, 200);
		message.setTitle("Delete Customer");
		message.showAndWait();

		if (message.getResult() == ButtonType.OK) {
			FCarsRentalSystem.getCustomers().remove(selectedTableindex);
			FileProcessing.writeCustomerDB(FCarsRentalSystem.getCustomers());

			// resets table selection, to avoid outOFbound exception
			selectedTableindex = -1;
		}
	}

	@FXML
	void handleSelection(MouseEvent event) {
		selectedTableindex = customerView.getSelectionModel().getSelectedIndex();
	}

	@FXML
	void handleUpdate(ActionEvent event) {
		if (selectedTableindex < 0) {
			Alert message = new Alert(Alert.AlertType.ERROR, "Please select a customer to update.");
			message.show();
			return;
		}

		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateCustomerView.fxml"));
			Parent updateCustomerRoot = loader.load();

			UpdateCustomerController updateSceneController = loader.getController();
			Customer selectedCustomer = FCarsRentalSystem.getCustomers().get(selectedTableindex);
			if (selectedCustomer instanceof Visitor) {

				updateSceneController.initialize(selectedCustomer.getCustomerId(), selectedCustomer.getName(),
						selectedCustomer.getPhone(), selectedCustomer.getAddress(), selectedCustomer.getNationality(),
						selectedCustomer.getDrivingLicence(), ((Visitor) selectedCustomer).getPassportNo(),
						((Visitor) selectedCustomer).getEntryDate(), ((Visitor) selectedCustomer).getVisaExpiryDate());

			} else if (selectedCustomer instanceof Resident)
				updateSceneController.initialize(selectedCustomer.getCustomerId(), selectedCustomer.getName(),
						selectedCustomer.getPhone(), selectedCustomer.getAddress(), selectedCustomer.getNationality(),
						selectedCustomer.getDrivingLicence(), ((Resident) selectedCustomer).getQatarId());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("FCar Rental System - Update Customer");
			stage.setScene(new Scene(updateCustomerRoot, 800, 600));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			System.out.println("Update Customer view not found.");
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("CustomerId"));
		name.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
		phone.setCellValueFactory(new PropertyValueFactory<Customer, String>("Phone"));
		address.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
		nationality.setCellValueFactory(new PropertyValueFactory<Customer, String>("Nationality"));
		drivingLicense.setCellValueFactory(new PropertyValueFactory<Customer, String>("DrivingLicence"));

		passportNo.setCellValueFactory(cellData -> {
			if (cellData.getValue() instanceof Visitor)
				return new SimpleStringProperty(((Visitor) cellData.getValue()).getPassportNo() + "");
			else
				return new SimpleStringProperty("");
		});

		entryDate.setCellValueFactory(cellData -> {
			if (cellData.getValue() instanceof Visitor)
				return new SimpleStringProperty(((Visitor) cellData.getValue()).getEntryDate() + "");
			else
				return new SimpleStringProperty("");
		});

		expiryDate.setCellValueFactory(cellData -> {
			if (cellData.getValue() instanceof Visitor)
				return new SimpleStringProperty(((Visitor) cellData.getValue()).getVisaExpiryDate() + "");
			else
				return new SimpleStringProperty("");
		});

		qatarId.setCellValueFactory(cellData -> {
			if (cellData.getValue() instanceof Resident)
				return new SimpleStringProperty(((Resident) cellData.getValue()).getQatarId() + "");
			else
				return new SimpleStringProperty("");
		});

		customerView.setItems(FCarsRentalSystem.getCustomers());

	}

}
