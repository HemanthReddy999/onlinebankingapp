package com.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entities.User;

public interface UserRepo extends JpaRepository<User, String>{
	
	@Query("select MAX(u.id) from User u")
	int getMaxId();
	
	@Query("select u.firstName from User u where u.gmail=:user")
	String getFname(String user);
	
}
