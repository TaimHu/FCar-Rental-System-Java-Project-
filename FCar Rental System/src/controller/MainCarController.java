package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.FCarsRentalSystem;
import model.rental.car.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
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

/**
 * Controller class for the main car window showing details of all the cars and
 * also buttons to add, update or delete a car.
 * 
 * @version 1.0
 * @since 2020-11-15
 */

public class MainCarController implements Initializable {

	@FXML
	private Button addBTN;

	@FXML
	private Button updateBTN;

	@FXML
	private Button deleteBTN;

	@FXML
	private Button backBTN;

	@FXML
	private TableView<Car> carTableView;

	@FXML
	private TableColumn<Car, String> platNoCol;

	@FXML
	private TableColumn<Car, String> modelCol;

	@FXML
	private TableColumn<Car, CarType> typeCol;

	@FXML
	private TableColumn<Car, Boolean> availabilityCol;

	static int selectedTableindex = -1;

	@FXML
	void handleSelect(MouseEvent event) {
		selectedTableindex = carTableView.getSelectionModel().getSelectedIndex();
	}

	@FXML
	void handleAdd(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent addCarRoot = FXMLLoader.load(getClass().getResource("../view/AddCarView.fxml"));
			// forces the user to stay on the same window until he closes it.
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("FCar Rental System - Add Car");
			stage.setScene(new Scene(addCarRoot, 450, 300));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			System.out.println("Add car view not found.");
		}
	}

	@FXML
	void handleDelete(ActionEvent event) {
		if (selectedTableindex < 0) {
			Alert message = new Alert(Alert.AlertType.ERROR, "Please select a car to delete.");
			message.show();
			return;
		}

		String plateNo = FCarsRentalSystem.getCars().get(selectedTableindex).getPlateNo();
		Alert message = new Alert(Alert.AlertType.CONFIRMATION,
				"Are you sure you want to delete car with plate number: " + plateNo + "?");
		message.getDialogPane().setMinSize(200, 200);
		message.setTitle("Delete Car");
		message.showAndWait();

		if (message.getResult() == ButtonType.OK) {
			FCarsRentalSystem.getCars().remove(selectedTableindex);
			model.FileProcessing.writeCarDB(FCarsRentalSystem.getCars());
			// resets table selection, to avoid index outOFbound exception
			selectedTableindex = -1;
		}
	}

	@FXML
	void handleUpdate(ActionEvent event) {
		if (selectedTableindex < 0) {
			Alert message = new Alert(Alert.AlertType.ERROR, "Please select a car to update.");
			message.show();
			return;
		}

		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateCarView.fxml"));
			Parent updateCarRoot = loader.load();

			UpdateCarController updateSceneController = loader.getController();
			Car selectedCar = FCarsRentalSystem.getCars().get(selectedTableindex);
			updateSceneController.initialize(selectedCar.getPlateNo(), selectedCar.getModel(),
					selectedCar.isAvailable(), selectedCar.getType());

			// forces the user to stay on the same window until he closes it.
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("FCar Rental System - Update Car");
			stage.setResizable(false);
			stage.setScene(new Scene(updateCarRoot, 450, 300));
			stage.show();
		} catch (IOException e) {
			System.out.println("Add car view not found.");
		}
	}

	@FXML
	void handleBack(ActionEvent event) {
		try {
			Stage stage = (Stage) backBTN.getScene().getWindow();
			Parent mainUIRoot = FXMLLoader.load(getClass().getResource("../view/MainUIView.fxml"));
			stage.setTitle("FCar Rental System");
			stage.setScene(new Scene(mainUIRoot, 600, 500));
		} catch (IOException e) {
			System.out.println("Main UI view not found.");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		platNoCol.setCellValueFactory(new PropertyValueFactory<Car, String>("plateNo"));
		modelCol.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
		typeCol.setCellValueFactory(new PropertyValueFactory<Car, CarType>("type"));
		availabilityCol.setCellValueFactory(new PropertyValueFactory<Car, Boolean>("isAvailable"));
		carTableView.setItems(FCarsRentalSystem.getCars());
	}

}
