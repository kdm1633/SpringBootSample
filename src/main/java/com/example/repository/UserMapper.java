package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.user.model.User;

@Mapper
public interface UserMapper {
	public int insertOne(User user);
	
	public User findOne(String userId);
	public List<User> findMany(User user);
	
	public void updateOne(@Param("userId") String userId,
			@Param("password") String password,
			@Param("userName") String userName);
	
	public int deleteOne(@Param("userId") String userId);
}