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
import model.FCarsRentalSystem;
import model.FileProcessing;
import model.rental.car.*;

/**
 * Controller class for the update car window. 
 * 
 * @version 1.0
 * @since 2020-11-15
 */

public class UpdateCarController {

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
	private Button updateBTN;

	@FXML
	private Button cancelBTN;

	@FXML
	void handleCancel(ActionEvent event) {
		Stage addCarStage = (Stage) cancelBTN.getScene().getWindow();
		addCarStage.close();
	}

	@FXML
	void handleUpdate(ActionEvent event) {
		String plateNo = plateNoTF.getText();
		String model = modelTF.getText();
		CarType type = typeCB.getValue();

		boolean isAvailable = true;
		if (availableYesRB.isSelected())
			isAvailable = true;
		else if (availableNoRB.isSelected())
			isAvailable = false;

		if (model.isBlank()) {
			// Sets the border color of text field to red in case this info is missing.
			plateNoTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
			Alert message = new Alert(Alert.AlertType.ERROR, "Incomplete model. Please try again.");
			message.setTitle("Update Car");
			message.show();
			return;
		}

		Car updatedCar = new Car(plateNo, model, type, isAvailable);

		FCarsRentalSystem.getCars().set(MainCarController.selectedTableindex, updatedCar);
		FileProcessing.writeCarDB(FCarsRentalSystem.getCars());

		Alert message = new Alert(Alert.AlertType.INFORMATION, "Car updated succesfully.");
		message.setTitle("Add Car");
		message.show();
		Stage updateCarStage = (Stage) updateBTN.getScene().getWindow();
		updateCarStage.close();
	}

	public void initialize(String plateNo, String model, boolean isAvailable, CarType type) {
		typeCB.getItems().addAll(CarType.values());

		plateNoTF.setText(plateNo);
		plateNoTF.disableProperty().setValue(true);
		modelTF.setText(model);
		typeCB.setValue(type);

		if (isAvailable)
			availableYesRB.setSelected(true);
		else
			availableNoRB.setSelected(true);
	}
}
