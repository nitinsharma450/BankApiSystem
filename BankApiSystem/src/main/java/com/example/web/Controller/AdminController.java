package com.example.web.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.service.ServiceInterface;
import com.example.web.user.User;

@RestController
@RequestMapping("/BankApiSystem/admin")
public class AdminController {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "adminpass123";

    @Autowired
    private ServiceInterface service;
    
    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts(@RequestHeader(value = "username", required = false) String username,
                                         @RequestHeader(value = "password", required = false) String password) {
        // Basic authentication check
        if (username == null || password == null || 
            !username.equals(ADMIN_USERNAME) || !password.equals(ADMIN_PASSWORD)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Admin credentials required");
        }
        
        List<User> accounts = service.getAllAccount();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard(@RequestHeader(value = "username", required = false) String username,
                                       @RequestHeader(value = "password", required = false) String password) {
        // Basic authentication check
        if (username == null || password == null || 
            !username.equals(ADMIN_USERNAME) || !password.equals(ADMIN_PASSWORD)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Admin credentials required");
        }
        
        // Get all accounts
        List<User> accounts = service.getAllAccount();
        
        // Create a dashboard summary
        int totalAccounts = accounts.size();
        
        return ResponseEntity.ok("Admin Dashboard\n" +
                "Total Accounts: " + totalAccounts);
    }
    // @GetMapping("/get-user")
    // public List<User> getAllUsers(@RequestHeader(value = "username", required = false) String username,
    // @RequestHeader(value = "password", required = false) String password) {
    //     return service.getAllAccount();
    // }
     @PostMapping("/insert-user")
    public ResponseEntity<String> insert(@RequestBody User user,@RequestHeader(value = "username", required = false) String username,
    @RequestHeader(value = "password", required = false) String password) {
        service.insert(user);

        return ResponseEntity.ok("ACCOUNT CREATE SUCCESSFULLY");
    }

    @DeleteMapping("/delete-user/{AccountNumber}")
    public ResponseEntity<String> delete(@PathVariable("AccountNumber") int Ac,@RequestHeader(value = "username", required = false) String username,
    @RequestHeader(value = "password", required = false) String password) {

        service.delete(Ac);
        return ResponseEntity.ok("ACCOUNT DELETE SUCCESSFULLY");
    }

    @PutMapping("/update-account")
    public ResponseEntity<String> UpdateAccount(@RequestBody User user,@RequestHeader(value = "username", required = false) String username,
    @RequestHeader(value = "password", required = false) String password) {

        service.UpdateAccount(user);

        return ResponseEntity.ok("ACCOUNT UPDATE SUCCESSFLLY");
    }
}