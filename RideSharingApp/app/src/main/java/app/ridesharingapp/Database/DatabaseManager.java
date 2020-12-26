package app.ridesharingapp.Database;

import java.util.ArrayList;
import java.util.List;

import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.Model.User;

public class DatabaseManager {
    private static DatabaseManager instance;

    private List<Ride> availableRides;
    private User loggedUser;

    //Clasa asta va comunica cu baza de date si va adauga si citi informatii din baza de date
    private DatabaseManager(){
        availableRides = new ArrayList<>();
        loggedUser = new User("Radu"); //asta va disparea
    }

    public static DatabaseManager getInstance(){
        if(instance==null) instance = new DatabaseManager();

        return instance;
    }

    //metoda asta va adauga in baza de date un ride
    public void addRide(Ride ride){
        availableRides.add(ride);
    }

    //metoda asta va adauga un user in baza de date
    public void addUser(User user){
        loggedUser = user;
    }

    //metoda asta va cauta dupa user in baza de date. Practic cele doua metode se vor folosi pentru LogIn si SignUp
    public void findUser(User user){
        loggedUser = user;
    }

    //metoda asta va returna cursele prezente in baza de date la momentul actual
    public List<Ride> retrieveRides(){
        return availableRides;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
