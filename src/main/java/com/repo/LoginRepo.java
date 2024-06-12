package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.entities.Login;

public interface LoginRepo extends JpaRepository<Login, String>{
	
	List<Login> findByGmail(String userName);
	
	@Modifying
	@Query("update Login u set u.failedCount=u.failedCount+1 where u.gmail=:gmail")
	void incrementFailedCount(String gmail);
	
	@Modifying
	@Query("update Login u set u.failedCount=0 where u.gmail=:gmail")
	void setFailedCountZero(String gmail);
	
	@Modifying
	@Query("update Login u set u.accStatus='locked' where u.gmail=:gmail")
	void lockAccount(String gmail);
	
	@Query("select l.lastLoginTime from Login l where l.gmail=:user")
	String getOldTime(String user);
	
	@Modifying
	@Query("update Login u set u.lastLoginTime=:newTime where u.gmail=:gmail")
	void setNewTime(String gmail,String newTime);
}
