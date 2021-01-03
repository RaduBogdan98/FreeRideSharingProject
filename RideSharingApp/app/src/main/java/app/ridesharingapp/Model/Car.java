package app.ridesharingapp.Model;

public class Car {
    private String carManufacturerName;
    private String carModel;
    private String carManufacturingYear;
    private String carFuelType;
    private String carColor;

    public Car(String carManufacturerName, String carModel, String carManufacturingYear, String carFuelType, String carColor) {
        this.carManufacturerName = carManufacturerName;
        this.carModel = carModel;
        this.carManufacturingYear = carManufacturingYear;
        this.carFuelType = carFuelType;
        this.carColor = carColor;
    }

    public String getCarManufacturerName() {
        return carManufacturerName;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarManufacturingYear() {
        return carManufacturingYear;
    }

    public String getCarFuelType() {
        return carFuelType;
    }

    public String getCarColor() {
        return carColor;
    }
}
