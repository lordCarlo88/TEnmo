package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

    public BalanceData getBalanceGivenAnId(int id);

    public TransferData transferFunds(int fromId, int toId, BigDecimal decimal);

    public List<Account> getUsers();

    public List<TransferData> getTransfers(int account);
}
