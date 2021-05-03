package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private BigDecimal balance;
    private String userName;
    private int userId;


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

//    @Override
//    public String toString() {
//
//        return userId +"        " + userName  ;
//    }

    @Override
    public String toString() {

        return userName;
    }
}
