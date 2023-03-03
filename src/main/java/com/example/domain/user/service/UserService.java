package com.example.domain.user.service;

import java.util.List;

import com.example.domain.user.model.MdUser;

public interface UserService {
	public void signup(MdUser user);
	
	public MdUser getUser(String userId);
	public List<MdUser> getUsers(MdUser user);
	
	public void updateUser(String userId, String password, String userName);
	
	public void deleteUser(String userId);
	
	public MdUser getLoginUser(String userId);
}