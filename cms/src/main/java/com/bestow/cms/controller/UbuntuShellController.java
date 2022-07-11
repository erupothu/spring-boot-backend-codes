package com.bestow.cms.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UbuntuShellController {
	
	@GetMapping(value="create-ubuntu-user/{myuser}/{mypassword}")
	public ResponseEntity<?> createUbuntuUser(@PathVariable String myuser, @PathVariable String mypassword) {
		String response = "";
		try {
			File bashFile = new File("runjavafile.sh");
			FileWriter bashFw = new FileWriter(bashFile); // FileWriter fr = new FileWriter(appendFile, true); true will enable the append data
			bashFw.write("#!/bin/sh\n");
			bashFw.write("");
			bashFw.close();
			
			ProcessBuilder pb = new ProcessBuilder("bash", bashFile.toString());
//			pb.inheritIO();
			Process process = pb.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String s = null;
			while ((s = stdInput.readLine()) != null)
				response += s;
			while ((s = stdError.readLine()) != null)
				response += s;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return ResponseEntity.ok(response);
	}

}
