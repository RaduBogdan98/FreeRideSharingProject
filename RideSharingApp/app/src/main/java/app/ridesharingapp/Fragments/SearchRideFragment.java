package app.ridesharingapp.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import app.ridesharingapp.Adapters.PassengersAdapter;
import app.ridesharingapp.Adapters.RidesAdapter;
import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.LoginActivity;
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
    RidesAdapter adapter;

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
                        date = new Date(dayOfMonth, month + 1, year);
                        dateText.setText(date.toString());
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
                        time = new Time(selectedHour, selectedMinute);
                        timeText.setText(time.toString());
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        ListView foundRidesList = fragment.findViewById(R.id.found_rides_list);

        adapter = new RidesAdapter(getContext(), R.layout.ride_details_card, foundRides);
        foundRidesList.setAdapter(adapter);

        foundRidesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride clickedRide = adapter.getRides().get(position);
                startRideDetailsDialog(clickedRide);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startLocation != null && destination != null && date != null && time != null) {
                    if(startLocation.equals(destination)){
                        Toast.makeText(getContext(), "Pickup point and destination match!", Toast.LENGTH_SHORT).show();
                    }
                    else if(!date.isCorrect() || !time.isCorrect()){
                        Toast.makeText(getContext(), "Selected date or time are incorrect!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        displayFoundRides();
                    }
                } else {
                    Toast.makeText(getContext(), "Please fill all necessary fields correctly!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Home button handling
        FloatingActionButton homeButton = fragment.findViewById(R.id.floating_home_button_search);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.switchToHomeFragment();
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

    private void displayFoundRides() {
        List<Ride> rides = DatabaseManager
                .getInstance()
                .retrieveRides()
                .stream()
                .filter((ride) ->
                        ride.getDepartureDate().equals(date) &&

                                ride.getNumberOfPassengers() != 0 &&

                                ride.getDepartureTime().greaterOrEqual(time) &&

                                calculateDistanceInKilometers(ride.getPickupPoint().getLatLng().latitude,
                                        ride.getPickupPoint().getLatLng().longitude,
                                        startLocation.getLatLng().latitude,
                                        startLocation.getLatLng().longitude) < 3 &&

                                calculateDistanceInKilometers(ride.getDestination().getLatLng().latitude,
                                        ride.getDestination().getLatLng().longitude,
                                        destination.getLatLng().latitude,
                                        destination.getLatLng().longitude) < 3

                                //&& !ride.getDriver().equals(DatabaseManager.getInstance().getLoggedUser())

                                && !ride.getClients().contains(DatabaseManager.getInstance().getLoggedUser())
                )
                .collect(Collectors.toList());

        foundRides.clear();

        if (rides.isEmpty()) {
            Toast.makeText(getContext(), "No rides found!", Toast.LENGTH_SHORT).show();
        } else {
            foundRides.addAll(rides);
            adapter.notifyDataSetChanged();
        }
    }

    private void openMapsActivity(int requestCode) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    public double calculateDistanceInKilometers(double lat1, double lng1,
                                                double lat2, double lng2) {

        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(lng1 - lng2);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH_KM * c;
    }

    private void startRideDetailsDialog(Ride ride) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ride_card_details_dialog, null);

        TextView startLocation = (TextView) view.findViewById(R.id.ride_startLocation);
        TextView destination = (TextView) view.findViewById(R.id.ride_destination);
        TextView date = (TextView) view.findViewById(R.id.ride_date);
        TextView time = (TextView) view.findViewById(R.id.ride_time);
        TextView availablePlaces = (TextView) view.findViewById(R.id.ride_available_places);
        TextView driverName = (TextView) view.findViewById(R.id.ride_driver_name);

        startLocation.setText(ride.getPickupPoint().getLocationName());
        destination.setText(ride.getDestination().getLocationName());
        date.setText(ride.getDepartureDate().toString());
        time.setText(ride.getDepartureTime().toString());
        availablePlaces.setText(ride.getNumberOfPassengers() + "");
        driverName.setText(ride.getDriver().getName());

        ListView passengerList = view.findViewById(R.id.ride_passenger_list);

        final PassengersAdapter adapter = new PassengersAdapter(getContext(), R.layout.ride_details_card, ride.getClients());
        passengerList.setAdapter(adapter);

        builder.setCancelable(true)
                .setView(view)
                .setPositiveButton("Join Ride", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ride.getClients().add(DatabaseManager.getInstance().getLoggedUser());
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }
}