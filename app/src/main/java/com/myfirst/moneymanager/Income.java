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

public class Income extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mSalary, mAwards, mGrants, mSale, mRental, mRefunds, mLottery;
    private ImageButton mInvestments, mOthers, mIbShowSelect;
    private EditText mEtInputAmount,mEtInputDate,mEtInputComment;
    private Button mBtnAdd;
    private TextView mTvShowSelect;
    private int id = R.drawable.others;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        initViews();
        if(ListPassingHelper.edit)
            alreadySetData();
        mSalary.setOnClickListener(this);
        mAwards.setOnClickListener(this);
        mGrants.setOnClickListener(this);
        mSale.setOnClickListener(this);
        mRental.setOnClickListener(this);
        mRefunds.setOnClickListener(this);
        mLottery.setOnClickListener(this);
        mInvestments.setOnClickListener(this);
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
        mSalary = findViewById(R.id.salary);
        mAwards = findViewById(R.id.awards);
        mGrants = findViewById(R.id.grants);
        mSale = findViewById(R.id.sale);
        mRental = findViewById(R.id.rental);
        mRefunds = findViewById(R.id.refunds);
        mLottery = findViewById(R.id.lottery);
        mInvestments = findViewById(R.id.investments);
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
        switch (v.getId()){
            case R.id.salary:
                mIbShowSelect.setImageResource(R.drawable.salary);
                mTvShowSelect.setText("Salary");
                id = R.drawable.salary;
                break;
            case R.id.awards:
                mIbShowSelect.setImageResource(R.drawable.awards);
                mTvShowSelect.setText("Awards");
                id = R.drawable.awards;
                break;
            case R.id.grants:
                mIbShowSelect.setImageResource(R.drawable.grants);
                mTvShowSelect.setText("Grants");
                id = R.drawable.grants;
                break;
            case R.id.sale:
                mIbShowSelect.setImageResource(R.drawable.sale);
                mTvShowSelect.setText("Sale");
                id = R.drawable.sale;
                break;
            case R.id.rental:
                mIbShowSelect.setImageResource(R.drawable.rental);
                mTvShowSelect.setText("Rental");
                id = R.drawable.rental;
                break;
            case R.id.refunds:
                mIbShowSelect.setImageResource(R.drawable.refunds);
                mTvShowSelect.setText("Refunds");
                id = R.drawable.refunds;
                break;
            case R.id.lottery:
                mIbShowSelect.setImageResource(R.drawable.lottery);
                mTvShowSelect.setText("Lottery");
                id = R.drawable.lottery;
                break;
            case R.id.investments:
                mIbShowSelect.setImageResource(R.drawable.investments);
                mTvShowSelect.setText("Investments");
                id = R.drawable.investments;
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
                new DatePickerDialog(Income.this,R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btnAdd:
                if (mEtInputAmount.getText().toString().length() < 1 ||
                        mEtInputDate.getText().toString().length() < 1 ||
                    mEtInputComment.getText().toString().trim().length() < 1)
                    Toast.makeText(Income.this, "Please fill up the details",
                            Toast.LENGTH_SHORT).show();
                else {
                    if(ListPassingHelper.edit){
                        ListPassingHelper.userData.getItemList().
                                remove(ListPassingHelper.clickPosition);
                        ListPassingHelper.edit = false;
                    }
                    ItemList itemList = new ItemList(mTvShowSelect.getText().toString(),
                            "Income", mEtInputComment.getText().toString(),
                            id, Float.parseFloat(mEtInputAmount.getText().
                            toString()), mEtInputDate.getText().toString());
                    ListPassingHelper.userData.getItemList().add(itemList);
                    reference.child(ListPassingHelper.userData.getUserName()).
                            setValue(ListPassingHelper.userData);
                    Intent intent = new Intent(Income.this,Home.class);
                    startActivity(intent);
                }
        }
    }
}