package app.ridesharingapp.Fragments;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.R;
import app.ridesharingapp.Services.ApiClient;
import app.ridesharingapp.Utils.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsFragment extends Fragment {

    public UserDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_user_details, container, false);
        ImageView imageView = (ImageView) fragment.findViewById(R.id.user_image);
        EditText IdEdit = fragment.findViewById(R.id.user_id);
        EditText nameEdit = fragment.findViewById(R.id.user_name);
        EditText surnameEdit = fragment.findViewById(R.id.user_surname);
        EditText emailEdit = fragment.findViewById(R.id.user_email);
        EditText phoneEdit = fragment.findViewById(R.id.user_phone);
        EditText usernameEdit = fragment.findViewById(R.id.user_username);
        EditText addressEdit = fragment.findViewById(R.id.user_address);
        EditText emailVerified = fragment.findViewById(R.id.user_emailVerified);
        EditText activeEdit = fragment.findViewById(R.id.user_active);
        EditText roleEdit = fragment.findViewById(R.id.user_role);
        EditText CIDEdit = fragment.findViewById(R.id.user_CID);
        EditText userScoreEdit = fragment.findViewById(R.id.user_userScore);
        EditText ageEdit = fragment.findViewById(R.id.user_age);
        EditText googleEdit = fragment.findViewById(R.id.user_google);

        getUserDetails(IdEdit,nameEdit,surnameEdit,emailEdit,phoneEdit,usernameEdit,addressEdit,emailVerified,activeEdit,roleEdit,CIDEdit,userScoreEdit,ageEdit,googleEdit,imageView);

        Button addCarButton = fragment.findViewById(R.id.addCarButton);

        return fragment;
    }
    private void getUserDetails(
            EditText IdEdit,
        EditText nameEdit ,
        EditText surnameEdit,
        EditText emailEdit ,
        EditText phoneEdit,
        EditText usernameEdit,
        EditText addressEdit,
        EditText emailVerified,
        EditText activeEdit,
        EditText roleEdit,
        EditText CIDEdit,
        EditText userScoreEdit,
        EditText ageEdit,
        EditText googleEdit,
            ImageView imageView
    ){
        Call<User> userCall = ApiClient.getUserService().getUserDetails(SharedPreferenceUtil.getEmail(getContext()));
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    User res = response.body();
                    new Handler().post(() -> {
                        assert res != null;
                        DatabaseManager.getInstance().addUser(res);
                        IdEdit.setText(res.get_id());
                        nameEdit.setText(res.getName());
                        surnameEdit.setText(res.getSurname());
                        emailEdit.setText(res.getEmail());
                        phoneEdit.setText(res.getPhoneNumber());
                        usernameEdit.setText(res.getUsername());
                        emailVerified.setText(res.getEmailVerified() ? "yes":  "no");
                        activeEdit.setText(res.getActive() ? "yes":  "no");
                        roleEdit.setText(res.getRole());
                        CIDEdit.setText(res.getCid());
                        userScoreEdit.setText(res.getUserScore()!=null ? res.getUserScore().toString() : "0");
                        addressEdit.setText(res.getAddress());
                        googleEdit.setText("no");
                        ageEdit.setText("0");
                        Picasso.get().load(res.getImage()).fit().centerCrop().into(imageView);
                    });
                }else{
                    Toast.makeText(getContext(), "Fetch Failed",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable: " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
