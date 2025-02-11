package com.revature.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revature.implementation.AccountImp;
import com.revature.utility.BankLogger;
import com.revature.utility.DataPersistenceUtility;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4943565665674681429L;
	private int accountID;
	private double balance;
	private boolean isDeactivated;
	private boolean isPending;
	
	
	public Account() {
		super();
		
	}
	
		
	public Account(int accountID, double balance, boolean isDeactivated, boolean isPending) {
		super();
		this.accountID = accountID;
		this.balance = balance;
		this.isDeactivated = isDeactivated;
		this.isPending = isPending;
		
		
		
	}
	
	public Account( double balance, boolean isDeactivated, boolean isPending) {
		super();
		
		this.balance = balance;
		this.isDeactivated = isDeactivated;
		this.isPending = isPending;
		
	}
	
	public boolean isDeactivated() {
		return isDeactivated;
	}
	
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}


	public boolean isPending() {
		return isPending;
	}


	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}


	


	public void setDeactivated(boolean isDeactivated) {
		this.isDeactivated = isDeactivated;
	}


	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", balance=" + balance + ", Deactivated= " + isDeactivated +", isPending= "+ isPending
				+ " ]";
	}
	
	
	
	
}
