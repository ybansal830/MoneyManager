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

public class Login extends AppCompatActivity implements View.OnClickListener {

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
        if(getSharedPreference(this).getString("username",null) != null){
            Intent intent = new Intent(Login.this,Home.class);
            Query user = reference.orderByChild("userName").equalTo(getSharedPreference
                    (this).getString("username",null));
            user.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userData = snapshot.child(getSharedPreference
                            (Login.this).getString("username",null)).
                            getValue(UserData.class);
                    ListPassingHelper.userData = userData;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            startActivity(intent);
        }
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
                startActivity(new Intent(Login.this, SignUp.class));
        }
    }
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
                        if(mCbRememberMe.isChecked()){
                            SharedPreferences.Editor editor = getSharedPreference(Login.this).edit();
                            editor.putString("username",mEtUserName.getText().toString());
                            editor.apply();
                        }
                        Intent intent = new Intent(Login.this,Home.class);
                        userData = snapshot.child(mEtUserName.getText().toString()).
                                getValue(UserData.class);
                        ListPassingHelper.userData = userData;
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(Login.this,"Incorrect Passowrd",
                                Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Login.this,"Incorrect Username",
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