package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.JdbcAccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class ApplicationController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    UserDAO userDAO;


    @RequestMapping(path="/get-balance", method = RequestMethod.GET)
    public BalanceData processBalanceRequests(Principal principal){
        System.out.println("requesting balance");

        int correspondingUserId = userDAO.findIdByUsername(principal.getName());
        BalanceData balanceObject = accountDAO.getBalanceGivenAnId(correspondingUserId);

        return balanceObject;
    }

    @RequestMapping(path="/transfer", method= RequestMethod.POST)
    public TransferData processTransfer(@RequestBody TransferData transferData, Principal principal ){
        System.out.println("requesting transfer");
        System.out.println(transferData.getUserNameTo());
        System.out.println(transferData.getAmount());
        int correspondingUserId = userDAO.findIdByUsername(principal.getName());
        int userIdTo = userDAO.findIdByUsername(transferData.getUserNameTo());
        TransferData transferData1 = accountDAO.transferFunds(correspondingUserId, userIdTo,transferData.getAmount() );

        return transferData1;
    }

    @RequestMapping(path="/transfer", method= RequestMethod.GET)
    public List<TransferData> processTransfer(Principal principal ){
        int correspondingUserId = userDAO.findIdByUsername(principal.getName());
        List<TransferData> transferData = accountDAO.getTransfers(correspondingUserId);

        return transferData;
    }


    @RequestMapping(path = "/getUsers", method = RequestMethod.GET)
    public List<Account> getUsers(){
                List<Account> accounts = accountDAO.getUsers();
                System.out.println("list of users");
                return accounts;
           }
}
