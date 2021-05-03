package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    Account account = new Account(1,1,"James");

    @Test
    public void getAccountId() {
        Assert.assertEquals(1,account.getAccountId());

    }

    @Test
    public void setAccountId() {
          account.setAccountId(1000);
          Assert.assertEquals(1000,account.getAccountId());

    }

    @Test
    public void getUserId() {
        Assert.assertEquals(1,account.getUserId());

    }

    @Test
    public void setUserId() {
        account.setUserId(2000);
        Assert.assertEquals(2000,account.getUserId());
    }

    @Test
    public void getUserName() {
        Assert.assertEquals("James",account.getUserName());
    }

    @Test
    public void setUserName() {
        account.setUserName("Figaro");
        Assert.assertEquals("Figaro",account.getUserName());
    }
}