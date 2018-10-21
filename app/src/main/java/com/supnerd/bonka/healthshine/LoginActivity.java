package com.supnerd.bonka.healthshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private FirebaseManager manager;
    private Button m_Login;
    private TextView m_Username;
    private TextView m_Password;
    private TextView m_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = FirebaseManager.getInstance();
        m_Register = (TextView) findViewById(R.id.login_register_button);
        m_Login = (Button) findViewById(R.id.login_button);
        m_Username = (TextView) findViewById(R.id.login_email);
        m_Password = (TextView) findViewById(R.id.login_password);

        m_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegister();
            }
        });
        m_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = m_Username.getText().toString();
        String password = m_Password.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter an your Email", Toast.LENGTH_SHORT).show();
        } else if(password.isEmpty()) {
            Toast.makeText(this, "Please enter an your Password", Toast.LENGTH_SHORT).show();
        } else {
            Intent mainIntent = new Intent(LoginActivity.this, MainPage.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();
        }
    }

    private void sendUserToRegister() {
        Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }

}
