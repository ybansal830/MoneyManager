package com.myfirst.moneymanager;

public class ItemList {

    private String category,categoryType,categoryComment;
    private int categoryImageId;
    private float categoryAmount;
    private String categoryDate;

    public ItemList(){}

    public ItemList(String category, String categoryType, String categoryComment,
                    int categoryImageId, float categoryAmount, String categoryDate) {
        this.category = category;
        this.categoryType = categoryType;
        this.categoryComment = categoryComment;
        this.categoryImageId = categoryImageId;
        this.categoryAmount = categoryAmount;
        this.categoryDate = categoryDate;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public String getCategoryComment() {
        return categoryComment;
    }

    public int getCategoryImageId() {
        return categoryImageId;
    }

    public float getCategoryAmount() {
        return categoryAmount;
    }

    public String getCategoryDate() {
        return categoryDate;
    }
}
