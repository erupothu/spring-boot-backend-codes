package com.erv.blog.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erv.blog.entity.StoryLogEntity;

@Repository
public interface StoryLogRepository extends JpaRepository<StoryLogEntity, Integer>{
	
	@Query(value="select * from story_log_entity where user_id=?1", nativeQuery = true)
	List<StoryLogEntity> findByUserVisitedLog(Integer userId);
	
	List<StoryLogEntity> findByStoryId(Integer storyId);
	
	List<StoryLogEntity> findByCategory(String category);
	
	@Query(value="SELECT COUNT(category) AS count, category " + 
			"FROM STORY_LOG_ENTITY GROUP BY category " + 
			"ORDER BY count  DESC limit 10", nativeQuery = true)
	List<Map<String, Object>> findTopTenCategories();
	
	@Query(value="SELECT COUNT(story_id) AS count, story_id " + 
			"FROM STORY_LOG_ENTITY GROUP BY story_id " + 
			"ORDER BY count  DESC limit 10", nativeQuery = true)
	List<Map<String, Object>> findTopTenStories();
	
	@Query(value="SELECT COUNT(story_id) AS count, user_id " + 
			"FROM STORY_LOG_ENTITY GROUP BY user_id " + 
			"ORDER BY count  DESC limit 10", nativeQuery = true)
	List<Map<String, Object>> findTopTenUsers();
	
	@Query(value="SELECT COUNT(category) AS count, category " + 
			"FROM STORY_LOG_ENTITY where user_id=?1 GROUP BY category " + 
			"ORDER BY count  DESC limit 10", nativeQuery = true)
	List<Map<String, Object>> findTopTenCategoriesByUser(Integer userId);
	
	@Query(value="SELECT COUNT(story_id) AS count, story_id " + 
			"FROM STORY_LOG_ENTITY where user_id=?1 GROUP BY story_id " + 
			"ORDER BY count  DESC limit 10", nativeQuery = true)
	List<Map<String, Object>> findTopTenStoriesByUser(Integer userId);
}
