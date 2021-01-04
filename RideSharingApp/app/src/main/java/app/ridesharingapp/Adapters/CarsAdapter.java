package app.ridesharingapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.ridesharingapp.Model.Car;
import app.ridesharingapp.R;

public class CarsAdapter extends ArrayAdapter<Car> {
    private Context context;
    private List<Car> cars;
    private int layoutResID;

    public CarsAdapter(Context context, int layoutResourceID, List<Car> cars) {
        super(context, layoutResourceID, cars);
        this.context = context;
        this.cars = cars;
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
            itemHolder.carManufacturerName = (TextView) view.findViewById(R.id.car_manufacturer_name_TV);
            itemHolder.carModel = (TextView) view.findViewById(R.id.car_model_TV);
            itemHolder.carManufacturingYear = (TextView) view.findViewById(R.id.car_year_TV);
            itemHolder.carFuelType = (TextView) view.findViewById(R.id.car_fuel_type_TV);
            itemHolder.carColor = (TextView) view.findViewById(R.id.car_color_TV);

            view.setTag(itemHolder);

        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        final Car car = cars.get(position);

        itemHolder.carManufacturerName.setText(car.getOwner());
        itemHolder.carModel.setText(car.getModel());
        itemHolder.carManufacturingYear.setText(car.getYear());
        itemHolder.carFuelType.setText(car.getPlate());
        itemHolder.carColor.setText(car.getFuel());

        return view;
    }

    private static class ItemHolder {
        TextView carManufacturerName;
        TextView carModel;
        TextView carManufacturingYear;
        TextView carFuelType;
        TextView carColor;
    }
}
