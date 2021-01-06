package app.ridesharingapp.Model.Requests;

import java.util.ArrayList;
import app.ridesharingapp.Model.Date;

public class AddRideRequest {
    private String driver;
    private String carName;
    private ArrayList<Double> pickup_lat_long;
    private String pickupName;
    private ArrayList<Double> destination_lat_long;
    private String destinationName;
    private Date departureDate;
    private String departureTime;
    private int availablePlaces; // seats

    public AddRideRequest() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public ArrayList<Double> getPickup_lat_long() {
        return pickup_lat_long;
    }

    public void setPickup_lat_long(ArrayList<Double> pickup_lat_long) {
        this.pickup_lat_long = pickup_lat_long;
    }

    public String getPickupName() {
        return pickupName;
    }

    public void setPickupName(String pickupName) {
        this.pickupName = pickupName;
    }

    public ArrayList<Double> getDestination_lat_long() {
        return destination_lat_long;
    }

    public void setDestination_lat_long(ArrayList<Double> destination_lat_long) {
        this.destination_lat_long = destination_lat_long;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }
}
