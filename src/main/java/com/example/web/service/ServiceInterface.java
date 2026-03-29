package com.example.web.service;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

import com.example.web.user.User;

public interface ServiceInterface {
	
	public void insert(User user);
	public void delete(long Ac);
	public User getAccount(long Ac);
	public List<User> getAllAccount();
	public void UpdateAccount(User user);
	
	public double withDraw(long accountNumber, double amount);
	public double deposit(long accountNumber, double amount);
	public void moneyTransfer(long ac1, double amount, long ac2);
	public boolean calculateInterest(long accountNUmber);

}
