package com.example.web.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.service.ServiceInterface;
import com.example.web.user.User;

@RestController
public class AdminController {

	private  final String username_="Nitin";
	private final String password_="1234";
	
	@Autowired
	private ServiceInterface service;
	
	@GetMapping("/get-user")
	public ResponseEntity<?> getAllUsers(@RequestHeader(value = "username")String username, @RequestHeader(value = "password")String password ) {
		
		if(!username.equals( username_)  || !password.equals(password_)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}
		List<User>accounts=service.getAllAccount();
	 return	 ResponseEntity.ok(accounts); 

	}
	
	@PostMapping("/insert-user")
	public ResponseEntity<String> insert(@RequestBody User user, @RequestHeader(value = "username")String username, @RequestHeader(value = "password")String password) {
		if(!username.equals( username_)  || !password.equals(password_)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}
		
		
		
		if(user.getInterestRate()==0) {
			
		     if(user.getTypeOfAccount().equalsIgnoreCase("saving")){
				user.setInterestRate(5.2);
			
			}
		else {
			user.setInterestRate(2.1);
			System.out.println("erog");
		}
		}
		
		if(user.getTypeOfAccount().equalsIgnoreCase("saving") || user.getTypeOfAccount().equalsIgnoreCase("current")) {
			service.insert(user);
		}
		else {
			return ResponseEntity.badRequest().body("INVALID ACCOUNT TYPE");
		}
		
		
		
	
		
		
		return ResponseEntity.ok("ACCOUNT CREATE SUCCESSFULLY");
	}
	
	
	@DeleteMapping("/delete-user/{AccountNumber}")
	public ResponseEntity<String> delete(@PathVariable("AccountNumber") int Ac, @RequestHeader(value = "username")String username, @RequestHeader(value = "password")String password) {
		
		service.delete(Ac);
		return ResponseEntity.ok("ACCOUNT DELETE SUCCESSFULLY"); 
	}
	
	@PutMapping("/update-account")
	public ResponseEntity<String> UpdateAccount(User user, @RequestHeader(value = "username")String username, @RequestHeader(value = "password")String password){
		
		service.UpdateAccount(user);
		
		return ResponseEntity.ok("ACCOUNT UPDATE SUCCESSFLLY");
	}
	
	@PutMapping("/calculate-interest/{accountNumber}")
public ResponseEntity<?> CalculateInterest(@PathVariable("accountNumber") long accountNumber){
	
		boolean success =service.calculateInterest(accountNumber);
		
		if(success) {
		User user=service.getAccount(accountNumber);
		
		  return ResponseEntity.ok(Map.of(
                  "status", "Interest calculated successfully",
                  "accountType", user.getTypeOfAccount(),
                  "interestRate", user.getInterestRate() + "%",
                 // "interestAmount", "$" + interestAmount,
                  "newBalance", "$" + user.getBalance()
              ));
          } else {
              return ResponseEntity.badRequest().body("Interest calculation failed");
          }

		}
}
	
	

