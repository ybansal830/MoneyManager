package com.myfirst.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Custom extends AppCompatActivity implements View.OnClickListener,OnItemClickListener {

    private CardView mLMenu, mCvDate;
    private ImageButton mIbMenu, mIbDateInput, mIbBack, mIbAddExpense, mIbAddIncome;
    private TextView mTvProfile, mTvHome, mTvCustom, mTvCategory;
    private TextView mTvTotalExpense, mTvTotalIncome, mTvTotalBalance;
    private Button mBtnApply;
    private EditText mEtInputDateStart, mEtInputDateEnd;
    private Boolean dateInputVisible = true;
    private List<ItemList> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private RecyclerView mRecyclerView;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date, date2;
    String myFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private ArrayList<Integer> originalPosition = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        initViews();
        datePickerDialogOne();
        datePickerDialogTwo();
        mIbMenu.setOnClickListener(this);
        mIbDateInput.setOnClickListener(this);
        mBtnApply.setOnClickListener(this);
        mIbBack.setOnClickListener(this);
        mIbAddExpense.setOnClickListener(this);
        mIbAddIncome.setOnClickListener(this);
        mTvProfile.setOnClickListener(this);
        mTvHome.setOnClickListener(this);
        mTvCustom.setOnClickListener(this);
        mTvCategory.setOnClickListener(this);
        mEtInputDateStart.setOnClickListener(this);
        mEtInputDateEnd.setOnClickListener(this);
    }

    private void initViews() {
        mLMenu = findViewById(R.id.lMenu);
        mIbMenu = findViewById(R.id.ibMenu);
        mCvDate = findViewById(R.id.cvDate);
        mIbDateInput = findViewById(R.id.ibDateInput);
        mBtnApply = findViewById(R.id.btnApply);
        mIbBack = findViewById(R.id.ibBack);
        mIbAddExpense = findViewById(R.id.ibAddExpense);
        mIbAddIncome = findViewById(R.id.ibAddIncome);
        mTvProfile = findViewById(R.id.tvProfile);
        mTvHome = findViewById(R.id.tvHome);
        mTvCustom = findViewById(R.id.tvCustom);
        mTvCategory = findViewById(R.id.tvCategory);
        mEtInputDateStart = findViewById(R.id.etInputDateStart);
        mEtInputDateEnd = findViewById(R.id.etInputDateEnd);
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
                startActivity(new Intent(Custom.this, Expenses.class));
                break;
            case R.id.ibAddIncome:
                startActivity(new Intent(Custom.this, Income.class));
                break;
            case R.id.btnApply:
                mCvDate.setVisibility(View.GONE);
                setRecyclerView();
                itemList = new ArrayList<>();
                dateInputVisible = false;
                break;
            case R.id.ibDateInput:
                if (dateInputVisible == false) {
                    mCvDate.setVisibility(View.VISIBLE);
                    dateInputVisible = true;
                } else {
                    mCvDate.setVisibility(View.GONE);
                    dateInputVisible = false;
                }
                break;
            case R.id.tvProfile:
                startActivity(new Intent(Custom.this, Profile.class));
                break;
            case R.id.tvHome:
                startActivity(new Intent(Custom.this, Home.class));
                break;
            case R.id.tvCustom:
                startActivity(new Intent(Custom.this, Custom.class));
                break;
            case R.id.tvCategory:
                startActivity(new Intent(Custom.this, Category.class));
                break;
            case R.id.etInputDateStart:
                new DatePickerDialog(Custom.this,R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.etInputDateEnd:
                new DatePickerDialog(Custom.this,R.style.DialogTheme, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    public void datePickerDialogOne() {
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
    }

    private void updateLabel() {
        mEtInputDateStart.setText(sdf.format(myCalendar.getTime()));
    }

    public void datePickerDialogTwo() {
        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelTwo();
            }
        };
    }

    private void updateLabelTwo() {
        mEtInputDateEnd.setText(sdf.format(myCalendar.getTime()));
    }

    public void setRecyclerView() {
        if (ListPassingHelper.userData.getItemList() != null) {
            float totalIncome = 0, totalBalance = 0, totalExpense = 0;
            try {
                Date start = sdf.parse(mEtInputDateStart.getText().toString());
                Date end = sdf.parse(mEtInputDateEnd.getText().toString());
                for (int i = 0; i < ListPassingHelper.userData.getItemList().size(); i++) {
                    try {
                        Date check = sdf.parse(ListPassingHelper.userData.getItemList().get(i).
                                getCategoryDate());
                        if (check.compareTo(start) >= 0 && check.compareTo(end) <= 0) {
                            itemList.add(ListPassingHelper.userData.getItemList().get(i));
                            originalPosition.add(i);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(itemList != null){
                itemAdapter = new ItemAdapter(itemList,this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setAdapter(itemAdapter);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                for(int i=0;i<itemList.size();i++){
                    if(itemList.get(i).getCategoryType().equals("Income"))
                        totalIncome += itemList.get(i).getCategoryAmount();
                    else if(itemList.get(i).getCategoryType().equals("Expenses"))
                        totalExpense += itemList.get(i).getCategoryAmount();
                }
                totalBalance = totalIncome - totalExpense;
                mTvTotalIncome.setText("Rs."+totalIncome+"");
                mTvTotalBalance.setText("Rs."+totalBalance+"");
                mTvTotalExpense.setText("Rs."+totalExpense+"");
            }
        }
    }

    @Override
    public void onClick(int position) {
        ListPassingHelper.clickPosition = originalPosition.get(position);
        startActivity(new Intent(Custom.this,ItemDetails.class));
    }
}