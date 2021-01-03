package app.ridesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ridesharingapp.Database.DatabaseManager;
import app.ridesharingapp.Model.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                if(DatabaseManager.getInstance().findUser(emailText.getText().toString().trim(), passwordText.getText().toString().trim()) == true){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Log In Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
