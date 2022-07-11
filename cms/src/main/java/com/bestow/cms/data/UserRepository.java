package com.bestow.cms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bestow.cms.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer>{
	
	public UserModel findByNameAndPassword(String name, String password);
	
	public UserModel findByName(String name);
	
	public UserModel findByEmail(String email);

}
