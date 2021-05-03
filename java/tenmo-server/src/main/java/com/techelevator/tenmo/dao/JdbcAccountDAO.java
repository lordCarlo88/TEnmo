package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDAO implements AccountDAO{

    private UserDAO userDAO;
    private JdbcTemplate template;

    public JdbcAccountDAO(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public BalanceData getBalanceGivenAnId(int id){
        String sql = "SELECT balance from accounts where user_id = ?";
        SqlRowSet rowSet = template.queryForRowSet(sql, id);

        BalanceData balanceData = new BalanceData();
        if(rowSet.next()){
            String balance = rowSet.getString("balance");
            BigDecimal balanceBD = new BigDecimal(balance);
            balanceData.setBalance(balanceBD);
        }

        return balanceData;
    }

    @Override
    public TransferData transferFunds(int fromId, int toId, BigDecimal amount) {

        // Get current balance from transfer account
        BalanceData balanceFrom = getBalanceGivenAnId(fromId);
        BigDecimal currentBalanceFrom = balanceFrom.getBalance();

        // get account ids
        String sqlAccountIds = "select account_id from accounts where user_id = ?";
        SqlRowSet sqlRowSet1 = template.queryForRowSet(sqlAccountIds, fromId);

        int accountFromId = 0;
        if(sqlRowSet1.next()){
            accountFromId = sqlRowSet1.getInt("account_id");
        }
        String sqlAccountIds2 = "select account_id from accounts where user_id = ?";//not trying to select?
        SqlRowSet sqlRowSet2 = template.queryForRowSet(sqlAccountIds2, toId);
        int accountToId =0;
        if(sqlRowSet2.next()){
            accountToId = sqlRowSet2.getInt("account_id");
        }

        // Create transfer object
        TransferData transferData = new TransferData();
        transferData.setAmount(amount);
        transferData.setTransferTypeId(2); // send = 2
        transferData.setAccountTo(accountToId);// cause of error, doesn't throw error when you hardcode the account ID.
        transferData.setAccountFrom(accountFromId);
        //transferData.setTransferId(nextId);

        // Check for sufficient funds
        if( currentBalanceFrom.compareTo(amount) != -1){
            BigDecimal newBalanceFrom = currentBalanceFrom.subtract(amount);

            // update account sending balance
            String sqlFrom = "UPDATE accounts SET balance = ? WHERE user_id = ?";
            template.update(sqlFrom,newBalanceFrom, fromId);

            // update account receiving balance
            String sqlTo = "UPDATE accounts SET balance = balance + ? WHERE user_id =?";
            template.update(sqlTo,amount,toId);

            transferData.setStatusId(2); // approve = 2
        } else {
            transferData.setStatusId(3); // rejected = 3
            System.out.println("Not enough money FOOL!");
        }

        String sqlTransfer = "INSERT INTO transfers (transfer_type_id,transfer_status_id,account_from,account_to,amount) VALUES(?,?,?,?,?)";
        template.update(sqlTransfer, transferData.getTransferTypeId(),transferData.getStatusId(),transferData.getAccountFrom(), transferData.getAccountTo(),transferData.getAmount());

        return transferData;
    }

    @Override
    public List<Account> getUsers(){
        String sql = "select username, accounts.account_id, users.user_id from users join accounts on accounts.user_id = users.user_id;";
        List<Account> accountList = new ArrayList<>();

        SqlRowSet results = template.queryForRowSet(sql);

        while(results.next()){
            int accountId = results.getInt("account_id");
            int userId = results.getInt("user_id");
            String userName = results.getString("username");

            Account account = new Account(accountId,userId,userName);
            accountList.add(account);

        }
        return accountList;
    }

    @Override
    public List<TransferData> getTransfers(int correspondingUserId){
        // get account ids
        String sqlAccountIds = "select account_id from accounts where user_id = ?";
        SqlRowSet sqlRowSet1 = template.queryForRowSet(sqlAccountIds, correspondingUserId);

        int accountFromId = 0;
        if(sqlRowSet1.next()){
            accountFromId = sqlRowSet1.getInt("account_id");
        }

        String sql = "select users.username, transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount  \n" +
                "from transfers \n" +
                "join accounts on accounts.account_id = transfers.account_to \n" +
                "join users on users.user_id = accounts.user_id \n"+
                "where account_from = ? OR account_to = ?";
        List<TransferData> transfers = new ArrayList<>();
        SqlRowSet results = template.queryForRowSet(sql,accountFromId,accountFromId);

        while(results.next()){
            int transferTypeId = results.getInt("transfer_type_id");
            int transferStatusId = results.getInt("transfer_status_id");
            int accountFrom = results.getInt("account_from");
            int accountTo = results.getInt("account_to");
            BigDecimal amount = results.getBigDecimal("amount");
            int transferId = results.getInt("transfer_id");
            String userNameTo = results.getString("username");

            TransferData transferData = new TransferData();
            transferData.setTransferTypeId(transferTypeId);
            transferData.setStatusId(transferStatusId);
            transferData.setAccountFrom(accountFrom);
            transferData.setAccountTo(accountTo);
            transferData.setAmount(amount);
            transferData.setTransferId(transferId);
            transferData.setGetUserNameTo(userNameTo);

            transfers.add(transferData);

        }
        return transfers;
    }


}
