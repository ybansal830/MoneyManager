package com.myfirst.moneymanager;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    private String name,userName,email,password;
    private List<ItemList> itemList = new ArrayList<>();

    public UserData(){}

    public UserData(String name, String userName, String email, String password, List<ItemList> itemList) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.itemList = itemList;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<ItemList> getItemList() {
        return itemList;
    }
}
