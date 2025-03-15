package com.example.web.Controller;

import java.security.PublicKey;
import java.util.List;

import javax.sound.midi.VoiceStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.service.ServiceInterface;
import com.example.web.service.serviceImplementation;
import com.example.web.user.User;

@RestController
public class Controller {
	
	@Autowired
	private ServiceInterface service;
	
	@GetMapping("/get-user")
	public List<User> getAllUsers() {
	 return	service.getAllAccount();

	}

	@GetMapping("/get-AccountDetails/{Ac-number}")
	public User getDetails( @PathVariable("Ac-number") int Ac_number) {
		return service.getAccount(Ac_number);
	}
	
	@PostMapping("/insert-user")
	public ResponseEntity<String> insert(@RequestBody User user) {
		service.insert(user);
		
		return ResponseEntity.ok("ACCOUNT CREATE SUCCESSFULLY");
	}
	
	
	@DeleteMapping("/delete-user/{AccountNumber}")
	public ResponseEntity<String> delete(@PathVariable("AccountNumber") int Ac) {
		
		service.delete(Ac);
		return ResponseEntity.ok("ACCOUNT DELETE SUCCESSFULLY"); 
	}
	
	@PutMapping("/update-account")
	public ResponseEntity<String> UpdateAccount(User user){
		
		service.UpdateAccount(user);
		
		return ResponseEntity.ok("ACCOUNT UPDATE SUCCESSFLLY");
	}

}
