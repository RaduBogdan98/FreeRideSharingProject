package app.ridesharingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.Fragments.CreateRideFragment;
import app.ridesharingapp.Fragments.HomeFragment;
import app.ridesharingapp.Fragments.SearchRideFragment;
import app.ridesharingapp.Fragments.UserDetailsFragment;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.Services.ApiClient;
import app.ridesharingapp.Utils.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Menu navbarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check internet connection
        this.isConnected(getApplicationContext());

        BottomNavigationView navbar = findViewById(R.id.bottom_navigation);
        Menu navbarMenu = navbar.getMenu();

        MenuItem createRideButton = navbarMenu.findItem(R.id.navigation_create);
        MenuItem searchRideButton = navbarMenu.findItem(R.id.navigation_search);
        MenuItem userDetailsButton = navbarMenu.findItem(R.id.navigation_user);

        createRideButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switchToCreateRideFragment();
                return true;
            }
        });

        searchRideButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switchToSearchRideFragment();
                return true;
            }
        });

        userDetailsButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switchToUserDetailsFragment();
                return true;
            }
        });

        switchToHomeFragment();
    }

    private void switchToCreateRideFragment() {
        CreateRideFragment createRideFragment = new CreateRideFragment(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, createRideFragment)
                .commit();
    }

    private void switchToUserDetailsFragment() {
        UserDetailsFragment createRideFragment = new UserDetailsFragment(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, createRideFragment)
                .commit();
    }

    private void switchToSearchRideFragment() {
        SearchRideFragment searchRideFragment = new SearchRideFragment(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, searchRideFragment)
                .commit();
    }

    public void switchToHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, homeFragment)
                .commit();
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connectivityManager.getAllNetworks();

        for (Network network : networks) {
            if (connectivityManager.getNetworkCapabilities(network).hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || connectivityManager.getNetworkCapabilities(network).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true;
            }
        }

        showConnectToInternetDialog();
        return false;
    }

    private void showConnectToInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please connect to the internet")
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isConnected(getApplicationContext());
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}