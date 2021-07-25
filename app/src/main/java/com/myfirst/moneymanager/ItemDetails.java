package com.myfirst.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemDetails extends AppCompatActivity {

    private TextView mTvAmount,mTvCategory,mTvType,mTvDate,mTvComment;
    private ImageButton mIbEdit,mIbDelete;
    private ImageView mIvCategoryImage;
    private ItemList itemList = ListPassingHelper.userData.getItemList().
            get(ListPassingHelper.clickPosition);
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        initViews();
        mIbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ItemDetails.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListPassingHelper.userData.getItemList().remove
                                        (ListPassingHelper.clickPosition);
                                reference.child(ListPassingHelper.userData.getUserName()).
                                        setValue(ListPassingHelper.userData);
                                startActivity(new Intent(ItemDetails.this,Home.class));
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        mIbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPassingHelper.edit = true;
                if(itemList.getCategoryType().equals("Expenses"))
                    startActivity(new Intent(ItemDetails.this,Expenses.class));
                else
                    startActivity(new Intent(ItemDetails.this,Income.class));
            }
        });
    }

    private void initViews() {
        mTvAmount = findViewById(R.id.tvAmount);
        mTvCategory = findViewById(R.id.tvCategory);
        mTvType = findViewById(R.id.tvType);
        mTvDate = findViewById(R.id.tvDate);
        mTvComment = findViewById(R.id.tvComment);
        mIvCategoryImage = findViewById(R.id.ivCategoryImage);
        mIbEdit = findViewById(R.id.ibEdit);
        mIbDelete = findViewById(R.id.ibDelete);
        setData();
    }

    private void setData() {
        mTvAmount.setText(itemList.getCategoryAmount()+"");
        mTvCategory.setText(itemList.getCategory());
        mTvType.setText(itemList.getCategoryType());
        mTvDate.setText(itemList.getCategoryDate());
        mTvComment.setText(itemList.getCategoryComment());
        mIvCategoryImage.setImageResource(itemList.getCategoryImageId());
    }
}