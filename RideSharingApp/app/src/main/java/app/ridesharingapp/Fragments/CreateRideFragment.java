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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.MainActivity;
import app.ridesharingapp.MapsActivity;
import app.ridesharingapp.Model.Date;
import app.ridesharingapp.Model.Location;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.Time;
import app.ridesharingapp.R;

import static android.app.Activity.RESULT_OK;


public class CreateRideFragment extends Fragment {
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private int START_ADDRESS_REQUEST = 10;
    private int DESTINATION_ADDRESS_REQUEST = 20;
    private MainActivity parentActivity;

    private TextView selectedStartLocationLabel;
    private TextView selectedDestinationLabel;

    private Location startAddress;
    private Location destinationAddress;
    private Date selectedDate;
    private Time selectedTime;

    public CreateRideFragment(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_create_ride, container, false);

        Button selectTimeButton = fragment.findViewById(R.id.selectTime_btn);
        final Button selectDateButton = fragment.findViewById(R.id.selectDate_btn);
        Button selectStartLocationButton = fragment.findViewById(R.id.selectStartLocation_btn);
        Button selectDestinationButton = fragment.findViewById(R.id.selectDestination_btn);
        Button createRideButton = fragment.findViewById(R.id.create_ride);
        FloatingActionButton homeButton = fragment.findViewById(R.id.floating_home_button_cr);

        final TextView selectedTimeLabel = fragment.findViewById(R.id.time_label);
        final TextView selectedDateLabel = fragment.findViewById(R.id.date_label);
        selectedStartLocationLabel = fragment.findViewById(R.id.startLocation_label);
        selectedDestinationLabel = fragment.findViewById(R.id.destination_label);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDateLabel.setText(dayOfMonth + "." + (month + 1) + "." + year);
                        selectedDate = new Date(dayOfMonth, month + 1, year);
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });

        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        selectedTimeLabel.setText(selectedHour + ":" + selectedMinute);
                        selectedTime = new Time(selectedHour, selectedMinute);
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        selectStartLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity(START_ADDRESS_REQUEST);
            }
        });

        selectDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity(DESTINATION_ADDRESS_REQUEST);
            }
        });

        createRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startAddress != null && destinationAddress != null && selectedDate != null && selectedTime != null) {
                    showPassengerNumberPickerDialog();
                } else {
                    Toast.makeText(getContext(), "Please fill all necessary fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.switchToHomeFragment();
            }
        });

        // Inflate the layout for this fragment
        return fragment;
    }

    private void openMapsActivity(int requestCode) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Address selectedLocation = data.getParcelableExtra("location");

        if (selectedLocation != null) {
            if (requestCode == START_ADDRESS_REQUEST && resultCode == RESULT_OK) {
                selectedStartLocationLabel.setText(selectedLocation.getAddressLine(0));
                startAddress = new Location(selectedLocation.getLatitude(), selectedLocation.getLongitude(), selectedLocation.getAddressLine(0));
            } else if (requestCode == DESTINATION_ADDRESS_REQUEST && resultCode == RESULT_OK) {
                selectedDestinationLabel.setText(selectedLocation.getAddressLine(0));
                destinationAddress = new Location(selectedLocation.getLatitude(), selectedLocation.getLongitude(), selectedLocation.getAddressLine(0));
            }
        } else {
            Toast.makeText(getContext(), "No location was selected...", Toast.LENGTH_LONG).show();
        }
    }

    private void showPassengerNumberPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.passenger_number_picker, null);

        final TextView plusButton = view.findViewById(R.id.plus_button);
        final TextView minusButton = view.findViewById(R.id.minus_button);
        final TextView numberOfPassengers = view.findViewById(R.id.passenger_number);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int passengerNumber = Integer.parseInt(numberOfPassengers.getText().toString());
                passengerNumber++;
                numberOfPassengers.setText(passengerNumber + "");
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int passengerNumber = Integer.parseInt(numberOfPassengers.getText().toString());
                if (passengerNumber > 1) {
                    passengerNumber--;
                    numberOfPassengers.setText(passengerNumber + "");
                }
            }
        });

        builder.setCancelable(false)
                .setMessage("Select the number of passengers")
                .setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseManager.addRide(
                                new Ride(
                                        databaseManager.getLoggedUser(),
                                        destinationAddress,
                                        selectedDate,
                                        new Date(1,4,2021)
                                        ));
                        parentActivity.switchToHomeFragment();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
