package com.revature.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.Banking.MainDriver;
import com.revature.implementation.AccountImp;
import com.revature.implementation.CustomerImp;
import com.revature.implementation.EmployeeImp;
import com.revature.implementation.EndUserImp;
import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.Employee;
import com.revature.model.EndUser;
import com.revature.utility.BankLogger;
import com.revature.utility.DataPersistenceUtility;

//record input 
public class ConsoleMenu {
 private  String errMessage= " Please enter a valid value for each field.";
 private  Scanner input = new Scanner(System.in);
 private AccountImp accountImp= new AccountImp();
 private CustomerImp customerImp= new CustomerImp();
 private EmployeeImp employeeImp = new EmployeeImp();
 private final  String TABS = "\t\t\t";
 // Welcome & login menu
 public  void startMenu(){
	
	 try {
		prt("What would you like to do?");
		 int decision;
		 prt("[1] Log in?");
		 prt("[2] Register?");
		 
		 decision = Integer.parseInt(input.nextLine());
		 if (decision== 1) {
			 login();
		 }else if (decision==2) {
			 EndUser tempEu = new Customer();
			 customerRegister(tempEu);
		 
		 }else {
			 prt("Please enter 1 for login or 2 to register.");
			 startMenu();
		 }
	} catch (NumberFormatException e) {
		prt("Please enter a number.");
		startMenu();
		
	}
			 
	 
 }
	 
 public  void login(){
   try {
	String userName;
	String password;
	prt("Please enter your User Name");
	userName= input.nextLine();
	prt("Please enter your password.");
	password= input.nextLine();
	
	EndUser currentEndUser = EndUserImp.authenticateEndUser(userName, password);
	
		if (currentEndUser!= null) {
			mainMenu(currentEndUser);
			
			 } else {
				 startMenu();
			 }
			  
				 
		
} catch (NullPointerException e) {
	prt(errMessage);
	
		startMenu();
}
}

 public  void mainMenu(EndUser currentEU) {
	   
			 if(currentEU instanceof Customer) {
				 customerDetails((Customer) currentEU);
			 }
			 else if(currentEU instanceof Employee) {
				 adminMenu((Employee) currentEU);
				  } else {
					 prt("\tError: an unexpected error has occured.");
					 startMenu();
   }
	 
 }

 public  void customerRegister(EndUser eu){
	
	String uName;
	String password;
	String fName ;
	String lName;
	String phNum;
	String stAdd;
	String cityAdd;
	 int zpAdd = 0;
	try {
		
		  prt("\tPlease enter first name");
		 fName = input.nextLine();
		 prt("\tPlease enter last name.");
		 lName = input.nextLine();
		do { prt("\tPlease enter a user name ");
		  uName=input.nextLine();}
		 while( EndUserImp.verifyUserNameUnique(uName));
		  
		 password = confirmPassword();
		 prt("\tPlease enter a phone number");
		 phNum = input.nextLine();
		 prt("\tPlease enter a street Address");
		 stAdd = input.nextLine();
		 prt("\tPlease enter a city");
		 cityAdd = input.nextLine();
		 prt("\tPlease enter a Zipcode");
		 try {zpAdd = Integer.parseInt(input.nextLine());}
		 catch (NumberFormatException e) {
			 prt(" Please input a 5 digit number");
			 zpAdd = Integer.parseInt(input.nextLine());}
		 
		 
		 String add = stAdd + ","+ cityAdd+ ","+ zpAdd;  
		 Customer a = new Customer(fName, lName,  uName, password, true,0, add, phNum);
		 
		 customerImp.createNewCustomer(a);
		 a= customerImp.customerLookUp(a.getUserName());
		 accountRequest(a);
		
	  
	} catch (NullPointerException e) {
	 prt(errMessage);
		startMenu();
	}
 }
	 
 
// customer menus
 
 
 //look up account verifying account holder and account number
 public  void viewcreateAccount(Customer eu){

	try {	
		
		prt("[1] Access existing account? ");
		prt("[2] Apply for an account? ");
		
	int decision = Integer.parseInt(input.nextLine());
	 if (decision == 1) {
				 prt("\tPlease enter your account number");
				 int accountNum = Integer.parseInt(input.nextLine());
				 Account a = AccountImp.accountLookUpByNum(accountNum);
				 accountDetails(a,eu);
			   
			 	 
	 } else {
		 accountRequest(eu);
	 }
					
				 
 
		 
	 } catch (NumberFormatException e) {
		 prt("\tPlease enter a number.");
		 mainMenu(eu);

	 } catch (NullPointerException e) {
		 prt(errMessage);
		 mainMenu(eu);
		 e.printStackTrace();
	 }

 }
	 

public  void updateCustomerDetails(EndUser eu) {
	prt("[1] Update Customer Details? ");
	prt("[2] View account or make a transaction?");
    prt("[3] Apply for new account?");
	prt("[4] Delete Account?");
	prt("[5] Log out?");
	try {
		String decision = input.nextLine();
		 if (decision.contains("1")){
			 prt("\n\n \t\t Choose an option to update!");
			    prt("\t[1]  Password? ");
				prt("\t[2]  Address?");
			    prt("\t[3]  Phone Number?");
				prt("\t[4]  Go Back? ");
				prt("\t[5]  Log out?");
			 
			 String decision1 = input.nextLine();
			  switch (decision1) {
			  case "1"://update password
				  eu.setPassword(confirmPassword());
				  prt("Password has been updated");
				  updateCustomerDetails(eu);
			  break;
			  case "2"://update address
				  prt("What is your new street address?");
				  String stAdd = input.nextLine();
				  prt("New City?");
				  String city = input.nextLine();
				  prt("New Zipcode?");
				  String zp = input.nextLine();
				  ((Customer) eu).setAddress(stAdd + ", "+ city+", "+ zp);
				  prt("Your address has been updated!");
				  updateCustomerDetails(eu);
			  break;
			  case "3"://update phone number
				  prt(" What is your New phone number?");
				  String phNum= input.nextLine();
				  ((Customer) eu).setPhoneNumber(phNum);
				  prt("Your phone number has been updated");
				  updateCustomerDetails(eu);
			  break;
			  case "4":
				  mainMenu(eu);
				  break;
			  case "5":
				  startMenu();
				  break;
			  default:
				  prt("\tPlease enter 1 to update password, 2 to update address or 3 to update phone number.");
				  startMenu();
			  }
		 }else if (decision.contains("2")){
			prt("Enter account number:");
			int acctNum= Integer.parseInt(input.nextLine());
			Account a = AccountImp.accountLookUpByNum(acctNum);
			 accountDetails(a,eu);
		 }
		 else if (decision.contains("3")) {
			 accountRequest((Customer) eu);
		 }else if (decision.contains("4")) {
			 
			 prt("Enter account number:");
				int acctNum= Integer.parseInt(input.nextLine());
				Account a = AccountImp.accountLookUpByNum(acctNum); 
				accountImp.deactivateAccount(a);
				mainMenu(eu);
			 }
		 else{ startMenu();
		 }
	} catch (NumberFormatException e) {
		prt(errMessage);
		mainMenu(eu);
	}
}

public  void updateAccountTransactions(Account account, EndUser currentUser) {
	Account acct = account;
	EndUser eu = currentUser;
	prt("\t Please choose an option.");
	
	try {
		prt("\t[1] Deposit funds?");	//deposit
		prt("\t[2] Withdraw Funds?");//withdrawal
		prt("\t[3] Go back to main menu?");
		
		int decision = Integer.parseInt(input.nextLine());
		double amt;
		 switch (decision ) {
		 case (1):
			 prt("\t Enter amount to deposit.");
		 		amt = Double.parseDouble(input.nextLine());
		 		if(verify(amt)) {
		 		accountImp.deposit(amt, account);
		 		updateAccountTransactions(acct, eu);
		 		} else { prt(errMessage);
		 		  mainMenu(eu);
		 		}
			 break;
		 case(2):
			 prt("\t Enter amount to withdraw.");
		 		amt = Double.parseDouble(input.nextLine());
		 		if(verify(amt)) {
		 			if(amt > acct.getBalance()) {
		 				System.out.println("\t\t\t\tWARNING!! \n"
		 						+ "\t\t\tThe amount you are withdrawing: "+ amt+
		 						" is more than your balance of: " +acct.getBalance()+ "\n\t\t\t\t\t An overdraft fee in the amount of 35.00 will apply!!!!");
		 						System.out.println("\t\t\t Would you like to proceed? [1] yes or [2] no");
		 							int yesNo = Integer.parseInt(input.nextLine());
		 						if (yesNo==1) {
		 							amt =amt+35.00;
		 						}else { mainMenu(eu);
		 				}
		 			}
		 		accountImp.withdrawal(amt, account);
		 		updateAccountTransactions(acct, eu);
		 		} else { prt(errMessage);
		 		  mainMenu(eu);
		 		}
		 	 break;
			 case(3):
			 mainMenu(eu);
		 	break;
			default: 
				 mainMenu(eu);
		 }
		
		
	} catch (NullPointerException e) {
		prt(errMessage);
		 updateAccountTransactions(acct, eu);
	}catch (NumberFormatException e) {
		prt("Please enter 1 to deposit funds, 2 to withdraw funds");
		 updateAccountTransactions(acct, eu);
	}catch (Exception e) {
		mainMenu(eu);
	}

	
	
}

public void accountRequest(Customer eu) {
	  prt("What type of account would you like? ");
	  prt("[1] Checking \t [2] Savings");
	
	try {
		int  acctChoice = Integer.parseInt(input.nextLine());
		prt("What is the initial deposit?");
		double deposit = Double.parseDouble(input.nextLine());
		Account a = new Account(deposit, false, true);
		accountImp.createNewAccount(a);
		accountImp.insertAccountLookup(eu,a);
		prt("customer " + eu.getfName() +" " +eu.getlName()+ acctChoice+ a.toString());
	} catch (NumberFormatException e) {
		prt("Please enter a number.");
		accountRequest(eu);
	}
	
	
	
	mainMenu(eu);
}
 
public  void adminMenu(Employee admin) {
		 
		prt("Please choose an option.");
		prt("[1] Account Lookup");
		prt("[2] Customer Lookup");
		prt("[3] Employee Lookup ");
		prt("[4] Additional Admin Menus ");
		prt("[5] Log out");
		int decision = Integer.parseInt(input.nextLine());
		switch (decision) {
		case(1)://lookup account
		viewAllAccounts(admin);
		break;
		case(2): //lookup customer
		viewAllCustomers(admin);
		break;
		case(3):// lookup employees
		viewAllEmployees(admin);
		break;
		case(4):
			additionalAdminMenu(admin);
			break;
		
		default:
			startMenu();
			
		
		}
		
	}
 
 private  void additionalAdminMenu(Employee admin) {
	prt("Select appropriate option.");
	prt("\t[1] Edit Customer details");
	prt("\t[2] Delete Account");
	prt("\t[3] Edit Account details");
	prt("\t[4] Main Menu ");
	prt("\t[5] Log out");
	int decision = Integer.parseInt(input.nextLine());
	try {
		switch (decision) {
			case(1)://Edit customer Details
				prt("Please enter the User Name of the Customer.");
				String cxUserName = input.nextLine();
				Customer cx = customerImp.customerLookUp(cxUserName);
				if (cx != null) {
						editCxDetails(cx, admin);
				} else { prt("User name not found! Try again.");
						additionalAdminMenu(admin);
				}
			break;
			case(2): 
			
			break;
			case(3):// edit account details
			
			break;
			case(4):
				mainMenu(admin);
				break;
			case(5):
				startMenu();
				break;
			default:
				mainMenu(admin);
		}
	} catch (NullPointerException e) {
		prt("Please enter valid inputs.");
		mainMenu(admin);
		e.printStackTrace();
	}
	mainMenu(admin);
}

private  void editCxDetails(Customer cx, Employee admin) {
	prt("Select appropriate option.");
	prt("\t[1] Edit Customer name");
	prt("\t[2] Edit account number");
	prt("\t[3] Edit User Name");
	prt("\t[4] Reset password ");
	prt("\t[5] Edit Address /phone number ");
	prt("\t[6] deactivate Customer ");
	prt("\t[7] Log out");
	int decision = Integer.parseInt(input.nextLine());
	try {
		switch (decision) {
		case (1): //name
			prt("\t Enter the Customer new first name.");
			String fName = input.nextLine();
			cx.setfName(fName);

			prt("\t Enter the Customer new Last name.");
			String lName = input.nextLine();
			cx.setlName(lName);
			customerImp.updateCustomer(cx);
			prt("\t Name has been updated to " + cx.getfName() + " " + cx.getlName());
			editCxDetails(cx,admin);
			break;
		case (2): //Customer Id
			prt("\t Enter the Customer new ID.");
			int cxID = Integer.parseInt(input.nextLine());
			cx.setCustomerID(cxID);
			customerImp.updateCustomer(cx);
			prt("\t Customer ID has been updated to " + cx.getCustomerID());
			editCxDetails(cx,admin);
			break;
		case (3)://username
			prt("\t Enter the Customer new username.");
			String userName = input.nextLine();
			cx.setUserName(userName);
			customerImp.updateCustomer(cx);
			prt("\t User name has been updated to " + cx.getUserName());
			editCxDetails(cx,admin);
			break;
		case (4): //password
			prt("\t Enter the Customer password.");
			String password = input.nextLine();
			cx.setPassword(password);
			customerImp.updateCustomer(cx);
			prt("\t Password has been reset. ");
			editCxDetails(cx,admin);
			break;
		case (5): //address & phone number
			editCxDetails(cx,admin);
			break;
		case (6): //active
			prt("\t Are you sure you want to deactivate Customer? [Yes] or [No]");
			String yesNo = input.nextLine();
			if (yesNo.equalsIgnoreCase("yes")) {
				cx.setActive(false);
				//DataPersistenceUtility.writeUtility(EndUserImp.getEndUserList(),
					//	DataPersistenceUtility.getEnduserfile());
				prt("\t Customer is active is set to " + cx.isActive());
			}
			customerImp.updateCustomer(cx);
			editCxDetails(cx,admin);
			break;
		case (7): //Logout
			startMenu();
			break;
		default:
			mainMenu(admin);
		}
	} catch (NumberFormatException e) {
		prt("Please enter a NUMBER");

		mainMenu(admin);
		e.printStackTrace();
	} 
	
	
	
	
	
}

private  String confirmPassword() {
	   try {
		String p1;
		String p2;
		   do {prt("\tPlease enter a password");
			  p1 =input.nextLine();
			 prt("\tPlease confirm password");
			 p2 =input.nextLine();
			  } while (!p1.equals(p2) || p1.isBlank());
		   return p2;
	} catch (NullPointerException e) {
		prt(errMessage);
		confirmPassword();
	}
	return " ";
		  
   }

public  void accountLookUpMenu(Employee currentEmp) {
	try {
		int acctNum;
		Employee cEmp = currentEmp;
		prt("Please enter the account number to view details:");
		prt("Or [B]ack to main menu.");
		acctNum= Integer.parseInt(input.nextLine());
		Account account = accountImp.accountLookUpByNum(acctNum);
		
		accountDetails(account, cEmp);
	} catch (NumberFormatException e) {
		mainMenu(currentEmp);
		
		
	} catch (NullPointerException e) {
		prt("Please verify that the Account number is correct and try again.");
		accountLookUpMenu(currentEmp);
	}
}

public  void employeeAccountUpdate(Account acct, EndUser eu) {
	try {
		prt("\t[1] Deposit funds?");	//deposit
		prt("\t[2] Withdraw Funds?");//withdrawal
		prt("\t[3] Transfer funds");
		prt("\t[4] Delete account");
	    prt("\t[5] Approve a pending account?"); //approve pending accounts
		prt("\t[6]  Go back to main menu?");// cancel
		int decision = Integer.parseInt(input.nextLine());
		double amt;
		 switch (decision ) {
		 case (1):
			 prt("\t Enter amount to deposit.");
		 		amt = Double.parseDouble(input.nextLine());
		 		if (verify(amt)) {
		 		accountImp.deposit(amt, acct);
		 		employeeAccountUpdate(acct, eu);
		 		mainMenu(eu);
		 		} else { prt(errMessage);
		 		employeeAccountUpdate(acct, eu);
		 		}
		 		
			 break;
		 case(2):
			 prt("\t Enter amount to withdraw.");
		 		amt = Double.parseDouble(input.nextLine());
		 		if(verify(amt)) {
		 			if(amt > acct.getBalance()) {
		 				System.out.println("\t\t\t\tWARNING!! \n"
		 						+ "\t\t\tThe amount you are withdrawing: "+ amt+
		 						" is more than your balance of: " +acct.getBalance()+ "\n\t\t\t\t\t An overdraft fee in the amount of 35.00 will apply!!!!");
		 						System.out.println("\t\t\t Would you like to proceed? [1] yes or [2] no");
		 							int yesNo = Integer.parseInt(input.nextLine());
		 						if (yesNo==1) {
		 							amt =amt+35.00;
		 						}else { mainMenu(eu);
		 				}
		 			}
		 			
		 		accountImp.withdrawal(amt, acct);
		 		employeeAccountUpdate(acct, eu);
		 		} else { prt(errMessage);
		 		employeeAccountUpdate(acct, eu);
		 		}
			 break;
		 case (3):
			 
		 		
		 		mainMenu(eu);
		
			 break;
		 case(4):
			accountImp.deactivateAccount(acct);
		 employeeAccountUpdate(acct, eu);		 
			 break;
		 case(5):
			 accountImp.approvePendingAccount(acct);
		 	employeeAccountUpdate(acct, eu);
			 break;
		 
				default: 
				 mainMenu(eu);
		 }
		
		
	} catch (NullPointerException e) {
		prt("Please input a value.");
		employeeAccountUpdate(acct, eu);
	}catch (NumberFormatException e) {
		prt(errMessage);
		mainMenu(eu);
	}catch (Exception e) {
		prt("Ut Oh , you broke it. Start over");
		startMenu();
		e.printStackTrace();
	}
	
}

public  void cxLookUpMenu(Employee currentEmp) {
	try {
		String cxUserName;
		 
		prt("Please enter the Customer User Name to view details:");
		cxUserName = input.nextLine();
		Customer cx = customerImp.customerLookUp(cxUserName);
		 customerDetails(cx);
		
	} catch (NullPointerException e) {
		prt("User name not found.");
		
		mainMenu(currentEmp);
	}
	
}

public  void employeeLookUpMenu(Employee admin) {
	
	try {
		int employeeID;
		
		Employee administrator = admin;
		prt("Please enter the Employee ID to view details:");
		prt("Or enter [B]ack to return to the main menu.");
		employeeID = Integer.parseInt(input.nextLine());
		Employee employee = EmployeeImp.lookUpBYID(employeeID);
		 employeeDetails(employee, administrator);
		
	} catch (NullPointerException e) {
		prt("Please verify that the customer user name is correct and try again.");
		employeeLookUpMenu(admin);
	} catch (NumberFormatException e) {
		mainMenu(admin);
	}
	
}

public  void updateEmployeeDetails(Employee emp, Employee admin) {
	prt("[1] Update Employee?");
	 prt("[2] Back to main menu?");
	 prt("[3] Log out?");
	int choice = Integer.parseInt(input.nextLine());
 
	try {
		if (choice ==1) {
			
			employeeUpdate(emp);
			
			try {
				int decision = Integer.parseInt(input.nextLine());
			
				switch (decision) {
				case (1): //name
					prt("\t Enter the  Employee new first name.");
					String fName = input.nextLine();
					emp.setfName(fName);

					prt("\t Enter the Employee new Last name.");
					String lName = input.nextLine();
					emp.setlName(lName);
					employeeImp.updateEmployee(emp);
					prt("\t Name has been updated to " + emp.getfName() + " " + emp.getlName());
					updateEmployeeDetails(emp,admin);
					break;
				case (2): //position
					prt("\t Enter the  Employee new position.");
					String position = input.nextLine();
					emp.setPosition(position);
					employeeImp.updateEmployee(emp);
					prt("\t Position has been updated to " + emp.getPosition());
					updateEmployeeDetails(emp,admin);
					break;
				case (3)://username
					prt("\t Enter the Employee new username.");
					String userName = input.nextLine();
					emp.setUserName(userName);
					employeeImp.updateEmployee(emp);
					prt("\t User name has been updated to " + emp.getUserName());
					updateEmployeeDetails(emp,admin);
					break;
				case (4): //password
					prt("\t Enter the Employee password.");
					String password = input.nextLine();
					emp.setPassword(password);
					employeeImp.updateEmployee(emp);
					prt("\t Password has been reset. ");
					updateEmployeeDetails(emp,admin);
					break;
				case (5): 
					
					break;
				case (6): //active
					prt("\t Are you sure you want to deactivate Employee? [Yes] or [No]");
					String yesNo = input.nextLine();
					if (yesNo.equalsIgnoreCase("yes")) {
						emp.setActive(false);
						employeeImp.updateEmployee(emp);
						prt("\t Employee is active is  " + emp.isActive());
					}
					updateEmployeeDetails(emp,admin);
					break;
				case (7): 
					prt("\t Are you sure you want to delete Employee? [Yes] or [No]");
				String yesNo2 = input.nextLine();
				if (yesNo2.equalsIgnoreCase("yes")) {
					prt("\t Employee "+ emp.getUserName()+" has been deleted.");
					employeeImp.deactivateEmployee(emp);
				}
					break;
				default:
					mainMenu(admin);
				}
			} catch (NumberFormatException e) {
				prt("Please enter a NUMBER");

				mainMenu(admin);
				e.printStackTrace();
			} 
		} else if (choice ==3) {
			startMenu();
		}else {
			mainMenu(admin);
		}
	} catch (Exception e) {
		mainMenu(admin);
		
	}
}
  
public  boolean verify(double num) {
	   
	   if (num < 0) {
		   return false;
	   }
	   return true;
   }
  
private  void prt(String s) {
	   System.out.println("\t\t\t"+ s);
   }
   
private  void header(String header) {
		 System.out.println(TABS+ "****************************************************************");
		 System.out.println(TABS+TABS+ header );
		 System.out.println(TABS+"****************************************************************");
		}
		
private  void divider() {
			 System.out.println(TABS+"****************************************************************");
			 System.out.println("\n"+TABS+"****************************************************************");
			}
		

public  void customerDetails(Customer customer) {
		 Customer cx = customer;
		List <Integer> acctIDList= AccountImp.lookupAcctbyCxID(cx);
		 header("Customer Details");
		 System.out.println("");
		 System.out.println("");
		 System.out.println(TABS+ TABS+" Hello "+ cx.getfName());
		 System.out.println("");
		 System.out.println("");
		 System.out.println(TABS+"  Name: " + cx.getfName()+ " "+ cx.getlName()+ "\t Phone Number: " + cx.getPhoneNumber());
		 System.out.println("");
		 System.out.println("");
		 System.out.println(TABS+"  User Name: " +cx.getUserName());
		 System.out.println("");
		 System.out.println(TABS+"Account number(s) :"+ acctIDList);
		 System.out.println("");
		 System.out.println(TABS+" Address: " + cx.getAddress());
		 System.out.println("");
		 
		 System.out.println("");
		 System.out.println("");
		 
		 divider();
		 updateCustomerDetails(cx);
		}
		
		
public  void accountDetails(Account account, EndUser currentUser) {
			Account acct = account;
			EndUser eu= currentUser;
			 header("Account Details");
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+"Account Number "+ acct.getAccountID());
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+"Balance " + acct.getBalance());
			 System.out.println("");
			  if (acct.isPending()) {
				 System.out.println("\t\t\tACCOUNT IS PENDING!");
			 }else if (acct.isDeactivated()) {
				 System.out.println("\t\t\tACCOUNT IS NOT ACTIVE.");
			 } 
			 divider();
			if (eu instanceof Customer) {
		updateAccountTransactions(acct,eu);
			} else if (eu instanceof Employee) {
				employeeAccountUpdate(acct,eu);
			}
		}
		
		
		public  void viewAllAccounts(Employee currentEU) {
			header("All Accounts");
			
			AccountImp.getAccountList().forEach((acct) -> {
				prt(acct.toString() );
				divider();
			});
			accountLookUpMenu(currentEU);
			divider();
			
		}
		
		public void viewAllCustomers(Employee currentEmp) {
			header("All Customers");
			customerImp.getCustomerList().forEach((cx) -> {
				prt( " UserName: "+ cx.getUserName()+ ", Name: " + cx.getfName() +" " + cx.getlName()+",  "+ cx.toString()+ "\n");
				divider();		
			});
			cxLookUpMenu(currentEmp);
			
		}
		public  void viewAllEmployees(Employee admin) {
			header("All Employees");
			
			EmployeeImp.getEmployeeList().forEach((eu) -> {
				
					prt("UserName; " +eu.getUserName()+ ", Name: " + eu.getfName() +" " + eu.getlName()+", " +eu.toString() + "\n" );
				 divider();
			});
			employeeLookUpMenu(admin);
		}
		public  void employeeDetails(Employee employee, Employee admin) {
			Employee emp = employee;
			
			 header("Employee Details");
			 
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+"  Name: " + emp.getfName()+ " "+ emp.getlName()+ "\t Position: " + emp.getPosition());
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+"  User Name: " +emp.getUserName());
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+"  Employee ID: " + emp.getEmployeeID()+ "\t  Active =" + emp.isActive());
			 System.out.println("");
			 System.out.println("");
			 System.out.println("");
			 
			 divider();
			 
			 updateEmployeeDetails(emp, admin);
			}
		public  void employeeUpdate(Employee emp) {
			
			
			 header("Employee Update");
			 
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+"[1]  Name: " + emp.getfName()+ " "+ emp.getlName()+ "\t [2]Position: " + emp.getPosition());
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+" [3] User Name: " +emp.getUserName() + "\t [4]Reset password:");
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS+" [5] Employee ID: " + emp.getEmployeeID() + "\t [6] Active =" + emp.isActive());
			 System.out.println("");
			 System.out.println("");
			 System.out.println(TABS +"[7] Delete Employee");
			  divider();
			
		}
			
		}
   

