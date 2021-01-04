package app.ridesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.Model.Requests.LoginRequest;
import app.ridesharingapp.Model.Requests.RegisterRequest;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.Services.ApiClient;
import app.ridesharingapp.Utils.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText nameEdit = findViewById(R.id.nameEdit);
        final EditText emailEdit = findViewById(R.id.emailEdit);
        final EditText passwordEdit = findViewById(R.id.passwordEdit);
        final EditText surnameEdit = findViewById(R.id.surnameEdit);
        final EditText ageEdit = findViewById(R.id.ageEdit);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString().trim();
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String surname = surnameEdit.getText().toString().trim();
                int age = Integer.parseInt(ageEdit.getText().toString().trim());

                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setEmail(email);
                registerRequest.setName(name);
                registerRequest.setPassword(password);
                registerRequest.setSurname(surname);
                registerRequest.setAge(age);
                registerRequest.setRole(role);

                Call<User> loginResponseCall = ApiClient.getUserService().userRegister(registerRequest);
                loginResponseCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Register Successful",Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },300);
                        }else{
                            Toast.makeText(SignUpActivity.this, "Register Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "Throwable: " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_driver:
                if (checked)
                    role = "driver";
                System.out.println(role);
                    break;
            case R.id.radio_client:
                if (checked)
                    role = "client";
                System.out.println(role);
                    break;
            default:role = "client";
        }
    }
}
