package com.revature.model;

import java.io.Serializable;

import com.revature.implementation.EndUserImp;
import com.revature.utility.BankLogger;
import com.revature.utility.DataPersistenceUtility;

public class Employee extends EndUser implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = -5170173724624937855L;

private int employeeID;
private String position;
public Employee() {
	super();
}


public Employee(String fName, String lName, String userName, String password, boolean isActive,  int employeeID, String position) {
	super(fName, lName, userName, password, isActive);
	this.employeeID = employeeID;
	this.position = position;
	
	
}


public Employee(int employeeID,  String position) {
	super();
	this.employeeID = employeeID;
	this.position = position;
	
}
public int getEmployeeID() {
	return employeeID;
}
public void setEmployeeID(int employeeID) {
	this.employeeID = employeeID;
}

public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
@Override
public String toString() {
	return "EmployeeID: " + employeeID +  ", position: " + position + ".\n";
}
 
}
