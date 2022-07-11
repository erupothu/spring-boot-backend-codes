package com.erv.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erv.blog.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	UserEntity findByEmailAndPassword(String email, String password);
	
	UserEntity findByEmail(String email);

}
