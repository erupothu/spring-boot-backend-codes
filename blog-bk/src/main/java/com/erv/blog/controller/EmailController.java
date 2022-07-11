package com.erv.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
	
	@GetMapping(value="welcome-email")
	public ResponseEntity<?> welcomeMail() {
		
		return ResponseEntity.ok("welcome to Email controller");
	}

}
