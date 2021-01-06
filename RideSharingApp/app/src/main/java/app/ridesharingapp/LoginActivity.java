package app.ridesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.Utils.SharedPreferenceUtil;

public class LoginActivity extends AppCompatActivity {

    private DatabaseManager databaseManager = DatabaseManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferenceUtil.saveEmail(null, getApplicationContext());
        SharedPreferenceUtil.savePassword(null, getApplicationContext());

        if (SharedPreferenceUtil.getEmail(this) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        final EditText emailText = findViewById(R.id.emailEditLogin);
        final EditText passwordText = findViewById(R.id.passwordEditLogin);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            databaseManager.getAllRides(getApplicationContext());
            databaseManager.loginUser(getApplicationContext(), emailText.getText().toString(), passwordText.getText().toString());
        });
    }

    public void login(String email, String password) {

    }
}
