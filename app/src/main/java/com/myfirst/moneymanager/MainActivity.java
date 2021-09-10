package com.myfirst.moneymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private final String PREFERENCE_KEY = "com.myfirst.moneymanager";
    private UserData userData;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference("users");

    // Returning local shared preference file in private mode.

    public SharedPreferences getSharedPreference(Context context){
        return getSharedPreferences(PREFERENCE_KEY,context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* If User credentials already present in shared preference file then directly moving user
           to Home Activity else Login Activity.
        */

        if(getSharedPreference(this).getString("username",null) != null)
            directLogin();
        else {

            // 2 Sec delay to change Activity for showing splash screen.

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    /* Fetching all data from Firebase realtime database into local model class UserData
       and then directly login through the data comes from shared preference file.
    */

    private void directLogin(){
        Query user = reference.orderByChild("userName").equalTo(getSharedPreference
                (this).getString("username",null));
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userData = snapshot.child(getSharedPreference
                        (MainActivity.this).getString("username",null)).
                        getValue(UserData.class);
                ListPassingHelper.userData = userData;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /* 2 Sec delay to change Activity for showing splash screen as well as waiting for firebase
           to load data into local model class UserData.
        */

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish(); } }, 2000);
    }

}