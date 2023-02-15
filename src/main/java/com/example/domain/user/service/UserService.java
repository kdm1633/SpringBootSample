package com.example.domain.user.service;

import java.util.List;

import com.example.domain.user.model.User;

public interface UserService {
	public void signup(User user);
	
	public User getUser(String userId);
	public List<User> getUsers(User user);
	
	public void updateUser(String userId, String password, String userName);
	
	public void deleteUser(String userId);
}