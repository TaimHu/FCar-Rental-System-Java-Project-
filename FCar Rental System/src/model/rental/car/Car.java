package model.rental.car;

import java.io.Serializable;

/**
 * Represents a car. Used to store details about different cars available in
 * rental systems.
 * 
 * @version 1.0
 * @since 2020-10-15
 */

public class Car implements Serializable {

	private String plateNo;
	private String model;
	private CarType type;
	private boolean isAvailable;

	public Car(String plateNo, String model, CarType type, boolean isAvailable) {
		this.plateNo = plateNo;
		this.model = model;
		this.type = type;
		this.isAvailable = isAvailable;
	}

	/**
	 * Gets the plate number of the car.
	 * 
	 * @return Plate number of the car.
	 */
	public String getPlateNo() {
		return plateNo;
	}

	/**
	 * Sets the plate number of the car.
	 * 
	 * @param plateNo Plate number that needs to be set for the car.
	 */
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	/**
	 * Sets the plate number of the car.
	 * 
	 * @param model Model that needs to be set for the car.
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Sets the type of the car.
	 * 
	 * @param type Type that needs to be set for the car.
	 */
	public void setType(CarType type) {
		this.type = type;
	}

	/**
	 * Gets the model of the car.
	 * 
	 * @return Model of the car.
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Gets the type of the car.
	 * 
	 * @return CarType of the car.
	 */
	public CarType getType() {
		return type;
	}

	/**
	 * Sets the availability of the car.
	 * 
	 * @param isAvailable The availability the car must be set to true for available
	 *                    and false for not available.
	 */
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * Checks if the car is available or not.
	 * 
	 * @return true if the car is available. false if the car is not available.
	 */
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	/**
	 * Checks if the car is available or not. Alias for getIsAvailable()
	 * 
	 * @return true if the car is available. false if the car is not available.
	 */
	public boolean isAvailable() {
		return isAvailable;
	}
	
	@Override
	public String toString() {
		return "[CAR] PlateNumber: " + plateNo + ", Model: " + model + ", Type: " + type;
	}
}
