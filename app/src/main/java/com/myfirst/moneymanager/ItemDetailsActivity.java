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

public class ItemDetailsActivity extends AppCompatActivity {

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

        /* When user clicks on delete item button then showing alert box to the user with options
           confirm delete or cancel when user clicks on cancel then simply drop the alert box else
           removing the particular item from the list as well as from the firebase realtime database
           and then moving user to Home Activity.
        */

        mIbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ItemDetailsActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListPassingHelper.userData.getItemList().remove
                                        (ListPassingHelper.clickPosition);
                                reference.child(ListPassingHelper.userData.getUserName()).
                                        setValue(ListPassingHelper.userData);
                                startActivity(new Intent(ItemDetailsActivity.this, HomeActivity.class));
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

        /* When user clicks on edit item button then moving user to Expenses/Income Activity
           according to the item.
        */

        mIbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPassingHelper.edit = true;
                if(itemList.getCategoryType().equals("Expenses"))
                    startActivity(new Intent(ItemDetailsActivity.this, ExpensesActivity.class));
                else
                    startActivity(new Intent(ItemDetailsActivity.this, IncomeActivity.class));
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

    // Showing and setting all data of particular clicked item.

    private void setData() {
        mTvAmount.setText(itemList.getCategoryAmount()+"");
        mTvCategory.setText(itemList.getCategory());
        mTvType.setText(itemList.getCategoryType());
        mTvDate.setText(itemList.getCategoryDate());
        mTvComment.setText(itemList.getCategoryComment());
        mIvCategoryImage.setImageResource(itemList.getCategoryImageId());
    }
}