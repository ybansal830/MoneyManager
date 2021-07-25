package com.myfirst.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private TextView mTvUserName,mTvName,mTvEmailId;
    private Button mBtnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        mTvUserName.setText(ListPassingHelper.userData.getUserName());
        mTvName.setText(ListPassingHelper.userData.getName());
        mTvEmailId.setText(ListPassingHelper.userData.getEmail());
        mBtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Login.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        mTvUserName = findViewById(R.id.tvUserName);
        mTvName = findViewById(R.id.tvName);
        mTvEmailId = findViewById(R.id.tvEmailId);
        mBtnSignOut = findViewById(R.id.btnSignOut);
    }
}