package app.ridesharingapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.ridesharingapp.Model.User;
import app.ridesharingapp.R;

public class PassengersAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> passengers;
    private int layoutResID;

    public PassengersAdapter(Context context, int layoutResourceID, List<User> passengers) {
        super(context, layoutResourceID, passengers);
        this.context = context;
        this.passengers = passengers;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PassengersAdapter.ItemHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new PassengersAdapter.ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.passengerName = (TextView) view.findViewById(R.id.passenger_name);

            view.setTag(itemHolder);

        } else {
            itemHolder = (PassengersAdapter.ItemHolder) view.getTag();
        }

        final User passenger = this.passengers.get(position);

        if (itemHolder != null){
            if(passenger !=null){
                itemHolder.passengerName.setText(passenger.getCompleteName());
            }
        }

        return view;
    }

    private static class ItemHolder {
        TextView passengerName;
    }
}
