package com.example.domain.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.MdUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserRepository;

@Service
@Primary
public class UserServiceImpl2 implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	@Override
	public void signup(MdUser user) {
		boolean exist = userRepository.existsById(user.getUserId());
		
		if (exist) throw new DataAccessException("User already exists"){};
		
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");
		
		String rawPassword = user.getPassword();
		user.setPassword(passwordEncoder.encode(rawPassword));
		
		userRepository.save(user);
	}
	
	@Override
	public List<MdUser> getUsers(MdUser user) {
		ExampleMatcher exampleMatcher = ExampleMatcher
				.matching()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase();
		
		return userRepository.findAll(Example.of(user, exampleMatcher));
	}
	
	@Override
	public MdUser getUser(String userId) {
		Optional<MdUser> optional = userRepository.findById(userId);
		MdUser user = optional.orElse(null);
		
		return user;
	}
	
	@Transactional
	@Override
	public void updateUser(String userId, String password, String userName) {
		String encryptedPassword = passwordEncoder.encode(password);
		
		userRepository.updateUser(userId, encryptedPassword, userName);
	}

	@Transactional
	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
	
	@Override
	public MdUser getLoginUser(String userId) {
		return userRepository.findLoginUser(userId);
	}
}
