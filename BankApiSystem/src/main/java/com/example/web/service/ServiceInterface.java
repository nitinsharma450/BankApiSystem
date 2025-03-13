package com.example.web.service;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

import com.example.web.user.User;

public interface ServiceInterface {
	
	public void insert(User user);
	public void delete(int Ac);
	public User getAccount(int Ac);
	public List<User> getAllAccount();
	public void UpdateAccount(User user);

}
