package com.revature.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.DAOInterface.CustomerInterface;
import com.revature.model.Customer;
import com.revature.utility.BankLogger;
import com.revature.utility.DataPersistenceUtility;

public class CustomerImp implements CustomerInterface {

	public CustomerImp() {
		super();
	
	}
   
private static ArrayList<Customer> customerList = new ArrayList<>();
    
	
	
	//fill account list from DB
	
	public static void fillCustomerList(){
		customerList.clear();
		
		String sql= "select customer_id, first_name, last_name, is_active, user_name, "
				+ "password, address, phone_number from project0.customer";
		
		try {
			int id;
			String fnm, lnm,un, pw, add, phnum; 
			boolean act;
			//list to hold account holders 
			
			ResultSet rs = DataPersistenceUtility.getResultSet(sql);
			while (rs.next()) {
			id= rs.getInt("customer_id");
			fnm= rs.getString("first_name");
			lnm = rs.getString("last_name");
			un = rs.getString("user_name");
			pw = rs.getString("password");
			add = rs.getString("address");
			phnum = rs.getString("phone_number");
			act = rs.getBoolean("is_active");
			
					
						//select where statement
			//create new customer 
			Customer cx = new Customer(fnm, lnm, un, pw, act,id, add, phnum);
			//add new customer to the list 
			customerList.add(cx);
						   }
				} catch (SQLException e) {
			System.out.println("Check customerList SQL"  +e.getSQLState() + e.getMessage());
			e.printStackTrace();
		}
		
		}
			
        
   
       
	public  List<Customer> getCustomerList(){

				return customerList;
			}
	
	
	public Customer customerLookUp(String uName) {
		String Un= uName;
		for(Customer cx: getCustomerList())
			if(cx.getUserName().trim().equals(Un)) {
			 return cx;
		 }
		
		return null;
		
	}

	@Override
	public void createNewCustomer(Customer cx) {
	String sql = "Insert into project0.customer(first_name,last_name, user_name, password, is_active, "
			+ "address, phone_number) values (?,?,?,?,?,?,?)";	
		try {
			PreparedStatement stmnt = DataPersistenceUtility.makePrStmnt(sql);
			stmnt.setString(1, cx.getfName());
			stmnt.setString(2, cx.getlName());
			stmnt.setString(3, cx.getUserName());
			stmnt.setString(4, cx.getPassword());
			stmnt.setBoolean(5, cx.isActive());
			stmnt.setString(6, cx.getAddress());
			stmnt.setString(7, cx.getPhoneNumber());
			stmnt.executeUpdate();
			
			fillCustomerList();
			BankLogger.LogIt("info","A new customer was registered " + cx.getfName()+ " " +cx.getlName()+".");
		}catch(SQLException sqe){
	          System.out.println("Check Customer Insert SQL " + sqe.getSQLState() + sqe.getMessage());
	        } catch (Exception e) {
	            System.out.println("Error:" +e.getMessage());
	         }
	}

	@Override
	public void updateCustomer(Customer cx) {
		String sql= "UPDATE project0.customer SET  first_name = ?, last_name= ?, user_name =?," +
	            " password = ?, address = ? , phone_number = ? , is_active= ? WHERE customer_id = ?";
	        
	        try {
	            PreparedStatement stmnt = DataPersistenceUtility.makePrStmnt(sql);
	            stmnt.setString(1,cx.getfName());
	            stmnt.setString(2,cx.getlName());
	            stmnt.setString(3,cx.getUserName());
	            stmnt.setString(4,cx.getPassword());
	            stmnt.setString(5, cx.getAddress());
	            stmnt.setString(5, cx.getPhoneNumber());
	            stmnt.setInt(5,cx.getCustomerID());
	            
	            stmnt.executeUpdate();
	            
	            fillCustomerList();
	           
	         } catch(SQLException sqe){
	          System.out.println("Check Customer Update SQL " + sqe.getSQLState() + sqe.getMessage());
	        } catch (Exception e) {
	            System.out.println("Error:" +e.getMessage());
	         }
	    }
		
	

	@Override
	public void deactivateCustomer(Customer cx) {
		 String sql = "DELETE from project0.customer WHERE customer_id = ? ";
	     
	     try {
	    	 PreparedStatement stmnt = DataPersistenceUtility.makePrStmnt(sql);
	             stmnt.setInt(1,cx.getCustomerID());
	             stmnt.executeUpdate();
	             BankLogger.LogIt("info","A customer was deleted " + cx.getfName()+ " " +cx.getlName()+".");
	             fillCustomerList();
	        } catch(SQLException sqe){
	          System.out.println("Check Customer DELETE SQL " + sqe.getSQLState() + sqe.getMessage());
	        } catch (Exception e) {
	            System.out.println("Error:" +e.getMessage());
	         }
	}
	
	
	
	
	
	
 }
