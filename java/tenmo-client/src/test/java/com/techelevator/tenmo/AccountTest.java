package com.techelevator.tenmo;

import com.techelevator.tenmo.models.Account;
import io.cucumber.java.bs.A;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {

    @Test
    public void can_create_new_account(){
        Account testAccount = new Account();
        int testAccountId =1111;
        BigDecimal testBalance = new BigDecimal(1000.00);

        testAccount.setAccountId(1111);
        testAccount.setBalance(new BigDecimal(1000.00));

        Assert.assertEquals(testAccountId, testAccount.getAccountId());
        Assert.assertEquals(testBalance, testAccount.getBalance());
    }
}
