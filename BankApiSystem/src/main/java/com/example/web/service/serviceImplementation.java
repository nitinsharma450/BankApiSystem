package com.example.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.web.repository.repository;
import com.example.web.transaction.Transaction;
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
	public void delete(int Ac) {
		// TODO Auto-generated method stub
		
		repo.deleteAccount(Ac);
	}

	@Override
	public User getAccount(int Ac) {
		// TODO Auto-generated method stub
		return repo.getAccount(Ac);
	}

	@Override
	public List<User> getAllAccount() {
		// TODO Auto-generated method stub
		return repo.getAllAccounts();
	}

	@Override
	public void UpdateAccount(User user) {
		// TODO Auto-generated method stub
		repo.UpdateAccount(user);
		
	}
	@Override
    public boolean deposit(Long accountNumber, Double amount, String description) {
        return repo.deposit(accountNumber, amount, description);
    }

    @Override
    public boolean withdraw(Long accountNumber, Double amount, String description) {
        return repo.withdraw(accountNumber, amount, description);
    }

    @Override
    public List<Transaction> getTransactionHistory(Long accountNumber) {
        return repo.getTransactionHistory(accountNumber);
    }

}
