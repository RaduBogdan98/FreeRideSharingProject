package app.ridesharingapp.Model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Ride {
    private User driver;
    private List<User> clients;
    private Car car;
    private Location pickupPoint;
    private Location destination;
    private Date departureTime;
    private Date arrivalTime;
    private String status;
    private String distance;
    private String avgSpeed;
    private Integer numberOfPassengers;

    public Ride(User driver, Location destination, Date departureTime, Date arrivalTime) {
        this.driver = driver;
        this.clients = new ArrayList<>();
        this.car = driver.getCars().get(0);
        this.pickupPoint = driver.getLocation();
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = "pending";
        this.distance = "10km";
        this.avgSpeed = "50 km/h";
        this.numberOfPassengers = this.clients.size();
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public List<User> getClients() {
        return clients;
    }

    public void setClients(List<User> clients) {
        this.clients = clients;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Location getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(Location pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(String avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
