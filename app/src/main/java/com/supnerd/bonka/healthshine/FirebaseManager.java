package com.supnerd.bonka.healthshine;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

class FirebaseManager {

    private static FirebaseManager single_instance = null;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private FirebaseManager() {
        firebaseUser = null;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseManager getInstance() {
        if(single_instance == null) {
            single_instance = new FirebaseManager();
        }
        return single_instance;
    }

    public void SignUp(String email, String password, final ResultHandler handler) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    handler.Success();
                } else {
                    handler.Failed();
                }
            }
        });
    }

    public void Post(Data data, final ResultHandler handler) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM,dd,yyyy");
        Date date = new Date();
        System.out.println(formatter.format(date));
        HashMap map = data.generateHashMap();
        Random random = new Random();
        UUID uuid = UUID.randomUUID();
        DatabaseReference ref = firebaseDatabase.getReference("Reports").child(formatter.format(date)).child(uuid.toString());
        ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {
                    handler.Success();
                } else {
                    handler.Failed();
                }
            }
        });
    }

    public void Login(String email, String password, final ResultHandler handler) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    handler.Success();
                } else {
                    handler.Failed();
                }
            }
        });
    }

    private boolean CheckUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null && !currentUser.isAnonymous()) {
            return true;
        }
        return false;
    }

}
