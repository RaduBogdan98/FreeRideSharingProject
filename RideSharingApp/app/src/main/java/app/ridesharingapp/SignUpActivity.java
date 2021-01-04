package app.ridesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.ridesharingapp.Database.DatabaseManager;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText nameEdit = findViewById(R.id.nameEdit);
        final EditText emailEdit = findViewById(R.id.emailEdit);
        final EditText passwordEdit = findViewById(R.id.passwordEdit);
        final EditText phoneNumberEdit = findViewById(R.id.phoneNumberEdit);
        final EditText ageEdit = findViewById(R.id.ageEdit);

        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String phoneNumber = phoneNumberEdit.getText().toString();
                int age = Integer.parseInt(ageEdit.getText().toString());

//                if(DatabaseManager.getInstance().addUser(name, email, password, phoneNumber, age)){
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Sign Up Failed! Check credentials!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
