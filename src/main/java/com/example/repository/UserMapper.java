package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.user.model.MdUser;

@Mapper
public interface UserMapper {
	public int insertOne(MdUser user);
	
	public MdUser findOne(String userId);
	public List<MdUser> findMany(MdUser user);
	
	public void updateOne(@Param("userId") String userId,
			@Param("password") String password,
			@Param("userName") String userName);
	
	public int deleteOne(@Param("userId") String userId);
	
	public MdUser findLoginUser(String userId);
}