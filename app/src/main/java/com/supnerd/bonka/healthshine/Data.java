package com.supnerd.bonka.healthshine;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Data {

    String CurrentDate;
    String ZipCode;
    ArrayList<String> Symptoms;
    LatLng latLng;

    public Data() {
        Symptoms = new ArrayList<>();
    }

    public String getDate() {
        return CurrentDate;
    }

    public void setDate() {
        Date date = new Date();
        CurrentDate = date.toString();
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public ArrayList getSymptom() {
        return Symptoms;
    }

    public void setSymptom(ArrayList symptom) {
        Symptoms = symptom;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void addSymptoms(String symptom) {
        Symptoms.add(symptom);
    }

    public void clear() {
        Symptoms.clear();
        latLng = null;
    }

    public HashMap generateHashMap() {
        HashMap data = new HashMap();
        HashMap symptoms = new HashMap();
        data.put("Date", CurrentDate);
        data.put("Lat", latLng.latitude);
        data.put("Lng", latLng.longitude);
        int i = 0;
        for(String str : Symptoms) {
            symptoms.put(String.valueOf(i), str);
            i++;
        }
        data.put("Symptoms", symptoms);
        return data;
    }

}
