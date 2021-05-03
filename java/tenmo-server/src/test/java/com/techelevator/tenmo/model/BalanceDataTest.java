package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BalanceDataTest {

    BalanceData balanceData = new BalanceData();

    @Test
    public void getBalance() {
        BigDecimal balance = new BigDecimal("20000.00");
        balanceData.setBalance(balance);

        Assert.assertEquals(balance,balanceData.getBalance());
    }

    @Test
    public void setBalance() {
    }
}