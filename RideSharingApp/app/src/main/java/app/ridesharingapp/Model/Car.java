package app.ridesharingapp.Model;

public class Car {
    private String owner;
    private String model;
    private String year;
    private String plate;
    private String fuel;
    private String color;

    public Car(String owner, String model, String year, String plate, String fuel, String color) {
        this.owner = owner;
        this.model = model;
        this.year = year;
        this.plate = plate;
        this.fuel = fuel;
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
