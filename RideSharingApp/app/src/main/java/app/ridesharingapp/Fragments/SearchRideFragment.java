package app.ridesharingapp.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.ridesharingapp.Adapters.RidesAdapter;
import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.MainActivity;
import app.ridesharingapp.MapsActivity;
import app.ridesharingapp.Model.Date;
import app.ridesharingapp.Model.Location;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.Time;
import app.ridesharingapp.R;

import static android.app.Activity.RESULT_OK;

public class SearchRideFragment extends Fragment {
    private MainActivity parentActivity;
    private List<Ride> foundRides;
    private Location startLocation;
    private Location destination;
    private Date date;
    private Time time;

    private int START_ADDRESS_REQUEST = 110;
    private int DESTINATION_ADDRESS_REQUEST = 120;

    TextView startText;
    TextView destinationText;
    TextView dateText;
    TextView timeText;

    public SearchRideFragment(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
        foundRides = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_search_ride, container, false);

        startText = fragment.findViewById(R.id.start_location_text);
        destinationText = fragment.findViewById(R.id.destination_text);
        dateText = fragment.findViewById(R.id.date_text);
        timeText = fragment.findViewById(R.id.time_text);

        Button searchStartLocationButton = fragment.findViewById(R.id.search_start_location_button);
        Button searchDestinationButton = fragment.findViewById(R.id.search_destination_button);
        Button searchDateButton = fragment.findViewById(R.id.search_date_button);
        Button searchTimeButton = fragment.findViewById(R.id.search_time_button);
        Button searchButton = fragment.findViewById(R.id.search_ride_button);

        searchStartLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity(START_ADDRESS_REQUEST);
            }
        });

        searchDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity(DESTINATION_ADDRESS_REQUEST);
            }
        });

        searchDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateText.setText(dayOfMonth + "." + (month + 1) + "." + year);
                        date = new Date(dayOfMonth, month + 1, year);
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });

        searchTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeText.setText(selectedHour + ":" + selectedMinute);
                        time = new Time(selectedHour, selectedMinute);
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        ListView foundRidesList = fragment.findViewById(R.id.found_rides_list);

        final RidesAdapter adapter = new RidesAdapter(getContext(), R.layout.ride_details_card, foundRides);
        foundRidesList.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foundRides.clear();
                foundRides.addAll(DatabaseManager.getInstance().retrieveRides());
                adapter.notifyDataSetChanged();
            }
        });

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Address selectedLocation = data.getParcelableExtra("location");

        if (selectedLocation != null) {
            if (requestCode == START_ADDRESS_REQUEST && resultCode == RESULT_OK) {
                startText.setText(selectedLocation.getAddressLine(0));
                startLocation = new Location(selectedLocation.getLatitude(), selectedLocation.getLongitude(), selectedLocation.getAddressLine(0));
            } else if (requestCode == DESTINATION_ADDRESS_REQUEST && resultCode == RESULT_OK) {
                destinationText.setText(selectedLocation.getAddressLine(0));
                destination = new Location(selectedLocation.getLatitude(), selectedLocation.getLongitude(), selectedLocation.getAddressLine(0));
            }
        } else {
            Toast.makeText(getContext(), "No location was selected...", Toast.LENGTH_LONG).show();
        }
    }

    private void openMapsActivity(int requestCode) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, requestCode);
    }
}