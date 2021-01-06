package app.ridesharingapp.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return latLng.latitude == location.latLng.latitude &&
                latLng.longitude == location.latLng.longitude &&
                locationName.equals(location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latLng, locationName);
    }
}
