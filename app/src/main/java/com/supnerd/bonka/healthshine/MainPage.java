package com.supnerd.bonka.healthshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MainPage extends AppCompatActivity {

    final private int PLACE_PICKER_REQUEST = 1;
    private FirebaseManager manager;
    private LinearLayout m_Symptomview;
    private Button m_Submit;
    private Place m_Place;
    private Data m_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_Data = new Data();
        manager = FirebaseManager.getInstance();
        setContentView(R.layout.activity_main_page);
        FirebaseManager.getInstance();
        m_Symptomview = (LinearLayout) findViewById(R.id.main_symptoms);
        m_Submit = (Button) findViewById(R.id.main_submit);

        m_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MainPage.this), PLACE_PICKER_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                m_Place = place;
                post();
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                sendUserToThankyou();
            }
        }
    }

    private void sendUserToThankyou() {
        Intent mainIntent = new Intent(this, Thanks.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void post() {
        m_Data.setDate();
        m_Data.setLatLng(m_Place.getLatLng());
        for (int i = 0; i < m_Symptomview.getChildCount(); i++) {
            CheckBox v = (CheckBox) m_Symptomview.getChildAt(i);
            if (v.isChecked()) {
                m_Data.addSymptoms(v.getText().toString());
            }
        }
        String address = m_Place.getAddress().toString();
        m_Data.setZipCode(address.substring(address.length()-10, address.length()-5));
        manager.Post(m_Data, new ResultHandler() {
            @Override
            public void Success() {
                Toast.makeText(MainPage.this, "SUCCESS!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Failed() {
                Toast.makeText(MainPage.this, "Failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
        m_Data.clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] symptoms = getResources().getStringArray(R.array.symptoms);
        for(String str : symptoms) {
            CheckBox temp = new CheckBox(this);
            temp.setText(str);
            m_Symptomview.addView(temp);
        }
    }



}
