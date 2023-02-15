package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.User;
import com.example.domain.user.service.UserService;
import com.example.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper mapper;
	
	@Override
	public void signup(User user) {
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");
		mapper.insertOne(user);
	}
	
	@Override
	public User getUser(String userId) {
		return mapper.findOne(userId);
	}
	
	@Override
	public List<User> getUsers(User user) {
		return mapper.findMany(user);
	}
	
	@Override
	public void updateUser(String userId, String password, String userName) {
		mapper.updateOne(userId, password, userName);
	}
	
	@Override
	public void deleteUser(String userId) {
		int count = mapper.deleteOne(userId);
	}
}