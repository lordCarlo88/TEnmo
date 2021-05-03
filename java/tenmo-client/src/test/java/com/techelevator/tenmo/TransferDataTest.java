package com.techelevator.tenmo;

import com.techelevator.tenmo.models.TransferData;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferDataTest {


    @Test
    public void can_create_new_transfer_data(){
        TransferData testTransferData = new TransferData();
        BigDecimal testAmount = new BigDecimal(100.00);
        int testTransferTypeId = 2;
        int testAccountFrom = 1111;
        int testAccountTo = 1222;
        int testStatusId = 2;
        int testTransferId = 3010;
        String testUserNameTo = "dan";

        testTransferData.setAmount(testAmount);
        testTransferData.setTransferTypeId(testTransferTypeId);
        testTransferData.setAccountFrom(testAccountFrom);
        testTransferData.setAccountTo(testAccountTo);
        testTransferData.setStatusId(testStatusId);
        testTransferData.setTransferId(testTransferId);
        testTransferData.setGetUserNameTo(testUserNameTo);

        Assert.assertEquals(testAmount, testTransferData.getAmount());
        Assert.assertEquals(testTransferTypeId, testTransferData.getTransferTypeId());
        Assert.assertEquals(testAccountFrom,testTransferData.getAccountFrom());
        Assert.assertEquals(testAccountTo,testTransferData.getAccountTo());
        Assert.assertEquals(testStatusId, testTransferData.getStatusId());
        Assert.assertEquals(testTransferId, testTransferData.getTransferId());
        Assert.assertEquals(testUserNameTo, testTransferData.getUserNameTo());
    }
}
