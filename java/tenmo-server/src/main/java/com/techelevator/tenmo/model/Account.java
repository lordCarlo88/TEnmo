package com.techelevator.tenmo.model;

public class Account {
    private int accountId;
    private int userId;
    private String userName;

    public Account(int accountId, int userId, String userName) {
        this.accountId = accountId;
        this.userId = userId;
        this.userName = userName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
