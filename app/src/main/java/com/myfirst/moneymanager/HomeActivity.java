package com.myfirst.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

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

    /* Setting recycler view with all the income and expenses registered by the user and also
       calculating user total income, expenses and balance amount.
    */

    public void setRecyclerView() {
        if (ListPassingHelper.userData.getItemList() != null) {
            float totalIncome = 0,totalBalance = 0,totalExpense = 0;
            setAdapter();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this,
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
                startActivity(new Intent(HomeActivity.this, ExpensesActivity.class));
                break;
            case R.id.ibAddIncome:
                startActivity(new Intent(HomeActivity.this, IncomeActivity.class));
                break;
            case R.id.tvProfile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case R.id.tvHome:
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                break;
            case R.id.tvCustom:
                startActivity(new Intent(HomeActivity.this, CustomActivity.class));
                break;
            case R.id.tvCategory:
                startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
                break;
        }
    }

    public void setAdapter() {
        itemAdapter = new ItemAdapter(ListPassingHelper.userData.getItemList(), this);
    }

    /* When user click any of the item showing in the recycler view then moving to the item details
       activity for showing more details of the particular item.
    */

    @Override
    public void onClick(int position) {
        ListPassingHelper.clickPosition = position;
        startActivity(new Intent(HomeActivity.this, ItemDetailsActivity.class));
    }
}