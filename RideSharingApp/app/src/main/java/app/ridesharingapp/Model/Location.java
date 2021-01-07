package app.ridesharingapp.Model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Location {
    private double latitude;
    private double longitude;
    private String locationName;

    public Location(double latitude, double longitude, String locationName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    public double getLatitude(Context context) {
        if (latitude == 0){
            try {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                Address address = geocoder.getFromLocationName(locationName, 1).get(0);
                this.latitude = address.getLatitude();
                this.longitude = address.getLongitude();
            } catch (IOException e) {

            }
        }

        return latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude(Context context) {
        if (longitude == 0){
            try {
                Geocoder geocoder = new Geocoder(null, Locale.getDefault());
                Address address = geocoder.getFromLocationName(locationName, 1).get(0);
                this.latitude = address.getLatitude();
                this.longitude = address.getLongitude();
            } catch (IOException e) {

            }
        }

        return longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return latitude == location.latitude &&
                longitude == location.longitude &&
                locationName.equals(location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, locationName);
    }
}
