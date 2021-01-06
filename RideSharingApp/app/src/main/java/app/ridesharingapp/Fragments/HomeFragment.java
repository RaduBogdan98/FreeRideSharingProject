package app.ridesharingapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import app.ridesharingapp.Adapters.PassengersAdapter;
import app.ridesharingapp.Adapters.RidesAdapter;
import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.LayoutElements.NonScrollListView;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.R;

public class HomeFragment extends Fragment {
    private RidesAdapter ridesAdapter;
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_home, container, false);

        ListView listRides = fragment.findViewById(R.id.rides_list);

        ridesAdapter = new RidesAdapter(getContext(), R.layout.ride_details_card, databaseManager.retreiveRidesForLoggedUser(getContext()));
        listRides.setAdapter(ridesAdapter);

        listRides.setOnItemClickListener((parent, view, position, id) -> {
            Ride clickedRide = databaseManager.retreiveRidesForLoggedUser(getContext()).get(position);
            startRideDetailsDialog(clickedRide);
        });

        return fragment;
    }

    private void startRideDetailsDialog(Ride ride){
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
        time.setText(ride.getDepartureTime());
        availablePlaces.setText(ride.getNumberOfPassengers()+"");
        driverName.setText(ride.getDriver().getName());

        NonScrollListView passengerList = view.findViewById(R.id.ride_passenger_list);

        final PassengersAdapter adapter = new PassengersAdapter(getContext(), R.layout.passenger_details, ride.getClients());
        passengerList.setAdapter(adapter);

        if(ride.getDriver().getName().equals(databaseManager.getLoggedUser().getName())){
            builder.setCancelable(true)
                    .setView(view)
                    .setPositiveButton("Remove Ride", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            databaseManager.removeRide(getContext(),ride);
                            ridesAdapter.remove(ride);
                        }
                    });
        }
        else{
            builder.setCancelable(true)
                    .setView(view)
                    .setNegativeButton("Leave Ride", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            databaseManager.leaveRide(getContext(),ride.get_id());
                            ride.removeClient(databaseManager.getLoggedUser());
                            ridesAdapter.notifyDataSetChanged();
                        }
                    });
        }

        final AlertDialog alert = builder.create();
        alert.show();
    }
}
