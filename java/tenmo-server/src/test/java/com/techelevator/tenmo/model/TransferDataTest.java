package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransferDataTest {

    @Test
    public void transferDataTest() {
        TransferData transferData = new TransferData();
        BigDecimal amount = new BigDecimal(200.00);
        int typeId = 1;
        int accountFrom = 2000;
        int accountTo = 3000;
        int statusId = 2;
        int transferId = 1000;
        String userName = "Johnny";
        transferData.setAmount(amount);
        Assert.assertEquals(amount, transferData.getAmount());
        transferData.setTransferTypeId(typeId);
        Assert.assertEquals(typeId, transferData.getTransferTypeId());
        transferData.setAccountFrom(accountFrom);
        Assert.assertEquals(accountFrom, transferData.getAccountFrom());
        transferData.setAccountTo(accountTo);
        Assert.assertEquals(accountTo, transferData.getAccountTo());
        transferData.setStatusId(statusId);
        Assert.assertEquals(statusId, transferData.getStatusId());
        transferData.setTransferId(transferId);
        Assert.assertEquals(transferId, transferData.getTransferId());
        transferData.setGetUserNameTo(userName);
        Assert.assertEquals(userName, transferData.getUserNameTo());
    }
}

//    @Test
//    public void setAmount() {
//    }
//
//    @Test
//    public void getTransferTypeId() {
//    }
//
//    @Test
//    public void setTransferTypeId() {
//    }
//
//    @Test
//    public void getAccountFrom() {
//    }
//
//    @Test
//    public void setAccountFrom() {
//    }
//
//    @Test
//    public void getAccountTo() {
//    }
//
//    @Test
//    public void setAccountTo() {
//    }
//
//    @Test
//    public void getStatusId() {
//    }
//
//    @Test
//    public void setStatusId() {
//    }
//
//    @Test
//    public void getTransferId() {
//    }
//
//    @Test
//    public void setTransferId() {
//    }
//
//    @Test
//    public void getUserNameTo() {
//    }
//
//    @Test
//    public void setGetUserNameTo() {
//    }
//}