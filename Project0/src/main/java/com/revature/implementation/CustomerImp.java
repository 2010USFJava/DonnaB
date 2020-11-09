package com.revature.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.revature.model.Customer;

public class CustomerImp {

	private CustomerImp() {
		super();
	
	}
   
	public static int assignID() {
		 int id =0;
         List<Integer> tempList = new ArrayList<>();
         //make sorted list of IDs
        EndUserImp.getEndUserList().forEach((eu) -> {
        	if (eu instanceof Customer) {
			Customer cx = (Customer) eu;
           tempList.add(cx.getCustomerID());
        	}
        }); 
        Collections.sort(tempList); 
              
       
       //if user deletes first ID
        if (tempList.get(0)!=1000){
            id = 1000;
            System.out.println(id);
            return id;
        } else{
         id = tempList.size()+1000;
        
       return id;
      
	}
        
   
       
}
	public static List<Customer> customerLookUpList() {
		List<Customer> tempList = new ArrayList<>();
		EndUserImp.getEndUserList().forEach((eu) -> {
			if (eu instanceof Customer) {
				Customer cx = (Customer) eu;
				tempList.add(cx);
			}
		});
		return tempList;   
	   }
	
	public static Customer customerLookUp(String uName) {
		String Un= uName;
		for(Customer cx: customerLookUpList())
			if(cx.getUserName().trim().equals(Un)) {
			 return cx;
		 }
		
		return null;
		
	}

	
	
	
	
 }
