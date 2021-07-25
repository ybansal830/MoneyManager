package com.myfirst.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Expenses extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mFood, mBills, mHouse, mEntertainment, mShopping, mClothing, mTravel;
    private ImageButton mEducation, mOthers, mIbShowSelect;
    private EditText mEtInputAmount, mEtInputDate, mEtInputComment;
    private Button mBtnAdd;
    private TextView mTvShowSelect;
    private int id = R.drawable.others;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        initViews();
        if(ListPassingHelper.edit)
            alreadySetData();
        mFood.setOnClickListener(this);
        mBills.setOnClickListener(this);
        mHouse.setOnClickListener(this);
        mEntertainment.setOnClickListener(this);
        mShopping.setOnClickListener(this);
        mClothing.setOnClickListener(this);
        mTravel.setOnClickListener(this);
        mEducation.setOnClickListener(this);
        mOthers.setOnClickListener(this);
        mEtInputDate.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
    }

    private void alreadySetData() {
        ItemList itemList = ListPassingHelper.userData.getItemList().get
                (ListPassingHelper.clickPosition);
        mIbShowSelect.setImageResource(itemList.getCategoryImageId());
        mTvShowSelect.setText(itemList.getCategory());
        mEtInputAmount.setText(itemList.getCategoryAmount()+"");
        mEtInputDate.setText(itemList.getCategoryDate());
        mEtInputComment.setText(itemList.getCategoryComment());
    }

    private void initViews() {
        mFood = findViewById(R.id.food);
        mBills = findViewById(R.id.bills);
        mHouse = findViewById(R.id.house);
        mEntertainment = findViewById(R.id.entertainment);
        mShopping = findViewById(R.id.shopping);
        mClothing = findViewById(R.id.clothing);
        mTravel = findViewById(R.id.travel);
        mEducation = findViewById(R.id.education);
        mOthers = findViewById(R.id.others);
        mIbShowSelect = findViewById(R.id.ibShowSelect);
        mEtInputAmount = findViewById(R.id.etEnterAmount);
        mEtInputDate = findViewById(R.id.etEnterDate);
        mEtInputComment = findViewById(R.id.etEnterComment);
        mBtnAdd = findViewById(R.id.btnAdd);
        mTvShowSelect = findViewById(R.id.tvShowSelect);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food:
                mIbShowSelect.setImageResource(R.drawable.food);
                mTvShowSelect.setText("Food");
                id = R.drawable.food;
                break;
            case R.id.bills:
                mIbShowSelect.setImageResource(R.drawable.bills);
                mTvShowSelect.setText("Bills");
                id = R.drawable.bills;
                break;
            case R.id.house:
                mIbShowSelect.setImageResource(R.drawable.house);
                mTvShowSelect.setText("House");
                id = R.drawable.house;
                break;
            case R.id.entertainment:
                mIbShowSelect.setImageResource(R.drawable.entertainment);
                mTvShowSelect.setText("Entertainment");
                id = R.drawable.entertainment;
                break;
            case R.id.shopping:
                mIbShowSelect.setImageResource(R.drawable.shopping);
                mTvShowSelect.setText("Shopping");
                id = R.drawable.shopping;
                break;
            case R.id.clothing:
                mIbShowSelect.setImageResource(R.drawable.clothing);
                mTvShowSelect.setText("Clothing");
                id = R.drawable.clothing;
                break;
            case R.id.travel:
                mIbShowSelect.setImageResource(R.drawable.travel);
                mTvShowSelect.setText("Travel");
                id = R.drawable.travel;
                break;
            case R.id.education:
                mIbShowSelect.setImageResource(R.drawable.education);
                mTvShowSelect.setText("Education");
                id = R.drawable.education;
                break;
            case R.id.others:
                mIbShowSelect.setImageResource(R.drawable.others);
                mTvShowSelect.setText("Others");
                id = R.drawable.others;
                break;
            case R.id.etEnterDate:
                Calendar myCalendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        mEtInputDate.setText(sdf.format(myCalendar.getTime()));
                    }
                };
                new DatePickerDialog(Expenses.this,R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btnAdd:
                if (mEtInputAmount.getText().toString().length() < 1 ||
                        mEtInputDate.getText().toString().length() < 1)
                    Toast.makeText(Expenses.this, "Please fill up the details",
                            Toast.LENGTH_SHORT).show();
                else {
                    if(ListPassingHelper.edit){
                        ListPassingHelper.userData.getItemList().
                                remove(ListPassingHelper.clickPosition);
                        ListPassingHelper.edit = false;
                    }
                    ItemList itemList = new ItemList(mTvShowSelect.getText().toString(),
                            "Expenses", mEtInputComment.getText().toString(),
                            id, Float.parseFloat(mEtInputAmount.getText().
                            toString()), mEtInputDate.getText().toString());
                    ListPassingHelper.userData.getItemList().add(itemList);
                    reference.child(ListPassingHelper.userData.getUserName()).
                            setValue(ListPassingHelper.userData);
                    Intent intent = new Intent(Expenses.this,Home.class);
                    startActivity(intent);
                }
        }
    }
}