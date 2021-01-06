package app.ridesharingapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Ride {
    private String _id;
    private User driver;
    private List<User> clients;
    private Car car;
    private Location pickupPoint;
    private Location destination;
    private Date departureDate;
    private Time departureTime;
    private int availablePlaces;

    public Ride(User driver, Location pickupPoint, Location destination, Date departureDate, Time departureTime, int availablePlaces) {
        this.driver = driver;
        this.clients = new ArrayList<>();
        this.car = driver.getCars().get(0);
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.availablePlaces = availablePlaces;
    }


    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getNumberOfPassengers() {
        return availablePlaces - clients.size();
    }
}
