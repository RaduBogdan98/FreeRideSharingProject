package app.ridesharingapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import app.ridesharingapp.Adapters.PassengersAdapter;
import app.ridesharingapp.Adapters.RidesAdapter;
import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.R;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView noRidesText = fragment.findViewById(R.id.no_rides_text);
        ListView listRides = fragment.findViewById(R.id.rides_list);

        final RidesAdapter adapter = new RidesAdapter(getContext(), R.layout.ride_details_card, DatabaseManager.getInstance().retreiveRidesForLoggedUser());
        listRides.setAdapter(adapter);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if(!adapter.getRides().isEmpty()){
                    noRidesText.setVisibility(View.INVISIBLE);
                }
                else{
                    noRidesText.setVisibility(View.VISIBLE);
                }
            }
        });

        listRides.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride clickedRide = adapter.getRides().get(position);
                startRideDetailsDialog(clickedRide);
            }
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
        time.setText(ride.getDepartureTime().toString());
        availablePlaces.setText(ride.getNumberOfPassengers()+"");
        driverName.setText(ride.getDriver().getName());

        ListView passengerList = view.findViewById(R.id.ride_passenger_list);

        final PassengersAdapter adapter = new PassengersAdapter(getContext(), R.layout.ride_details_card, ride.getClients());
        passengerList.setAdapter(adapter);

        if(ride.getDriver().equals(DatabaseManager.getInstance().getLoggedUser())){
            builder.setCancelable(false)
                    .setView(view)
                    .setPositiveButton("Remove Ride", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DatabaseManager.getInstance().retrieveRides().remove(ride);
                        }
                    });
        }
        else{
            builder.setCancelable(false)
                    .setView(view)
                    .setNegativeButton("Leave Ride", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ride.getClients().remove(DatabaseManager.getInstance().getLoggedUser());
                        }
                    });
        }


        final AlertDialog alert = builder.create();
        alert.show();
    }
}
