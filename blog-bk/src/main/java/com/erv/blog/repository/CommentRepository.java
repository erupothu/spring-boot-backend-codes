package com.erv.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erv.blog.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

	List<CommentEntity> findByStoryId(Integer storyId);
}
