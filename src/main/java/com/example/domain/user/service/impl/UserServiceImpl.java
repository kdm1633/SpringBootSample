package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.MdUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void signup(MdUser user) {
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");
		
		String rawPassword = user.getPassword();
		user.setPassword(passwordEncoder.encode(rawPassword));
		
		mapper.insertOne(user);
	}
	
	@Override
	public MdUser getUser(String userId) {
		return mapper.findOne(userId);
	}
	
	@Override
	public List<MdUser> getUsers(MdUser user) {
		return mapper.findMany(user);
	}
	
	@Transactional
	@Override
	public void updateUser(String userId, String password, String userName) {
		mapper.updateOne(userId, password, userName);
	}
	
	@Override
	public void deleteUser(String userId) {
		int count = mapper.deleteOne(userId);
	}
	
	@Override
	public MdUser getLoginUser(String userId) {
		return mapper.findLoginUser(userId);
	}
}