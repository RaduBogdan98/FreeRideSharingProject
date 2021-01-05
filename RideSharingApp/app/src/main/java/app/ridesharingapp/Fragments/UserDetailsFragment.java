package app.ridesharingapp.Fragments;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.ridesharingapp.Adapters.CarsAdapter;
import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.LayoutElements.NonScrollListView;
import app.ridesharingapp.MainActivity;
import app.ridesharingapp.Model.Car;
import app.ridesharingapp.Model.Requests.AddCarRequest;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.R;
import app.ridesharingapp.Services.ApiClient;
import app.ridesharingapp.Utils.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsFragment extends Fragment {
    private MainActivity parentActivity;

    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private User loggedUser;

    public UserDetailsFragment(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
        this.loggedUser = databaseManager.getLoggedUser();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_user_details, container, false);

        //User details area handling
        EditText nameEdit = fragment.findViewById(R.id.user_name);
        EditText surnameEdit = fragment.findViewById(R.id.user_surname);
        EditText emailEdit = fragment.findViewById(R.id.user_email);
        EditText phoneEdit = fragment.findViewById(R.id.user_phone);
        EditText addressEdit = fragment.findViewById(R.id.user_address);
        EditText CIDEdit = fragment.findViewById(R.id.user_CID);
        EditText ageEdit = fragment.findViewById(R.id.user_age);

        nameEdit.setText(loggedUser.getName());
        surnameEdit.setText(loggedUser.getSurname());
        emailEdit.setText(loggedUser.getEmail());
        phoneEdit.setText(loggedUser.getPhoneNumber());
        CIDEdit.setText(loggedUser.getCid());
        addressEdit.setText(loggedUser.getAddress());
        ageEdit.setText("0");

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

        surnameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setSurname(s.toString());
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

        CIDEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loggedUser.setCid(s.toString());
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
        NonScrollListView listCars = fragment.findViewById(R.id.cars_list);

        final CarsAdapter adapter = new CarsAdapter(getContext(), R.layout.car_details_card, databaseManager.getLoggedUser().getCars());
        listCars.setAdapter(adapter);

        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCarCreatorDialog();
            }
        });

        Button updateProfileButton = fragment.findViewById(R.id.updateUser);
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
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
    private void updateProfile(){
        Call<User> updateCall = ApiClient.getUserService().updateUser(SharedPreferenceUtil.getEmail(getContext()),databaseManager.getLoggedUser());
        updateCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getContext(), "Update Success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Update error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showCarCreatorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.car_details_setter_dialog, null);

        final EditText manufacturerName = view.findViewById(R.id.dialog_manufacturer_setter);
        final EditText modelName = view.findViewById(R.id.dialog_model_setter);
        final EditText yearOfManufacturing = view.findViewById(R.id.dialog_year_setter);
        final EditText colorName = view.findViewById(R.id.dialog_color_setter);
        final EditText plate = view.findViewById(R.id.dialog_plate_setter);
        final Spinner fuelType = view.findViewById(R.id.dialog_fuel_setter);

        builder.setCancelable(true)
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
                String _plate = plate.getText().toString().trim();


                if ((manufacturer.length() != 0) &&
                        (model.length() != 0) &&
                        (year.length() == 4 && (year.startsWith("19") || year.startsWith("20"))) &&
                        (color.length() != 0)) {


                    String Email = SharedPreferenceUtil.getEmail(getContext());
                    String fullModel = manufacturer + " " + model;
                    Car car = new Car(Email,fullModel,year,_plate,fuel,color);
                    databaseManager.getLoggedUser().addCar(car);


                    AddCarRequest addCarRequest = new AddCarRequest(Email,fullModel,year,_plate,color,fuel);
                    Call<User> userCall = ApiClient.getUserService().addCar(addCarRequest);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Toast.makeText(getContext(), "Car added successfully!", Toast.LENGTH_SHORT).show();
                            alert.dismiss();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getContext(), "Add car error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Please fill all the necessary fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
