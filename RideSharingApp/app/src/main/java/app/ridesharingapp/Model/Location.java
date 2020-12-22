package app.ridesharingapp.Model;

import com.google.android.gms.maps.model.LatLng;

public class Location {
    private LatLng latLng;
    private String locationName;

    public Location(double latitude, double longitude, String locationName) {
        this.latLng = new LatLng(latitude, longitude);
        this.locationName = locationName;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getLocationName() {
        return locationName;
    }
}
