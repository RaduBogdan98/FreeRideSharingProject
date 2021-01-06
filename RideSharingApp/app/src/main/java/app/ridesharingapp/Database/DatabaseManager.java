package app.ridesharingapp.Database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import app.ridesharingapp.LoginActivity;
import app.ridesharingapp.MainActivity;
import app.ridesharingapp.Model.Requests.AddCarRequest;
import app.ridesharingapp.Model.Requests.AddRideRequest;
import app.ridesharingapp.Model.Requests.JoinRideRequest;
import app.ridesharingapp.Model.Requests.LoginRequest;
import app.ridesharingapp.Model.Requests.RegisterRequest;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.Services.ApiClient;
import app.ridesharingapp.Utils.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseManager {
    private static DatabaseManager instance;

    private ArrayList<Ride> availableRides;
    private User loggedUser;

    private DatabaseManager() {
        availableRides = new ArrayList<>();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();

        return instance;
    }

    public void signUpUser(Context context, String email, String password, String name, String surname, int age) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setName(name);
        registerRequest.setPassword(password);
        registerRequest.setSurname(surname);
        registerRequest.setAge(age);

        Call<User> registerResponseCall = ApiClient.getUserService().userRegister(registerRequest);
        registerResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
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
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        Call<User> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(() -> {
                        SharedPreferenceUtil.saveEmail(email, context);
                        SharedPreferenceUtil.savePassword(password, context);
                        loggedUser = response.body();
                        System.out.println(loggedUser);
                        System.out.println(loggedUser.getCars());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }, 300);
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

    public void addCar(Context context, AddCarRequest addCarRequest, AlertDialog alert){
        Call<User> userCall = ApiClient.getUserService().addCar(addCarRequest);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(context, "Car added successfully!", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Add car error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addRide(Context context,Ride ride) {
        AddRideRequest rideRequest = new AddRideRequest();
        rideRequest.setDriver(ride.getDriver().getEmail());
        rideRequest.setAvailablePlaces(ride.getNumberOfPassengers());
        rideRequest.setCarName(ride.getCar().getPlate());
        rideRequest.setDepartureDate(ride.getDepartureDate());
        rideRequest.setDepartureTime(ride.getDepartureTime().toString());

        ArrayList<Double> pickup = new ArrayList<>();
        pickup.add(ride.getPickupPoint().getLatLng().latitude);
        pickup.add(ride.getPickupPoint().getLatLng().longitude);
        ArrayList<Double> destination = new ArrayList<>();
        destination.add(ride.getDestination().getLatLng().latitude);
        destination.add(ride.getDestination().getLatLng().longitude);

        rideRequest.setPickupName(ride.getPickupPoint().getLocationName());
        rideRequest.setDestinationName(ride.getDestination().getLocationName());

        rideRequest.setPickup_lat_long(pickup);
        rideRequest.setDestination_lat_long(destination);

        Call<Ride> rideCall = ApiClient.getUserService().createRide(rideRequest);

        rideCall.enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                Toast.makeText(context, "Add ride error! " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                availableRides.remove(ride);
            }
        });
        availableRides.add(ride);
    }

    public void removeRide(Context context,Ride ride) {
        Call<Ride> rideCall = ApiClient.getUserService().deleteRide(ride.get_id());
        rideCall.enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                Toast.makeText(context, "MERE delete ride! ", Toast.LENGTH_LONG).show();
                availableRides.remove(ride);
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                Toast.makeText(context, "NU MERE delete ride! " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllRides(Context context){
        Call<ArrayList<Ride>> listCall = ApiClient.getUserService().getAllRides();
        listCall.enqueue(new Callback<ArrayList<Ride>>() {
            @Override
            public void onResponse(Call<ArrayList<Ride>> call, Response<ArrayList<Ride>> response) {
                assert response.body() != null;
                availableRides = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<Ride>> call, Throwable t) {
                Toast.makeText(context, "NU MERE! " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public List<Ride> retrieveRides() {
        return availableRides;
    }

    public List<Ride> retreiveRidesForLoggedUser(Context context) {
//        this.getAllRides(context);
        return availableRides
                .stream()
                .filter((ride) -> ride.getDriver().getName().equals(loggedUser.getName()) || ride.getClients().stream().map(User::getName).collect(Collectors.toList()).contains(loggedUser.getName()))
                .collect(Collectors.toList());
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void joinRide(Context context, String rideID){
        JoinRideRequest joinRideRequest = new JoinRideRequest();
        joinRideRequest.setUserEmail(this.loggedUser.getEmail());
        Call<Ride> rideCall = ApiClient.getUserService().joinRide(rideID,joinRideRequest);
        rideCall.enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                Toast.makeText(context, "Joined successfully ", Toast.LENGTH_LONG).show();
                getAllRides(context);
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                Toast.makeText(context, "Failed join ride! " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void leaveRide(Context context,String rideID){
        JoinRideRequest joinRideRequest = new JoinRideRequest();
        joinRideRequest.setUserEmail(loggedUser.getEmail());

        Call<Ride> rideCall = ApiClient.getUserService().leaveRide(rideID,joinRideRequest);
        rideCall.enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                Toast.makeText(context, "LEft successfully ", Toast.LENGTH_LONG).show();
                getAllRides(context);
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                Toast.makeText(context, "Failed leave ride! " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
