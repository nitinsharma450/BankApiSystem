package com.example.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.web.repository.repository;
import com.example.web.user.User;

@Service
public class serviceImplementation implements ServiceInterface {
	
	@Autowired
	private repository repo;

	@Override
	public void insert(User user) {
		// TODO Auto-generated method stub
		repo.insert(user);
		
		
	}

	@Override
	public void delete(long Ac) {
		// TODO Auto-generated method stub
		
		repo.deleteAccount(Ac);
	}

	@Override
	public User getAccount(long Ac) {
		// TODO Auto-generated method stub
	return repo.getAccount(Ac);
		
	}

	@Override
	public List<User> getAllAccount() {
		// TODO Auto-generated method stub
	return	repo.getAllAccounts();
		
	}

	@Override
	public void UpdateAccount(User user) {
		// TODO Auto-generated method stub
		repo.UpdateAccount(user);
		
	}

	@Override
	public double withDraw(long accountNumber, double amount) {
		// TODO Auto-generated method stub
	 return	repo.withdraw(accountNumber, amount);
		
	}

	@Override
	public double deposit(long accountNumber, double amount) {
		// TODO Auto-generated method stub
	return	repo.deposit(accountNumber, amount);
		
	}

	@Override
	public void moneyTransfer(long ac1, double amount, long ac2) {
		// TODO Auto-generated method stub
		repo.moneyTransfer(ac1, amount, ac2);
		
	}

	@Override
	public boolean calculateInterest(long accountNUmber) {
		// TODO Auto-generated method stub
		return	repo.calculateInterest(accountNUmber);
		
	}

	

}
