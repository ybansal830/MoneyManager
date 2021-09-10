package com.myfirst.moneymanager;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvItemCategory,mTvItemComment,mTvItemAmount,mTvItemDate;
    private ImageView mIvItem;
    private ConstraintLayout mItemView;
    private OnItemClickListener onItemClickListener;

    public ItemViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        initViews();
    }

    private void initViews() {
        mTvItemCategory = itemView.findViewById(R.id.tvItemCategory);
        mTvItemComment = itemView.findViewById(R.id.tvItemComment);
        mTvItemAmount = itemView.findViewById(R.id.tvItemAmount);
        mTvItemDate = itemView.findViewById(R.id.tvItemDate);
        mIvItem = itemView.findViewById(R.id.ivItem);
        mItemView = itemView.findViewById(R.id.itemView);
    }

    public void setData(ItemList itemList){
        mTvItemCategory.setText(itemList.getCategory());

        // Making comment of particular item short while showing in recycler view list.

        String comment = itemList.getCategoryComment();
        String shortComment = "";
        int i=0;
        while(i < comment.length() && comment.charAt(i) != ' ' && i < 10){
            shortComment += comment.charAt(i);
            i++;
        }
        mTvItemComment.setText(shortComment);

        mTvItemAmount.setText(itemList.getCategoryAmount()+"");
        if(itemList.getCategoryType().equals("Income"))
            mTvItemAmount.setTextColor(Color.parseColor("#3BA72D"));
        else
            mTvItemAmount.setTextColor(Color.parseColor("#EC2E42"));
        mTvItemDate.setText(itemList.getCategoryDate());
        mIvItem.setImageResource(itemList.getCategoryImageId());
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(getAdapterPosition());
            }
        });
    }
}
