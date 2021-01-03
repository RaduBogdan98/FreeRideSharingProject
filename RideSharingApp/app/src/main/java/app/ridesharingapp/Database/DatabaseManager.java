package app.ridesharingapp.Database;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.ridesharingapp.MainActivity;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.SignUpActivity;

public class DatabaseManager {
    private static DatabaseManager instance;

    private List<Ride> availableRides;
    private User loggedUser;

    //Clasa asta va comunica cu baza de date si va adauga si citi informatii din baza de date
    private DatabaseManager() {
        availableRides = new ArrayList<>();
        //loggedUser = new User("Radu"); //asta va disparea
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();

        return instance;
    }

    //metoda asta va adauga in baza de date un ride
    public void addRide(Ride ride) {
        availableRides.add(ride);
    }

    //metoda asta va adauga un user in baza de date
    public boolean addUser(String name, String email, String password, String phoneNumber, int age) {
        if (name.length()!=0
                && email.length()!=0 && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length()!=0
                && phoneNumber.length() == 10
                && age > 18 && age < 150) {

            loggedUser = new User(name, email, password, phoneNumber, age);
            return true;
        }

        return false;
    }

    //metoda asta va cauta dupa user in baza de date. Practic cele doua metode se vor folosi pentru LogIn si SignUp
    public boolean findUser(String email, String password) {
        String standardEmail = "radu";
        String standardPassword = "password";

        if (standardEmail.equals(email) && standardPassword.equals(password)) {
            loggedUser = new User("Radu", email, password, "0720000111", 22);

            return true;
        }

        return false;
    }

    //metoda asta va returna cursele prezente in baza de date la momentul actual
    public List<Ride> retrieveRides() {
        return availableRides;
    }

    public List<Ride> retreiveRidesForLoggedUser(){
        return availableRides
                .stream()
                .filter((ride) -> ride.getOwner().equals(loggedUser) || ride.getPassengers().contains(loggedUser))
                .collect(Collectors.toList());
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
