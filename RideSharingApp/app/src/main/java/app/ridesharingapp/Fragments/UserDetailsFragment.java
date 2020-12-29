package app.ridesharingapp.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import app.ridesharingapp.R;

public class UserDetailsFragment extends Fragment {

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_user_details, container, false);

        EditText nameEdit = fragment.findViewById(R.id.user_name);
        EditText ageEdit = fragment.findViewById(R.id.user_age);
        EditText emailEdit = fragment.findViewById(R.id.user_email);
        EditText phoneEdit = fragment.findViewById(R.id.user_phone);
        EditText licenseEdit = fragment.findViewById(R.id.user_licenseID);
        EditText addressEdit = fragment.findViewById(R.id.user_address);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            Drawable nameDrawable = nameEdit.getTextCursorDrawable().getCurrent();
        }

        return fragment;
    }
}
