package app.ridesharingapp.Model;

import android.content.Context;
import android.widget.Toast;

public class Ride {
    private Location startLocation;
    private Location destination;
    private Date date;
    private Time time;
    private int numberOfPassengers;
    private int placesOccupied;

    public Ride(Location startLocation, Location destination, Date date, Time time, int numberOfPassengers) {
        this.startLocation = startLocation;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.numberOfPassengers = numberOfPassengers;
        this.placesOccupied = 0;
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

    public void occupyPlace(Context context){
        if(this.placesOccupied<numberOfPassengers){
            this.placesOccupied++;
        }
        else{
            Toast.makeText(context ,"Ride is full!", Toast.LENGTH_LONG);
        }
    }

    public int getNumberOfAvailablePlaces(){
        return numberOfPassengers - placesOccupied;
    }
}
