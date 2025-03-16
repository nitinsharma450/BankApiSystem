package com.example.web.service;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
import com.example.web.transaction.Transaction;
import com.example.web.user.User;

public interface ServiceInterface {
	
	public void insert(User user);
	public void delete(int Ac);
	public User getAccount(int Ac);
	public List<User> getAllAccount();
	public void UpdateAccount(User user);
	public boolean deposit(Long accountNumber, Double amount, String description);
    public boolean withdraw(Long accountNumber, Double amount, String description);
    public List<Transaction> getTransactionHistory(Long accountNumber);
}
