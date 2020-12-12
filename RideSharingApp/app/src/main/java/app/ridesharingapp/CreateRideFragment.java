package app.ridesharingapp;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class CreateRideFragment extends Fragment{
    private int START_ADDRESS_REQUEST = 10;
    private int DESTINATION_ADDRESS_REQUEST = 20;

    private TextView selectedStartLocationLabel;
    private TextView selectedDestinationLabel;
    private Address startAddress;
    private Address destinationAddress;

    public CreateRideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_create_ride, container, false);

        Button selectTimeButton = fragment.findViewById(R.id.selectTime_btn);
        Button selectDateButton = fragment.findViewById(R.id.selectDate_btn);
        Button selectStartLocationButton = fragment.findViewById(R.id.selectStartLocation_btn);
        Button selectDestinationButton = fragment.findViewById(R.id.selectDestination_btn);

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
                        selectedDateLabel.setText(dayOfMonth+"."+(month+1)+"."+year);
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener , Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
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
                        selectedTimeLabel.setText( selectedHour + ":" + selectedMinute);
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

        // Inflate the layout for this fragment
        return fragment;
    }

    private void openMapsActivity(int requestCode){
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Address selectedLocation = data.getParcelableExtra("location");

        if(selectedLocation!=null){
            if(requestCode == START_ADDRESS_REQUEST && resultCode == RESULT_OK){
                selectedStartLocationLabel.setText(selectedLocation.getAddressLine(0));
                startAddress = selectedLocation;
            }
            else if(requestCode == DESTINATION_ADDRESS_REQUEST && resultCode == RESULT_OK){
                selectedDestinationLabel.setText(selectedLocation.getAddressLine(0));
                destinationAddress=selectedLocation;
            }
        }
        else {
            Toast.makeText(getContext(),"No location was selected...", Toast.LENGTH_LONG);
        }
    }
}
