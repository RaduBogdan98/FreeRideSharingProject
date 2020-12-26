package app.ridesharingapp.Model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Ride {
    private Location startLocation;
    private Location destination;
    private Date date;
    private Time time;
    private int numberOfPassengers;
    private User owner;
    private List<User> passengers;

    public Ride(User owner, Location startLocation, Location destination, Date date, Time time, int numberOfPassengers) {
        this.owner = owner;
        this.startLocation = startLocation;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.numberOfPassengers = numberOfPassengers;
        passengers = new ArrayList<>(numberOfPassengers);
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void occupyPlace(Context context, User user) {
        if (this.passengers.size() < numberOfPassengers) {
            this.passengers.add(user);
        } else {
            Toast.makeText(context, "Ride is full!", Toast.LENGTH_LONG);
        }
    }

    public int getNumberOfAvailablePlaces() {
        return numberOfPassengers - this.passengers.size();
    }

    public User getOwner() {
        return owner;
    }

    public List<User> getPassengers() {
        return passengers;
    }
}
