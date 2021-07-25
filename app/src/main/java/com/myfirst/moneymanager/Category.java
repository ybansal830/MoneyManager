package com.myfirst.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private CardView mLMenu, mCvCategory;
    private ImageButton mIbMenu, mIbCategoryInput, mIbBack, mIbAddExpense, mIbAddIncome;
    private Button mBtnApply;
    private TextView mTvProfile, mTvHome, mTvCustom, mTvCategory, mTvTotalIncome, mTvTotalBalance;
    private TextView mTvTotalExpense;
    private EditText mEtInputIncome, mEtInputExpense;
    private Boolean categoryInputVisible = true;
    private List<ItemList> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Integer> originalPosition = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
        mIbMenu.setOnClickListener(this);
        mIbBack.setOnClickListener(this);
        mIbAddExpense.setOnClickListener(this);
        mIbAddIncome.setOnClickListener(this);
        mTvProfile.setOnClickListener(this);
        mTvHome.setOnClickListener(this);
        mTvCustom.setOnClickListener(this);
        mTvCategory.setOnClickListener(this);
        mIbCategoryInput.setOnClickListener(this);
        mBtnApply.setOnClickListener(this);
    }

    private void initViews() {
        mLMenu = findViewById(R.id.lMenu);
        mIbMenu = findViewById(R.id.ibMenu);
        mCvCategory = findViewById(R.id.cvCategory);
        mIbCategoryInput = findViewById(R.id.ibCategoryInput);
        mBtnApply = findViewById(R.id.btnApply);
        mIbBack = findViewById(R.id.ibBack);
        mIbAddExpense = findViewById(R.id.ibAddExpense);
        mIbAddIncome = findViewById(R.id.ibAddIncome);
        mTvProfile = findViewById(R.id.tvProfile);
        mTvHome = findViewById(R.id.tvHome);
        mTvCustom = findViewById(R.id.tvCustom);
        mTvCategory = findViewById(R.id.tvCategory);
        mEtInputIncome = findViewById(R.id.etInputIncome);
        mEtInputExpense = findViewById(R.id.etInputExpense);
        mRecyclerView = findViewById(R.id.recyclerView);
        mTvTotalIncome = findViewById(R.id.tvTotalIncome);
        mTvTotalExpense = findViewById(R.id.tvTotalExpense);
        mTvTotalBalance = findViewById(R.id.tvTotalBalance);
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
                startActivity(new Intent(Category.this, Expenses.class));
                break;
            case R.id.ibAddIncome:
                startActivity(new Intent(Category.this, Income.class));
                break;
            case R.id.btnApply:
                mCvCategory.setVisibility(View.GONE);
                setRecyclerView();
                itemList = new ArrayList<>();
                categoryInputVisible = false;
                break;
            case R.id.ibCategoryInput:
                if (categoryInputVisible == false) {
                    mCvCategory.setVisibility(View.VISIBLE);
                    categoryInputVisible = true;
                } else {
                    mCvCategory.setVisibility(View.GONE);
                    categoryInputVisible = false;
                }
                break;
            case R.id.tvProfile:
                startActivity(new Intent(Category.this, Profile.class));
                break;
            case R.id.tvHome:
                startActivity(new Intent(Category.this, Home.class));
                break;
            case R.id.tvCustom:
                startActivity(new Intent(Category.this, Custom.class));
                break;
            case R.id.tvCategory:
                startActivity(new Intent(Category.this, Category.class));
        }
    }

    public void setRecyclerView() {
        float totalIncome = 0, totalBalance = 0, totalExpense = 0;
        if (ListPassingHelper.userData.getItemList() != null) {
            if (mEtInputIncome.getText().toString().trim().length() > 0 || mEtInputExpense.getText()
                    .toString().trim().length() > 0) {
                for (int i = 0; i < ListPassingHelper.userData.getItemList().size(); i++) {
                    if (ListPassingHelper.userData.getItemList().get(i).getCategory().equals
                            (mEtInputIncome.getText().toString()) || ListPassingHelper.userData.
                            getItemList().get(i).getCategory().equals
                            (mEtInputExpense.getText().toString())) {
                        itemList.add(ListPassingHelper.userData.getItemList().get(i));
                        originalPosition.add(i);
                    }
                }
                if (itemList != null) {
                    itemAdapter = new ItemAdapter(itemList, this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    mRecyclerView.setAdapter(itemAdapter);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    for (int i = 0; i < itemList.size(); i++) {
                        if (itemList.get(i).getCategoryType().equals("Income"))
                            totalIncome += itemList.get(i).getCategoryAmount();
                        else if (itemList.get(i).getCategoryType().equals("Expenses"))
                            totalExpense += itemList.get(i).getCategoryAmount();
                    }
                    totalBalance = totalIncome - totalExpense;
                    mTvTotalIncome.setText("Rs." + totalIncome + "");
                    mTvTotalBalance.setText("Rs." + totalBalance + "");
                    mTvTotalExpense.setText("Rs." + totalExpense + "");
                }
            }
        }

    }

    @Override
    public void onClick(int position) {
        ListPassingHelper.clickPosition = originalPosition.get(position);
        startActivity(new Intent(Category.this, ItemDetails.class));
    }
}