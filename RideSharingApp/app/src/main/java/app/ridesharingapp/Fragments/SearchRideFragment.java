package app.ridesharingapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import app.ridesharingapp.Adapters.RidesAdapter;
import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.MainActivity;
import app.ridesharingapp.Model.Date;
import app.ridesharingapp.Model.Location;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.Time;
import app.ridesharingapp.R;

public class SearchRideFragment extends Fragment {
    private MainActivity parentActivity;
    private List<Ride> foundRides;
    private Location startLocation;
    private Location destination;
    private Date date;
    private Time time;

    public SearchRideFragment(MainActivity parentAcrivity) {
        this.parentActivity = parentAcrivity;
        foundRides = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_search_ride, container, false);

        TextView startText = fragment.findViewById(R.id.start_location_text);
        TextView destinationText = fragment.findViewById(R.id.destination_text);
        TextView dateText = fragment.findViewById(R.id.date_text);
        TextView timeText = fragment.findViewById(R.id.time_text);

        Button searchStartLocationButton = fragment.findViewById(R.id.search_start_location_button);
        Button searchDestinationButton = fragment.findViewById(R.id.search_destination_button);
        Button searchDateButton = fragment.findViewById(R.id.search_date_button);
        Button searchTimeButton = fragment.findViewById(R.id.search_time_button);
        Button searchButton = fragment.findViewById(R.id.search_ride_button);

        ListView foundRidesList = fragment.findViewById(R.id.found_rides_list);

        final RidesAdapter adapter = new RidesAdapter(getContext(), R.layout.ride_details_card, foundRides);
        foundRidesList.setAdapter(adapter);

        return fragment;
    }
}