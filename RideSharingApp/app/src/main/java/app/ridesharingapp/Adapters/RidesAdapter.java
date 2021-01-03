package app.ridesharingapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.ridesharingapp.Model.Ride;
import app.ridesharingapp.R;

public class RidesAdapter extends ArrayAdapter<Ride> {
    private Context context;
    private List<Ride> rides;
    private int layoutResID;

    public RidesAdapter(Context context, int layoutResourceID, List<Ride> rides) {
        super(context, layoutResourceID, rides);
        this.context = context;
        this.rides = rides;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.tStartLocation = (TextView) view.findViewById(R.id.card_startLocation);
            itemHolder.tDestination = (TextView) view.findViewById(R.id.card_destination);
            itemHolder.tDate = (TextView) view.findViewById(R.id.card_date);
            itemHolder.tTime = (TextView) view.findViewById(R.id.card_time);
            itemHolder.tPlaces = (TextView) view.findViewById(R.id.card_places);
            itemHolder.tDriverName = (TextView) view.findViewById(R.id.card_driver_name);
            itemHolder.tDriverIco = (ImageView) view.findViewById(R.id.card_driver_ico);

            view.setTag(itemHolder);

        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        final Ride ride = rides.get(position);

        itemHolder.tStartLocation.setText(ride.getStartLocation().getLocationName());
        itemHolder.tDestination.setText(ride.getDestination().getLocationName());
        itemHolder.tDate.setText(ride.getDate().toString());
        itemHolder.tTime.setText(ride.getTime().toString());
        itemHolder.tPlaces.setText(ride.getNumberOfAvailablePlaces()+"");
        itemHolder.tDriverName.setText(ride.getOwner().getName());

        return view;
    }

    private static class ItemHolder {
        TextView tStartLocation;
        TextView tDestination;
        TextView tDate;
        TextView tTime;
        TextView tPlaces;
        TextView tDriverName;
        ImageView tDriverIco;
    }

    public List<Ride> getRides() {
        return rides;
    }
}
