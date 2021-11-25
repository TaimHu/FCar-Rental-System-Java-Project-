package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.rental.car.*;
import model.FCarsRentalSystem;
import model.FileProcessing;

/**
 * Controller class for the add car window. 
 * 
 * @version 1.0
 * @since 2020-11-15
 */

public class AddCarController {

	@FXML
	private TextField plateNoTF;

	@FXML
	private TextField modelTF;

	@FXML
	private RadioButton availableYesRB;

	@FXML
	private ToggleGroup availability;

	@FXML
	private RadioButton availableNoRB;

	@FXML
	private ComboBox<CarType> typeCB;

	@FXML
	private Button addBTN;

	@FXML
	private Button cancelBTN;

	@FXML
	void handleAdd(ActionEvent event) {
		String plateNo = plateNoTF.getText();
		String model = modelTF.getText();
		CarType type = typeCB.getValue();

		boolean isAvailable = true;
		if (availableYesRB.isSelected())
			isAvailable = true;
		else if (availableNoRB.isSelected())
			isAvailable = false;

		if (plateNo.isBlank()) {
			// Sets the border color of text field to red in case this info is missing.
			plateNoTF.setStyle("-fx-text-box-border: #B22222;");
			Alert message = new Alert(Alert.AlertType.ERROR, "Incomplete plate number. Please try again.");
			message.setTitle("Add Car");
			message.show();
			return;
		}
		if (model.isBlank()) {
			plateNoTF.setStyle("-fx-text-box-border: #B22222;");
			Alert message = new Alert(Alert.AlertType.ERROR, "Incomplete model. Please try again.");
			message.setTitle("Add Car");
			message.show();
			return;
		}
		for (Car car : FCarsRentalSystem.getCars()) {
			if (plateNo.equals(car.getPlateNo())) {
				Alert message = new Alert(Alert.AlertType.ERROR, "Car with the same plate number already exists.");
				message.setTitle("Unexpected Error");
				message.show();
				return;
			}
		}
		Car car = new Car(plateNo, model, type, isAvailable);

		FCarsRentalSystem.addCar(car);
		FileProcessing.writeCarDB(FCarsRentalSystem.getCars());

		Alert message = new Alert(Alert.AlertType.INFORMATION, "Car added succesfully.");
		message.setTitle("Add Car");
		message.show();
		Stage addCarStage = (Stage) addBTN.getScene().getWindow();
		addCarStage.close();

	}

	@FXML
	void handleCancel(ActionEvent event) {
		Stage addCarStage = (Stage) cancelBTN.getScene().getWindow();
		addCarStage.close();
	}

	@FXML
	void handleClear(ActionEvent event) {
		plateNoTF.setText("");
		modelTF.setText("");
		availableYesRB.setSelected(true);
		availableNoRB.setSelected(false);
		typeCB.setValue(CarType.SEDAN);
	}

	public void initialize() {
		typeCB.getItems().addAll(CarType.values());
		typeCB.setValue(CarType.SEDAN);
	}
}
