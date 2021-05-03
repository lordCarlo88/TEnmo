package com.techelevator.tenmo;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.TransferData;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;
import io.cucumber.core.gherkin.ScenarioOutline;

import java.math.BigDecimal;
import java.util.Scanner;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountService accountService;

    public static void main(String[] args) {
    	App app = new App(new AccountService(API_BASE_URL),new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(AccountService accountService, ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();

			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		accountService.getBalance(currentUser.getToken());

	}

	private void viewTransferHistory() {
    	TransferData[] transfers =  accountService.getTransfers(currentUser.getToken());

//    	String[] transferStrings = new String[transfers.length];
		System.out.println("---------------------------------");
		System.out.println("Transfer        To               ");
		System.out.println("Id              ID         Amount");
		System.out.println("---------------------------------");

    	for(int i=0;i<transfers.length;i+=1){
			System.out.printf("%-15s %-5s %10s %n",transfers[i].getTransferId(), transfers[i].getAccountTo(), transfers[i].getAmount());
//    		String x = transfers[i].getTransferId() +"		"+ transfers[i].getAccountTo()+ "		"+ transfers[i].getAmount();
//    		transferStrings[i]=x;
		}

		Scanner userInput = new Scanner(System.in);
		System.out.println("Please enter a transfer ID:");
    	String idString = userInput.nextLine();
    	int transferId = Integer.parseInt(idString);

    	for (TransferData transferData : transfers){
    		if(transferData.getTransferId()==transferId){
				System.out.println(transferData);
			}
		}




//    	Object transfer = console.getChoiceFromOptions(transferStrings);
//		System.out.println(transfer);
//
//		if(transfer.)

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		Account[] accounts = accountService.listAccounts(currentUser.getToken());
		Account[] accounts2 = new Account[accounts.length-1];

		System.out.println("Choose an account to transfer to");
		System.out.println("---------------------------");
		System.out.println("UserId          UserName   ");
		System.out.println("---------------------------");

		int j = 0;
		for (int i =0;i<accounts.length;i+=1){

			if(accounts[i].getUserId()!=currentUser.getUser().getId()) {
				accounts2[j] = accounts[i];
				j++;
			}
		}

		Object selectedAccount = console.getChoiceFromOptions(accounts2);
		String selectedAccountString = selectedAccount.toString();

		String amountToSent = console.getUserInput("Enter amount to send");
		BigDecimal amountBD = new BigDecimal(amountToSent);

		if(accountService.getBalance(currentUser.getToken()).getBalance().compareTo(amountBD)!=-1) {

			accountService.makeTransfer(currentUser.getToken(), selectedAccountString, amountBD);
		}else{
			System.out.println("NOT ENOUGH MONEY FOOL!");
		}
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
