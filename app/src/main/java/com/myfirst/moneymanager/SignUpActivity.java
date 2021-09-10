package com.myfirst.moneymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private Button mBtnRegister;
    private EditText mEtName, mEtEmail, mEtUserName, mEtPassword, mEtRePassword;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference("users");
    private List<ItemList> itemList = new ArrayList<>();
    private UserData userData;
    private String emailValid = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();

        /* After clicking on register button checking all the credentials entered by the user
           are valid or not. If not then showing error for particular field. */

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtName.getText().toString().trim().length() == 0)
                    mEtName.setError("Name can't be empty");
                else if (mEtUserName.getText().toString().trim().length() == 0)
                    mEtUserName.setError("Username can't be empty");
                else if (!mEtEmail.getText().toString().matches(emailValid))
                    mEtEmail.setError("Invalid Email");
                else if (mEtPassword.getText().toString().trim().length() == 0 &&
                        mEtPassword.getText().toString().trim().length() < 6)
                    mEtPassword.setError("Weak Password");
                else if (!mEtRePassword.getText().toString().equals(mEtPassword.getText().toString()))
                    mEtRePassword.setError("Password must be same");

        /* After checking credentials checking is username entered by the user is already exist or
           not. If exist then showing message user already exist else saving all the credentials
           entered by the user into firebase realtime database and moving user to login activity.
        */

                else {
                    Query checkUserAlreadyExist = reference.orderByChild("userName").
                            equalTo(mEtUserName.getText().toString());
                    checkUserAlreadyExist.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) Toast.makeText(SignUpActivity.this,
                                    "User already exist", Toast.LENGTH_LONG).show();
                            else {
                                userData = new UserData(mEtName.getText().toString(),
                                        mEtUserName.getText().toString(), mEtEmail.getText().toString(),
                                        mEtPassword.getText().toString(), itemList);
                                reference.child(mEtUserName.getText().toString()).setValue(userData);
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

    }

    private void initViews() {
        mBtnRegister = findViewById(R.id.btnRegister);
        mEtName = findViewById(R.id.etName);
        mEtEmail = findViewById(R.id.etEmail);
        mEtPassword = findViewById(R.id.etPassword);
        mEtRePassword = findViewById(R.id.etRePassword);
        mEtUserName = findViewById(R.id.etUsername);
    }

}