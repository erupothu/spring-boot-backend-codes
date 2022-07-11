package com.bestow.cms.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bestow.cms.data.AdminUserRepository;
import com.bestow.cms.model.AdminUserModel;
import com.bestow.cms.model.AssetsModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AdminUserController {
	
	@Autowired
	AdminUserRepository adminUserRepository;
	
	@PostMapping(value="api/create-admin-user")
	public ResponseEntity<?> createAdminUser(@RequestBody AdminUserModel adminUserModels) {
		adminUserModels.setCreated_date(new Date());
		adminUserModels.setLast_login(new Date());
		adminUserModels.setUserImagepath("/files/uploads/users/"+ adminUserModels.getFull_name() +".jpg");
		
		byte[] data = Base64.getMimeDecoder().decode(adminUserModels.getUserImage().split(",")[1]);
		Path destinationFile = Paths.get("/files/uploads/users/", adminUserModels.getFull_name() +".jpg");
		try {
			Files.write(destinationFile, data);
//			Files.copy(data, destinationFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adminUserModels.setUserImage("");
		AdminUserModel response = adminUserRepository.save(adminUserModels);
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = null;
		try {
			jsonResult = mapper.writerWithDefaultPrettyPrinter()
					  .writeValueAsString("successful");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(jsonResult);
	}
	
	@GetMapping(value="api/get-all-admin-users")
	private ResponseEntity<?> listAllUsers() {
		List<AdminUserModel> users = adminUserRepository.findAll();
		List<AdminUserModel> listUsers = new ArrayList<AdminUserModel>();
		for(AdminUserModel user: users) {
			String file = user.getUserImagepath();
			Path path = Paths.get(file);
			 byte[] data = null;
			ObjectMapper mapper = new ObjectMapper();
			String jsonResult = null;
			try {
				 data = Files.readAllBytes(path);
				    jsonResult = mapper.writerWithDefaultPrettyPrinter()
				    		  .writeValueAsString(data);
				    user.setUserImage(jsonResult);
//				    listImages.add(user);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ResponseEntity.ok(users);
	}
	
	@PostMapping(value="api/update-admin-user")
	public ResponseEntity<?> updateAdminUser(@RequestBody AdminUserModel adminUserModels) {
		
		adminUserModels.setLast_login(new Date());
		return ResponseEntity.ok(adminUserRepository.save(adminUserModels));
	}
	
	@PostMapping(value="api/delete-admin-user")
	public ResponseEntity<?> deleteAdminUser(@RequestBody List<AdminUserModel> adminUserModels) {
		
//		adminUserRepository.deleteAll(adminUserModels);
		for(AdminUserModel user: adminUserModels) {
			File f = new File(user.getUserImagepath());
			if(f.exists() && !f.isDirectory()) { 
			    f.delete();
			}
		}
		adminUserRepository.deleteAll(adminUserModels);
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = null;
		try {
			jsonResult = mapper.writerWithDefaultPrettyPrinter()
					  .writeValueAsString("successful");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(jsonResult);
	}
	
	

}
