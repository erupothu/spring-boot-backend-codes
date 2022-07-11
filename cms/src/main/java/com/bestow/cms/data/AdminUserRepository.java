package com.bestow.cms.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bestow.cms.model.AdminUserModel;

public interface AdminUserRepository extends JpaRepository<AdminUserModel, Integer> {

}
