package com.example.web.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RestController;

import com.example.web.service.ServiceInterface;

import com.example.web.user.User;

@RestController
public class Controller {
	
	@Autowired
	private ServiceInterface service;
	
	@PutMapping("/withdraw/{accountNumber}/{amount}")
	public ResponseEntity<String> withDraw(@PathVariable("accountNumber") long accountNumber, @PathVariable("amount") double amount) {
	    double balance = service.withDraw(accountNumber, amount);
	    
	  
	    return ResponseEntity.ok("WITHDRAW SUCCESSFULLY \n" + "UPDATED BALANCE IS: " + balance);
	}
	
	
	@PutMapping("/deposit/{accountNumber}/{amount}")
	public ResponseEntity<String> deposit(@PathVariable("accountNumber") long accountNumber, @PathVariable("amount") double amount) {
	    double balance = service.deposit(accountNumber, amount);
	    
	  
	    return ResponseEntity.ok("DEPOSIT SUCCESSFULLY \n" + "UPDATED BALANCE IS: " + balance);
	}

	@PutMapping("/money-tranfer/{ac1}/{amount}/{ac2}")
	public ResponseEntity<String> moneytransfer(@PathVariable("ac1") long ac1, @PathVariable("amount") double amount, @PathVariable("ac2") long ac2){
		
		service.moneyTransfer(ac1, amount, ac2);
		
		return ResponseEntity.ok("TRANSACTION SUCCESSFULL");
	}
	
	@GetMapping("/get-AccountDetails/{Ac-number}")
	public User getDetails( @PathVariable("Ac-number") int Ac_number, @RequestHeader(value = "username")String username, @RequestHeader(value = "password")String password) {
		
		
		return service.getAccount(Ac_number);
	}
	

}
