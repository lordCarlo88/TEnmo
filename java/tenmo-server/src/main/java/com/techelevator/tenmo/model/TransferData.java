package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferData {

    private BigDecimal amount;
    private int transferTypeId;
    private int accountFrom;
    private int accountTo;
    private int statusId;
    private int transferId;
    private String userNameTo;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getUserNameTo() {
        return userNameTo;
    }

    public void setGetUserNameTo(String getUserNameTo) {
        this.userNameTo = getUserNameTo;
    }
}
