package app.ridesharingapp.Fragments;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import app.ridesharingapp.Adapters.RidesAdapter;
import app.ridesharingapp.Database.DatabaseManager;
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
        ListView listPayments = fragment.findViewById(R.id.rides_list);

        final RidesAdapter adapter = new RidesAdapter(getContext(), R.layout.ride_details_card, DatabaseManager.getInstance().retreiveRidesForLoggedUser());
        listPayments.setAdapter(adapter);

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

        return fragment;
    }
}
