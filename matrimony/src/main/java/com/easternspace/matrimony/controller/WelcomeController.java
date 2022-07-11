package com.easternspace.matrimony.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	
	@GetMapping(value="welcome")
	public String welcome() {
		
		return "welcome to matrimony micro service";
	}

}
