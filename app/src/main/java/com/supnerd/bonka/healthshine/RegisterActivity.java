package com.supnerd.bonka.healthshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseManager manager;
    private TextView m_Password1;
    private TextView m_Password2;
    private TextView m_Email;
    private Button m_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        manager = FirebaseManager.getInstance();
        m_Email = (TextView) findViewById(R.id.register_email);
        m_Password1 = (TextView) findViewById(R.id.register_password1);
        m_Password2 = (TextView) findViewById(R.id.register_password2);
        m_Register = (Button) findViewById(R.id.register_button);

        m_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    private void signUp() {
        String email = m_Email.getText().toString();
        String password1 = m_Password1.getText().toString();
        String password2 = m_Password2.getText().toString();
        if(password1.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        } else if (!password1.equals(password2)) {
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private void sendUserToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainPage.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }
}
