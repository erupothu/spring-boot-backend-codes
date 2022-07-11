package com.bestow.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bestow.cms.data.BlogRepository;
import com.bestow.cms.data.LayoutRepository;
import com.bestow.cms.data.UserRepository;
import com.bestow.cms.model.BlogModel;
import com.bestow.cms.model.LayoutModel;
import com.bestow.cms.model.UserModel;
import com.bestow.cms.model.response.CommonResponse;
import com.bestow.cms.networkservices.SimpleEmailService;
import com.bestow.cms.util.GenerateSecurePassword;

@RestController
public class CmsLibraryController {
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LayoutRepository layoutRepository;
	
	@PostMapping("/register")
	public ResponseEntity<CommonResponse<UserModel>> addNewUser(@RequestBody UserModel userModel) {
		
		CommonResponse<UserModel> response = new CommonResponse();
		response.setResponse(userRepository.save(userModel));
		response.setMessage("successfully saved");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/validate-user/{name}")
	public ResponseEntity<CommonResponse<UserModel>> validateUser(@PathVariable String name) {
		
		CommonResponse<UserModel> response = new CommonResponse();
		UserModel user = userRepository.findByName(name);
		
		if(user!=null) {
			response.setMessage("username already exist");
			return ResponseEntity.ok(response);
		}
		response.setMessage("user name is valid");
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<CommonResponse<UserModel>> loginUser(@RequestBody UserModel userModel) {
		CommonResponse<UserModel> response = new CommonResponse();
		CommonResponse<UserModel> validateResponse = validateUser(userModel.getName()).getBody();
		
		UserModel userResponse = userRepository.findByNameAndPassword(userModel.getName(), userModel.getPassword());
		
		if(userResponse==null && validateResponse.getMessage().equalsIgnoreCase("username already exist")) {
			response.setMessage("password invalid");
			return ResponseEntity.ok(response);
			
		} else if(userResponse==null) {
			response.setMessage("username and password are invalid");
			return ResponseEntity.ok(response);
		}
		response.setResponse(userResponse);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value="/change-password/{name}/{oldPassword}/{newPassword}")
	public ResponseEntity<?> changePassword(@PathVariable String name, @PathVariable String oldPassword, @PathVariable String newPassword){
		CommonResponse<UserModel> response = new CommonResponse();
		
		UserModel userResponse = userRepository.findByNameAndPassword(name, oldPassword);
		
		if(userResponse != null) {
			userResponse.setPassword(newPassword);
			userRepository.save(userResponse);
			response.setMessage("Password Updated successfully");
			return ResponseEntity.ok(response);
		}
		
		response.setMessage("Password Invalid");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="forgot-password/{email}")
	public ResponseEntity<?> forgotPassword(@PathVariable String email){
		
		CommonResponse<UserModel> response = new CommonResponse();
		UserModel user = userRepository.findByEmail(email);
		
		String password = GenerateSecurePassword.generatePassword(8);
		user.setPassword(password);
		userRepository.save(user);
		
		String body = "Dear "+user.getName()+" <br> Your your password is <b> "+ password +"</b> <br> click below link to change password: http://localhost:8091/change-password";
		
		if(user!=null) {
			SimpleEmailService.sendEmail(email, "Reset Password Request", body);
			
			response.setMessage("password sent successfully");
			
			return ResponseEntity.ok(response);
		}
		response.setMessage("user with this email not found");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserModel>> getAllUsers(){
		
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@PostMapping("/addBlogPageData")
	public ResponseEntity<?> addblogpages(@RequestBody BlogModel blogModel) {
		
		
		
		BlogModel response = blogRepository.save(blogModel);
		
		if(blogModel.getBlogLayoutList()!=null) {
			Set<LayoutModel> layoutList = blogModel.getBlogLayoutList();
			for(LayoutModel layout: blogModel.getBlogLayoutList()) {
				layout.setBlogModel(response);
			}
			layoutRepository.saveAll(layoutList);
		}
		
		
		return ResponseEntity.ok("successfully saved");
	}
	
	@GetMapping("/getAllblogpages")
	public ResponseEntity<List<BlogModel>> getAllBlogs() {
		
		return ResponseEntity.ok(blogRepository.findAll());
	}
	
	@GetMapping("/getBlogPageByid/{id}")
	public ResponseEntity<Optional<BlogModel>> getAllBlogsByUserId(@PathVariable int id){
		
		return ResponseEntity.ok(blogRepository.findById(id));
	}
	
	@PostMapping("/addBlogLayout")
	public ResponseEntity<LayoutModel> addBlogLayout(@RequestBody LayoutModel layoutModel) {
		
		return ResponseEntity.ok(layoutRepository.save(layoutModel));
	}
	
	@GetMapping("/getAllLayouts")
	public ResponseEntity<List<LayoutModel>> getAllLayouts(){
		
		return ResponseEntity.ok(layoutRepository.findAll());
	}
	
	
	@DeleteMapping("/deleteUserById/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		
		Optional<UserModel> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();		
			}
		
		
		return ResponseEntity.notFound().build();
	}

}
