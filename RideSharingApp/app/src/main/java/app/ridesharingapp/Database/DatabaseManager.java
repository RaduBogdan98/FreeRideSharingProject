package app.ridesharingapp.Database;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.ridesharingapp.LoginActivity;
import app.ridesharingapp.MainActivity;
import app.ridesharingapp.Model.Car;
import app.ridesharingapp.Model.Location;
import app.ridesharingapp.Model.Requests.LoginRequest;
import app.ridesharingapp.Model.Requests.RegisterRequest;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.Services.ApiClient;
import app.ridesharingapp.SignUpActivity;
import app.ridesharingapp.Utils.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseManager {
    private static DatabaseManager instance;

    private List<Ride> availableRides;
    private User loggedUser;

    private DatabaseManager() {
        availableRides = new ArrayList<>();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();

        return instance;
    }

    public void addRide(Ride ride) {
        availableRides.add(ride);
    }

    public void signUpUser(Context context, String email, String password, String name, String surname, int age) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setName(name);
        registerRequest.setPassword(password);
        registerRequest.setSurname(surname);
        registerRequest.setAge(age);
        registerRequest.setRole("client");

        Call<User> loginResponseCall = ApiClient.getUserService().userRegister(registerRequest);
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }, 300);
                } else {
                    Toast.makeText(context, "Register Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginUser(Context context, String email, String password) {
//        List<Car> cars = new ArrayList<>();
//        cars.add(new Car("Skoda","Fabia","2002","HD 19 ABC", "Petrol", "silver"));
//
//        loggedUser = new User("Cotisel", "Radu",email, "02020202",password,"","",22, cars);
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        Call<User> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferenceUtil.saveEmail(email, context);
                            SharedPreferenceUtil.savePassword(password, context);
                            loggedUser = response.body();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }, 500);
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<Ride> retrieveRides() {
        return availableRides;
    }

    public List<Ride> retreiveRidesForLoggedUser() {
        return availableRides
                .stream()
                .filter((ride) -> ride.getDriver().equals(loggedUser) || ride.getClients().contains(loggedUser))
                .collect(Collectors.toList());
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
