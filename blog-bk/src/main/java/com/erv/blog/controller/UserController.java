package com.erv.blog.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.mail.internet.AddressException;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erv.blog.entity.CommentEntity;
import com.erv.blog.entity.StorageEntity;
import com.erv.blog.entity.StoryEntity;
import com.erv.blog.entity.StoryLogEntity;
import com.erv.blog.entity.TutorialEntity;
import com.erv.blog.entity.UserEntity;
import com.erv.blog.repository.CommentRepository;
import com.erv.blog.repository.StorageRepository;
import com.erv.blog.repository.StoryLogRepository;
import com.erv.blog.repository.StoryRepository;
import com.erv.blog.repository.TutorialRepository;
import com.erv.blog.repository.UserRepository;
import com.erv.blog.service.core.EmailService;

/**
 * @author harish
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	StoryRepository storyRepo;
	
	@Autowired
	TutorialRepository tutorialRepo;
	
	@Autowired
	StoryLogRepository storyLogRepo;
	
	@Autowired
	StorageRepository storageRepo;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	EmailService emailService;
	/**
	 * User CRUD Operations
	 * Login
	 * @param username
	 * @param Password
	 * @return
	 */
	@GetMapping(value="api/blog/login/{email}/{password}")
	public ResponseEntity<?> userLogin(@PathVariable String email, @PathVariable String password) {
		UserEntity user = userRepo.findByEmailAndPassword(email, password);
		return ResponseEntity.ok(user);
	}

	@PostMapping(value="api/blog/create-user")
	public ResponseEntity<?> createuser(@RequestBody UserEntity user) {
		UserEntity response = new UserEntity();
		String body = "<h1>Hi "+user.getUsername() + ",</h1> <br> Welcome to Blog Family";
		
		boolean send = emailService.sendHtmlEmail("Blog Login Verification", body, user.getEmail());
		if(send) {
			String username = user.getEmail().substring(0, user.getEmail().lastIndexOf("@"));
			user.setUsername("@" + username);
			user.setName(username);
			user.setRole("user");
			user.setCreated_date(new Date());
			UserEntity userExist =  userRepo.findByEmail(user.getEmail());
			if(userExist == null) {
				response = userRepo.save(user);
			} else {
				response.setErroMessage("Email Already Exist");
			}
		} else {
			response.setErroMessage("Email Invalid");
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="api/blog/forgot-passsword/{email}")
	public ResponseEntity<?> forgotPassword(@PathVariable String email) {
		UserEntity response = new UserEntity();
	    String password = generatePassword(8);
	    UserEntity userExist =  userRepo.findByEmail(email);
	    if(userExist != null) {
	    	String body = "<h1>Hi User</h1>, <br> your credentials are <br> email: "+ email + "<br> password: :"+ password;
			boolean send = emailService.sendHtmlEmail("Blog Login Verification", body, email);
			userExist.setPassword(password);
		    response = userRepo.save(userExist);
		    response.setErroMessage("password sent to your mail, please check");
	    } else {
	    	response.setErroMessage("This email not Registered");
	    }
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value="api/blog/delete-user")
	public ResponseEntity<?> deleteUser(@RequestBody UserEntity user) {
		userRepo.delete(user);
		return ResponseEntity.ok("delete successful");
	}
	
	@GetMapping(value="api/blog/get-all-users")
	public ResponseEntity<?> getAllUsers() {
		List<UserEntity> user = userRepo.findAll();
		return ResponseEntity.ok(user);
	}
	@GetMapping(value="api/blog/get-user-by-id/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
		Optional<UserEntity> user = userRepo.findById(userId);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping(value="api/blog/update-user")
	public ResponseEntity<?> updateUser(@RequestBody UserEntity user) {
		UserEntity response = userRepo.save(user);
		return ResponseEntity.ok(response);
	}
	
	
	
	/**
	 *   Blog Stories Log
	 */
	@PostMapping(value="api/blog/log/add-story-log")
	public ResponseEntity<?> addStoryLog(@RequestBody  StoryLogEntity storyLog) {
		
		StoryLogEntity response = storyLogRepo.save(storyLog);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="api/blog/log/get-story-logs")
	public ResponseEntity<?> getAllStoryLogs() {
		
		return ResponseEntity.ok(storyLogRepo.findAll());
	}
	
	@GetMapping(value="api/blog/log/get-popular-stories")
	public ResponseEntity<?> getPopularStories() {
		
		List<Map<String, Object>> userStories = storyLogRepo.findTopTenStories();
		List<Integer> stores = new ArrayList<Integer>();
		for(Map<String, Object> item: userStories) {
			stores.add(Integer.parseInt(item.get("story_id").toString()));
		}
		List<StoryEntity>  storiesBasedOnHistory = storyRepo.findPopularStoriesByStoryIdList(stores);
		return ResponseEntity.ok(storiesBasedOnHistory);
	}
	
	@GetMapping(value="api/blog/log/get-popular-categories")
	public ResponseEntity<?> getPopularCategories() {
		
		List<Map<String, Object>> userStories = storyLogRepo.findTopTenCategories();
		List<String> categories = new ArrayList<String>();
		for(Map<String, Object> item: userStories) {
			categories.add(item.get("category").toString());
		}
		List<StoryEntity>  storiesBasedOnHistory = storyRepo.findStoriesByCategoryList(categories);
		return ResponseEntity.ok(storiesBasedOnHistory);
	}
	
	@GetMapping(value="api/blog/log/get-popular-users")
	public ResponseEntity<?> getPopularUsers() {
		
		List<Map<String, Object>> userStories = storyLogRepo.findTopTenUsers();
		List<Integer> users = new ArrayList<Integer>();
		for(Map<String, Object> item: userStories) {
			users.add(Integer.parseInt(item.get("user_id").toString()));
		}
		List<StoryEntity> storiesBasedOnHistory = storyRepo.findStoriesByUserList(users);
		return ResponseEntity.ok(storiesBasedOnHistory);
	}
	
	
	@PostMapping(value="api/blog/log/get-stories-based-on-history")
	public ResponseEntity<?> getStoryBasedOnHist(@RequestBody UserEntity user) {
		
		List<Map<String, Object>> userStories = storyLogRepo.findTopTenCategoriesByUser(user.getUserId());
		List<String> categories = new ArrayList<String>();
		for(Map<String, Object> item: userStories) {
			categories.add(item.get("category").toString());
		}
		List<StoryEntity>  storiesBasedOnHistory = storyRepo.findStoriesByCategoryList(categories);
		return ResponseEntity.ok(storiesBasedOnHistory);
	}
	
	@PostMapping(value="api/blog/log/get-my-most-viewed-stories")
	public ResponseEntity<?> getMyMostViewedStoryByUser(@RequestBody UserEntity user) {
		
		List<Map<String, Object>> userStories = storyLogRepo.findTopTenStoriesByUser(user.getUserId());
		List<Integer> stores = new ArrayList<Integer>();
		for(Map<String, Object> item: userStories) {
			stores.add(Integer.parseInt(item.get("story_id").toString()));
		}
		List<StoryEntity>  storiesBasedOnHistory = storyRepo.findPopularStoriesByStoryIdList(stores);
		return ResponseEntity.ok(storiesBasedOnHistory);
	}
	
	/**
	 * Stories
	 */
	@PostMapping(value="api/blog/add-story")
	public ResponseEntity<?> addStory(@RequestBody  StoryEntity story) {
		List<StorageEntity> storageList = new ArrayList<StorageEntity>();
		StoryEntity response = storyRepo.save(story);
		for(String url: story.getUrls()) {
			StorageEntity storage = new StorageEntity();
			storage.setUserId(response.getUserId());
			storage.setStoryId(response.getStoryId());
			storage.setUrl(url);
			storage.setDate(new Date());
			storageList.add(storage);
		}
		storageRepo.saveAll(storageList);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value="api/blog/update-story")
	public ResponseEntity<?> updateStory(@RequestBody  StoryEntity story) {
		List<StorageEntity> storageList = new ArrayList<StorageEntity>();
		StoryEntity response = storyRepo.save(story);
		if(story.getUrls() != null) {
			for(String url: story.getUrls()) {
				StorageEntity storage = new StorageEntity();
				storage.setUserId(response.getUserId());
				storage.setStoryId(response.getStoryId());
				storage.setUrl(url);
				storage.setDate(new Date());
				storageList.add(storage);
			}
			storageRepo.saveAll(storageList);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="api/blog/get-all-stories")
	public ResponseEntity<?> getAllStories() {
		
		return ResponseEntity.ok(storyRepo.findAll());
	}
	
	@GetMapping(value="api/blog/get-stories-by-user/{userId}")
	public ResponseEntity<?> getStoriesByUser(@PathVariable Integer userId) {
		List<StoryEntity> response = storyRepo.findByUserId(userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="api/blog/get-Story-by-storyId/{storyId}")
	public ResponseEntity<?> getStoryById(@PathVariable Integer storyId) {
		Optional<StoryEntity> response = storyRepo.findById(storyId);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value="api/blog/delete-story-by-id/{id}")
	public ResponseEntity<?> deleteStoriesById(@PathVariable Integer id) {
		storyRepo.deleteById(id);
		return ResponseEntity.ok(id);
	}
	
	@DeleteMapping(value="api/blog/delete-story-by-user/{userId}")
	public ResponseEntity<?> deleteStoriesByUser(@PathVariable Integer userId) {
		List<StoryEntity> response = storyRepo.findByUserId(userId);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Tutorial
	 */
	@PostMapping(value="api/blog/add-tutorial")
	public ResponseEntity<?> addTutorial(@RequestBody  TutorialEntity tutorial) {
		List<StorageEntity> storageList = new ArrayList<StorageEntity>();
		TutorialEntity response = tutorialRepo.save(tutorial);
		for(String url: tutorial.getUrls()) {
			StorageEntity storage = new StorageEntity();
			storage.setUserId(response.getUserId());
			storage.setTutorialId(response.getTutorialId());
			storage.setUrl(url);
			storage.setDate(new Date());
			storageList.add(storage);
		}
		storageRepo.saveAll(storageList);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value="api/blog/update-tutorial")
	public ResponseEntity<?> updateTutorial(@RequestBody  TutorialEntity tutorial) {
		List<StorageEntity> storageList = new ArrayList<StorageEntity>();
		TutorialEntity response = tutorialRepo.save(tutorial);
		if(tutorial.getUrls() != null) {
			for(String url: tutorial.getUrls()) {
				StorageEntity storage = new StorageEntity();
				storage.setUserId(response.getUserId());
				storage.setTutorialId(response.getTutorialId());
				storage.setUrl(url);
				storage.setDate(new Date());
				storageList.add(storage);
			}
			storageRepo.saveAll(storageList);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="api/blog/get-tutorial-by-name/{name}")
	public ResponseEntity<?> getTutorialByName(@PathVariable String name) {
		
		return ResponseEntity.ok(tutorialRepo.findByName(name));
	}
	
	@GetMapping(value="api/blog/get-tutorial-by-name/{name}/{type}")
	public ResponseEntity<?> getTutorialByTypeAndName(@PathVariable String name, @PathVariable String type) {
		
		return ResponseEntity.ok(tutorialRepo.findByNameAndType(name, type));
	}
	
	@GetMapping(value="api/blog/get-all-tutorials")
	public ResponseEntity<?> getAllTutorials() {
		
		return ResponseEntity.ok(tutorialRepo.findAll());
	}
	
	@GetMapping(value="api/blog/get-tutorials-by-user/{userId}")
	public ResponseEntity<?> getTutorialsByUser(@PathVariable Integer userId) {
		List<StoryEntity> response = storyRepo.findByUserId(userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="api/blog/get-tutorial-by-tutorialId/{id}")
	public ResponseEntity<?> getTutorialById(@PathVariable Integer id) {
		Optional<TutorialEntity> response = tutorialRepo.findById(id);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value="api/blog/delete-tutorial-by-id/{id}")
	public ResponseEntity<?> deleteTutorialById(@PathVariable Integer id) {
		tutorialRepo.deleteById(id);
		return ResponseEntity.ok(id);
	}
	
//	@DeleteMapping(value="api/blog/delete-tutorial-by-user/{userId}")
//	public ResponseEntity<?> deleteTutorialsByUser(@PathVariable Integer userId) {
//		List<TutorialEntity> response = tutorialRepo.findByUserId(userId);
//		return ResponseEntity.ok(response);
//	}
	
	// Comment story sections
	@PostMapping(value="api/blog/add-comment")
	public ResponseEntity<?> addComment(@RequestBody  CommentEntity comment) {
		
		CommentEntity response = commentRepo.save(comment);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="api/blog/get-all-comments")
	public ResponseEntity<?> getAllComments() {
		
		return ResponseEntity.ok(commentRepo.findAll());
	}
	
	@GetMapping(value="api/blog/get-comments-by-storyId/{storyId}")
	public ResponseEntity<?> getCommentsByStoryId(@PathVariable Integer storyId) {
		
		return ResponseEntity.ok(commentRepo.findByStoryId(storyId));
	}
	

	// Extra Methods
	public String generatePassword(int length) {
		String password = "";
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String Small_chars = "abcdefghijklmnopqrstuvwxyz"; 
        String numbers = "0123456789";
        String symbols = "!@#$%&*?"; 
//        String symbols = "!@#$%^&*_=+-/.?<>)"; 
        String values = Capital_chars + Small_chars + numbers + symbols; 
        Random rndm_method = new Random();
        for (int i = 0; i < length; i++) { 
            password += values.charAt(rndm_method.nextInt(values.length())); 
        } 
		return password;
	}
	
	public String generateOTP(int length) {
		String otp = "";
		String numbers = "0123456789"; 
		Random rndm_method = new Random();
		for (int i = 0; i < length; i++){
			otp += numbers.charAt(rndm_method.nextInt(numbers.length())); 
        }
		return otp;
	}
}
