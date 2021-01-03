package app.ridesharingapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import app.ridesharingapp.Adapters.CarsAdapter;
import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.LayoutElements.NonScrollListView;
import app.ridesharingapp.MainActivity;
import app.ridesharingapp.Model.Car;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.R;

public class UserDetailsFragment extends Fragment {
    private MainActivity parentActivity;

    public UserDetailsFragment(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_user_details, container, false);

        //User details area handling
        EditText nameEdit = fragment.findViewById(R.id.user_name);
        EditText ageEdit = fragment.findViewById(R.id.user_age);
        EditText emailEdit = fragment.findViewById(R.id.user_email);
        EditText phoneEdit = fragment.findViewById(R.id.user_phone);
        EditText licenseEdit = fragment.findViewById(R.id.user_licenseID);
        EditText addressEdit = fragment.findViewById(R.id.user_address);

        final User loggedUser = DatabaseManager.getInstance().getLoggedUser();

        nameEdit.setText(loggedUser.getName());
        ageEdit.setText(loggedUser.getAge() + "");
        emailEdit.setText(loggedUser.getEmail());
        phoneEdit.setText(loggedUser.getPhoneNumber());
        licenseEdit.setText(loggedUser.getLicenseId());
        addressEdit.setText(loggedUser.getAddress());

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ageEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setAge(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setPhoneNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        licenseEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setLicenseId(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addressEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Car details area handling
        Button addCarButton = fragment.findViewById(R.id.addCarButton);

        NonScrollListView listPayments = fragment.findViewById(R.id.cars_list);

        final CarsAdapter adapter = new CarsAdapter(getContext(), R.layout.car_details_card, DatabaseManager.getInstance().getLoggedUser().getCars());
        listPayments.setAdapter(adapter);

        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCarCreatorDialog();
            }
        });

        //Home button handling
        FloatingActionButton homeButton = fragment.findViewById(R.id.floating_home_button_user);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.switchToHomeFragment();
            }
        });

        return fragment;
    }

    private void showCarCreatorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.car_details_setter_dialog, null);

        final EditText manufacturerName = view.findViewById(R.id.dialog_manufacturer_setter);
        final EditText modelName = view.findViewById(R.id.dialog_model_setter);
        final EditText yearOfManufacturing = view.findViewById(R.id.dialog_year_setter);
        final EditText colorName = view.findViewById(R.id.dialog_color_setter);
        final Spinner fuelType = view.findViewById(R.id.dialog_fuel_setter);

        builder.setCancelable(false)
                .setMessage("Set the car parameters")
                .setView(view)
                .setPositiveButton("Add Car", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });


        final AlertDialog alert = builder.create();
        alert.show();

        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manufacturer = manufacturerName.getText().toString().trim();
                String model = modelName.getText().toString().trim();
                String year = yearOfManufacturing.getText().toString().trim();
                String color = colorName.getText().toString().trim();
                String fuel = fuelType.getSelectedItem().toString().trim();

                if ((manufacturer.length() != 0) &&
                        (model.length() != 0) &&
                        (year.length() == 4 && (year.startsWith("19") || year.startsWith("20"))) &&
                        (color.length() != 0)) {

                    Car car = new Car(manufacturer, model, year, fuel, color);
                    DatabaseManager.getInstance().getLoggedUser().addCar(car);
                    alert.dismiss();
                } else {
                    Toast.makeText(getContext(), "Please fill all the necessary fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
