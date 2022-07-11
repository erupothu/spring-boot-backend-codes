package com.bestow.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {
	
	@GetMapping(value="")
	public ResponseEntity<?> getVisitorsCount() {
		
		return null;
	}

}
