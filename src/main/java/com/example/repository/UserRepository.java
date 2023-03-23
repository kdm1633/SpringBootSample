package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domain.user.model.MdUser;

public interface UserRepository extends JpaRepository<MdUser, String> {
	@Query("SELECT user FROM MdUser user WHERE userId = :userId")
	public MdUser findLoginUser(@Param("userId") String userId);
	
	@Modifying
	@Query("UPDATE MdUser SET password = :password, userName = :userName WHERE userId = :userId")
	public Integer updateUser(@Param("userId") String userId,
			@Param("password") String password, @Param("userName") String userName);
}
