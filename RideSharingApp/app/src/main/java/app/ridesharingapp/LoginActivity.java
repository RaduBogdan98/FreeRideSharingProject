package app.ridesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.Model.Requests.LoginRequest;
import app.ridesharingapp.Model.Responses.LoginResponse;
import app.ridesharingapp.Model.User;
import app.ridesharingapp.Services.ApiClient;
import app.ridesharingapp.Utils.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPreferenceUtil.getEmail(this) !=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        TextView forgotPasswordText = findViewById(R.id.forgotPasswordText);
        final EditText emailText = findViewById(R.id.emailEditLogin);
        final EditText passwordText = findViewById(R.id.passwordEditLogin);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailText.getText().toString(),passwordText.getText().toString());
            }
        });
    }

    public void login(String email,String password){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferenceUtil.saveEmail(email,getApplicationContext());
                            SharedPreferenceUtil.savePassword(password,getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("EMAIL",email);
                            startActivity(intent);
                            finish();
                        }
                    },500);
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable: " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
