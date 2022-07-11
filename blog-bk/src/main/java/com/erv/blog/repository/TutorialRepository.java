package com.erv.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erv.blog.entity.TutorialEntity;

public interface TutorialRepository extends JpaRepository<TutorialEntity, Integer>{

	public List<TutorialEntity> findByName(String name);
	
	public List<TutorialEntity> findByNameAndType(String name, String type);
	
}
