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
	public void delete(int Ac) {
		// TODO Auto-generated method stub
		
		repo.deleteAccount(Ac);
	}

	@Override
	public User getAccount(int Ac) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateAccount(User user) {
		// TODO Auto-generated method stub
		repo.UpdateAccount(user);
		
	}

}
