package com.myfirst.moneymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnLogin, mBtnCreateNow;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference("users");
    private EditText mEtUserName,mEtPassword;
    private CheckBox mCbRememberMe;
    private final String PREFERENCE_KEY = "com.myfirst.moneymanager";
    private UserData userData;

    public SharedPreferences getSharedPreference(Context context){
        return getSharedPreferences(PREFERENCE_KEY,context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        mBtnLogin.setOnClickListener(this);
        mBtnCreateNow.setOnClickListener(this);
    }

    private void initViews() {
        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnCreateNow = findViewById(R.id.btnCreateNow);
        mEtUserName = findViewById(R.id.etUsername);
        mEtPassword = findViewById(R.id.etPassword);
        mCbRememberMe = findViewById(R.id.cbRememberMe);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if(mEtUserName.getText().toString().trim().length() == 0)
                    mEtUserName.setError("Username can't be empty");
                else if(mEtPassword.getText().toString().trim().length() == 0)
                    mEtPassword.setError("Password can't be empty");
                else
                isCorrect();
                break;
            case R.id.btnCreateNow:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
    }

    /* Checking data entered by the user at login are matching with the data present in
       firebase realtime database or not. If present then directly moving to Home Activity
       else showing error.
    */

    private void isCorrect(){
        Query checkUserExist = reference.orderByChild("userName").equalTo(mEtUserName.
                getText().toString());
        checkUserExist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordFromDb = snapshot.child(mEtUserName.getText().toString())
                            .child("password").getValue(String.class);
                    if(passwordFromDb.equals(mEtPassword.getText().toString())){

                        /* After checking credentials checking remember me checkbox is checked
                           or not if checked then saving credentials into shared preference file
                           also.
                        */

                        if(mCbRememberMe.isChecked()){
                            SharedPreferences.Editor editor = getSharedPreference(LoginActivity.this).edit();
                            editor.putString("username",mEtUserName.getText().toString());
                            editor.apply();
                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        userData = snapshot.child(mEtUserName.getText().toString()).
                                getValue(UserData.class);
                        ListPassingHelper.userData = userData;
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(LoginActivity.this,"Incorrect Passowrd",
                                Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginActivity.this,"Incorrect Username",
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}