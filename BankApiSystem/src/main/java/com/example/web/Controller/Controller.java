package com.example.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.TransactionRequest;
import com.example.web.service.ServiceInterface;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.dto.TransactionRequest;
import com.example.web.service.ServiceInterface;
import com.example.web.transaction.Transaction;
import com.example.web.user.User;
@RestController
@RequestMapping("/BankApiSystem")
public class Controller {

    @Autowired
    private ServiceInterface service;

	@GetMapping("/balance/{accountNumber}")
    public ResponseEntity<?> getBalance(@PathVariable("accountNumber") int accountNumber) {
        User user = service.getAccount(accountNumber);
        
        if (user == null) {
            return ResponseEntity.badRequest().body("Account not found");
        }
        
        return ResponseEntity.ok("Account Balance: $" + user.getBalance());
    }
    
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionRequest request) {
        try {
            boolean success = service.deposit(
                request.getAccountNumber(), 
                request.getAmount(),
                request.getDescription()
            );
            
            if (success) {
                return ResponseEntity.ok("Deposit successful. New balance: $" + 
                        service.getAccount(request.getAccountNumber().intValue()).getBalance());
            } else {
                return ResponseEntity.badRequest().body("Deposit failed");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
    
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionRequest request) {
        try {
            boolean success = service.withdraw(
                request.getAccountNumber(), 
                request.getAmount(),
                request.getDescription()
            );
            
            if (success) {
                return ResponseEntity.ok("Withdrawal successful. New balance: $" + 
                        service.getAccount(request.getAccountNumber().intValue()).getBalance());
            } else {
                return ResponseEntity.badRequest().body("Withdrawal failed");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	@GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<?> getTransactions(@PathVariable("accountNumber") Long accountNumber) {
        List<Transaction> transactions = service.getTransactionHistory(accountNumber);
        return ResponseEntity.ok(transactions);
    }
    // @GetMapping("/get-user")
    // public List<User> getAllUsers() {
    //     return service.getAllAccount();
    // }

    // @PostMapping("/insert-user")
    // public ResponseEntity<String> insert(@RequestBody User user) {
    //     service.insert(user);

    //     return ResponseEntity.ok("ACCOUNT CREATE SUCCESSFULLY");
    // }

    // @DeleteMapping("/delete-user/{AccountNumber}")
    // public ResponseEntity<String> delete(@PathVariable("AccountNumber") int Ac) {

    //     service.delete(Ac);
    //     return ResponseEntity.ok("ACCOUNT DELETE SUCCESSFULLY");
    // }

    // @PutMapping("/update-account")
    // public ResponseEntity<String> UpdateAccount(@RequestBody User user) {

    //     service.UpdateAccount(user);

    //     return ResponseEntity.ok("ACCOUNT UPDATE SUCCESSFLLY");
    // }



}
