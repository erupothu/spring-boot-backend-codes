package com.erv.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erv.blog.entity.StoryEntity;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Integer> {
	
	List<StoryEntity> findByUserId(Integer userId);
	
	@Query(value="select * from story_entity where story_id in (?1)", nativeQuery = true)
	List<StoryEntity> findPopularStoriesByStoryIdList(List<Integer> storyIds);
	
	@Query(value="select * from story_entity where category in (?1)", nativeQuery = true)
	List<StoryEntity> findStoriesByCategoryList(List<String> category);
	
	@Query(value="select * from story_entity where user_id in (?1)", nativeQuery = true)
	List<StoryEntity> findStoriesByUserList(List<Integer> users);
	
}
