package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.FileProcessing;

/**
 * Controller class for the main UI window of the application. Contains buttosn
 * that allow to manage all customers, cars and rentals saved in the rental
 * system.
 * 
 * @version 1.0
 * @since 2020-11-15
 */

public class MainUIController {

	@FXML
	private Button carsBTN;

	@FXML
	private Button custBTN;

	@FXML
	private Button rentalBTN;

	@FXML
	private Button exitBtn;

	@FXML
	void handleCars(ActionEvent event) {
		try {
			Stage stage = (Stage) carsBTN.getScene().getWindow();
			Parent carsRoot = FXMLLoader.load(getClass().getResource("../view/MainCarView.fxml"));

			stage.setTitle("FCar Rental System - Cars");
			stage.setScene(new Scene(carsRoot, 600, 500));
		} catch (IOException e) {
			System.out.println("Car view not found.\n" + e);
		}

	}

	@FXML
	void handleCust(ActionEvent event) {
		try {

			Stage stage = (Stage) custBTN.getScene().getWindow();
			Parent customersRoot = FXMLLoader.load(getClass().getResource("../view/MainCustomerView.fxml"));

			stage.setTitle("FCar Rental System - Customer");
			stage.setScene(new Scene(customersRoot, 1000, 600));
			stage.setResizable(false);
		} catch (IOException e) {
			System.out.println("Customer view not found.\n" + e);
		}

	}

	@FXML
	void handleRental(ActionEvent event) {
		try {
			if (FileProcessing.loadCustomerDB().isEmpty() || FileProcessing.loadCarDB().isEmpty()) {
				Alert message = new Alert(Alert.AlertType.ERROR, "You cannot access the rental window unless "
						+ "you have regsitered atleast a customer and a car.");
				message.getDialogPane().setMinSize(200, 100);
				message.setTitle("FCar Rental System - Rental");
			} else {

				Stage stage = (Stage) rentalBTN.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("../view/MainRentalView.fxml"));

				stage.setTitle("FCar Rental System - Rent a car");
				stage.setScene(new Scene(root, 1050, 550));
				stage.setResizable(false);
				stage.show();
			}
		} catch (IOException e) {
			System.out.println("Rental view not found.\n" + e);
		}

	}

	@FXML
	void handleExit(ActionEvent event) {
		Stage exitStage = (Stage) exitBtn.getScene().getWindow();
		exitStage.close();
	}

}
