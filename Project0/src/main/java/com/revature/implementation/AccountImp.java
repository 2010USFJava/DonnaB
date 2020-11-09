package com.revature.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.utility.BankLogger;
import com.revature.utility.DataPersistenceUtility;

public class AccountImp {
	
	
	private static ArrayList<Account> accountList = new ArrayList<>();
    
	//get Account list from file
	public static List<Account> getAccountList(){

		return accountList;
	}


	// update Accounts
	public static void setAccountList(List<Account> acctList) {
		AccountImp.accountList = (ArrayList<Account>) acctList;
	}

	public static int assignAccountNumber() {
		 int id =0;
        List<Integer> tempList = new ArrayList<>();
        //make sorted list of IDs
       getAccountList().forEach((acct) -> {
       	tempList.add(acct.getAccountID());
       	
       }); 
       Collections.sort(tempList); 
             
      
      //set first ID
       if (tempList.get(0)!=10000){
           id = 10000;
           System.out.println(id);
           return id;
       } else{ //set to next available ID
        id = tempList.size()+10000;
       
      return id;
       }
	}
       
	
	
	
	public static boolean accountLookUp(int accountNum, String accountHolder){
		for (Account account : accountList) {
			int aNum = account.getAccountID();
			
			List<String> nameList = account.getAccountHolders();
			if (aNum== accountNum){
				for (String name : nameList) {
					
					if (name.equalsIgnoreCase(accountHolder)) {
						return true;
					}
				}
			}
		}
		
		return false;
		
		
		
	}
	public static Account accountLookUpByNum(int accountNum) {
	  for(Account account : accountList)
		  if (account.getAccountID() == accountNum) {
			  return account;
		  }
	  
		
		
		return null;
	
	}


	public static void deposit(double amt, Account acct) {
		Account account = acct ;
		double amount = amt;
		double newBalance = account.getBalance() + amount;
		account.setBalance(newBalance);
		DataPersistenceUtility.writeUtility(AccountImp.getAccountList(), DataPersistenceUtility.getAccountfile());
		System.out.println("Your new balance is " + account.getBalance());
		BankLogger.LogIt("info", "Deposit in the amount of " +amt+ " to " +acct.getAccountID()+ "was successful."); 
	}


	public static void withdrawal(double amt, Account acct) {
		Account account = acct ;
		double amount = amt;
		double newBalance = account.getBalance() - amount;
		account.setBalance(newBalance);
		DataPersistenceUtility.writeUtility(AccountImp.getAccountList(), DataPersistenceUtility.getAccountfile());
		System.out.println("Your new balance is " + account.getBalance());
		BankLogger.LogIt("info","Withdrawal of " +amt+ " from " +acct.getAccountID()+" was successful.");
	}


	public static void transferFunds(double amt, Account from, int to) {
		double amount = amt;
		Account fromAccount = from;
		Account toAccount= accountLookUpByNum(to);
		if (toAccount != null) {
			double newBalance1= fromAccount.getBalance()-amount;
			double newBalance2 = toAccount.getBalance() + amount;
		fromAccount.setBalance(newBalance1);
		toAccount.setBalance(newBalance2);
		DataPersistenceUtility.writeUtility(AccountImp.getAccountList(), DataPersistenceUtility.getAccountfile());
		System.out.println("Your new balance in  " +toAccount.getAccountID() + " is "+ toAccount.getBalance());
		System.out.println("Your new balance in  " + fromAccount.getAccountID()+ "is" +  fromAccount.getBalance());
		BankLogger.LogIt("info", "Transfer from " +fromAccount.getAccountID()+" to "+toAccount.getAccountID()+ " in the amount of " +amt+"was successful.");
	}
	}


	public static void addAccountHolder(Account acct, Customer cx) {
		Account account = acct;
		String acctHolder = cx.getUserName();
		account.getAccountHolders().add(acctHolder);
		DataPersistenceUtility.writeUtility(AccountImp.getAccountList(), DataPersistenceUtility.getAccountfile());
		System.out.println("Account Holders have been updated: " + account.getAccountHolders());
		BankLogger.LogIt("info", "Account Holders have been updated: " + account.getAccountHolders());
	}


	public static void approvePendingAccount(Account acct) {
		Account account = acct;
		account.setPending(false);
		account.setDeactivated(false);
		DataPersistenceUtility.writeUtility(AccountImp.getAccountList(), DataPersistenceUtility.getAccountfile());
		System.out.println("Account " +account.getAccountID()+ " is active and no longer pending!");
		BankLogger.LogIt("info", "Account " +account.getAccountID()+ " has been approved." );
		
	}


	


}