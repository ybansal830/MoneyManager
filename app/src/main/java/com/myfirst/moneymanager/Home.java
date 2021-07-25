package com.myfirst.moneymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private CardView mLMenu;
    private ImageButton mIbMenu, mIbBack, mIbAddExpense, mIbAddIncome;
    private TextView mTvProfile, mTvHome, mTvCustom, mTvCategory;
    private TextView mTvTotalExpense,mTvTotalIncome,mTvTotalBalance;
    private RecyclerView mRecyclerView;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("users");
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setRecyclerView();
        mIbMenu.setOnClickListener(this);
        mIbBack.setOnClickListener(this);
        mIbAddExpense.setOnClickListener(this);
        mIbAddIncome.setOnClickListener(this);
        mTvProfile.setOnClickListener(this);
        mTvHome.setOnClickListener(this);
        mTvCustom.setOnClickListener(this);
        mTvCategory.setOnClickListener(this);
    }

    private void initViews() {
        mLMenu = findViewById(R.id.lMenu);
        mIbMenu = findViewById(R.id.ibMenu);
        mIbBack = findViewById(R.id.ibBack);
        mIbAddExpense = findViewById(R.id.ibAddExpense);
        mIbAddIncome = findViewById(R.id.ibAddIncome);
        mTvProfile = findViewById(R.id.tvProfile);
        mTvHome = findViewById(R.id.tvHome);
        mTvCustom = findViewById(R.id.tvCustom);
        mTvCategory = findViewById(R.id.tvCategory);
        mRecyclerView = findViewById(R.id.recyclerView);
        mTvTotalIncome = findViewById(R.id.tvTotalIncome);
        mTvTotalExpense = findViewById(R.id.tvTotalExpense);
        mTvTotalBalance = findViewById(R.id.tvTotalBalance);
    }

    public void setRecyclerView() {
        if (ListPassingHelper.userData.getItemList() != null) {
            float totalIncome = 0,totalBalance = 0,totalExpense = 0;
            setAdapter();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Home.this,
                    RecyclerView.VERTICAL,false);
            mRecyclerView.setAdapter(itemAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            for(int i=0;i<ListPassingHelper.userData.getItemList().size();i++){
                if(ListPassingHelper.userData.getItemList().get(i).getCategoryType().equals("Income"))
                    totalIncome += ListPassingHelper.userData.getItemList().get(i).getCategoryAmount();
                else if(ListPassingHelper.userData.getItemList().get(i).getCategoryType().equals("Expenses"))
                    totalExpense += ListPassingHelper.userData.getItemList().get(i).getCategoryAmount();
            }
            totalBalance = totalIncome - totalExpense;
            mTvTotalIncome.setText("Rs."+totalIncome+"");
            mTvTotalBalance.setText("Rs."+totalBalance+"");
            mTvTotalExpense.setText("Rs."+totalExpense+"");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMenu:
                mLMenu.setVisibility(View.VISIBLE);
                break;
            case R.id.ibBack:
                mLMenu.setVisibility(View.GONE);
                break;
            case R.id.ibAddExpense:
                startActivity(new Intent(Home.this, Expenses.class));
                break;
            case R.id.ibAddIncome:
                startActivity(new Intent(Home.this, Income.class));
                break;
            case R.id.tvProfile:
                startActivity(new Intent(Home.this, Profile.class));
                break;
            case R.id.tvHome:
                startActivity(new Intent(Home.this, Home.class));
                break;
            case R.id.tvCustom:
                startActivity(new Intent(Home.this, Custom.class));
                break;
            case R.id.tvCategory:
                startActivity(new Intent(Home.this, Category.class));
                break;
        }
    }

    public void setAdapter() {
        itemAdapter = new ItemAdapter(ListPassingHelper.userData.getItemList(), this);
    }

    @Override
    public void onClick(int position) {
        ListPassingHelper.clickPosition = position;
        startActivity(new Intent(Home.this, ItemDetails.class));
    }
}