package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.TransferData;
import io.cucumber.java.bs.A;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    AuthenticatedUser authenticatedUser = new AuthenticatedUser();

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private String AUTH_TOKEN = authenticatedUser.getToken();

    public AccountService(String url) {
        this.baseUrl = url;
    }

    public Account getBalance(String token){
        Account account = null;

        account = restTemplate.exchange(baseUrl+"/get-balance", HttpMethod.GET,makeAuthEntity(token),Account.class).getBody();

        System.out.println("****************");
        System.out.println("Your current account balance is: $ "+ account.getBalance());
        System.out.println("****************");

    return account;
    }

    public Account[] listAccounts(String token){
        Account[] accounts = restTemplate.exchange(baseUrl+"/getUsers", HttpMethod.GET,makeAuthEntity(token),Account[].class).getBody();

        return accounts;
    }

    public TransferData[] getTransfers(String token){
        TransferData[] transferData = restTemplate.exchange(baseUrl+"/transfer",HttpMethod.GET,makeAuthEntity(token),TransferData[].class).getBody();
        return transferData;
    }

    public TransferData makeTransfer(String token, String selectedAccount, BigDecimal amountBD){
        TransferData transferData = new TransferData();
        transferData.setGetUserNameTo(selectedAccount);
        transferData.setAmount(amountBD);

        TransferData response = restTemplate.postForObject(baseUrl + "/transfer", makeTransferEntity(token, transferData), TransferData.class);

        return response;
    }

    private HttpEntity<TransferData> makeTransferEntity(String token, TransferData transferData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<TransferData> entity = new HttpEntity<>(transferData, headers);
        return entity;
    }

    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
