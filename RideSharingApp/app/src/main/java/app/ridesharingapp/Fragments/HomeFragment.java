package app.ridesharingapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

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

        ListView listPayments = (ListView) fragment.findViewById(R.id.rides_list);

        final RidesAdapter adapter = new RidesAdapter(getContext(), R.layout.ride_details_card, DatabaseManager.getInstance().retrieveRides());
        listPayments.setAdapter(adapter);

        return fragment;
    }
}
