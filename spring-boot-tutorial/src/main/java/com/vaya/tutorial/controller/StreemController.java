package com.vaya.tutorial.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vaya.tutorial.service.core.StreamService;

@RestController
public class StreemController {
	
	@Autowired
	StreamService streamServcie;
	
	@PostMapping("stream-anymatch")
	public ResponseEntity<?> streamMatch(@RequestBody List<Integer> input) {
		
		boolean result = streamServcie.streamMatch(input);
		return ResponseEntity.ok(result);
	}
}
