package com.erv.blog.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.erv.blog.repository.StorageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*")
public class StorageController {

	private String fileBasePath = "/files/images/stories/";

	@Autowired
	StorageRepository storageRepo;

	@PostMapping(value = "api/blog/upload-image/{email}")
	public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String email) {
		String command = "mkdir -p "+ fileBasePath;
		String[] commands = { "bash", "-c", command };
		Runtime r = Runtime.getRuntime();
		try {
			Process p = r.exec(commands);
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";

			while ((line = b.readLine()) != null) {
				System.out.println(line);
			}
			b.close();
		} catch (Exception e) {
			System.err.println("Failed to execute bash with command: " + command);
			e.printStackTrace();
		}
		String timeDate = String.valueOf(new Date().getTime());
		String emailName = email.substring(0, email.lastIndexOf("@"));
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uniqueId = UUID.randomUUID().toString() + emailName +timeDate+ fileName;
		Path path = Paths.get(fileBasePath + uniqueId);
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = null;
		try {
			jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uniqueId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(jsonResult);
	}
	
	@GetMapping(value = "api/blog/display-image/{fileName}")
	public ResponseEntity<?> displayImage(@PathVariable String fileName) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		Path path = Paths.get(fileBasePath + fileName);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
	}

	@PostMapping(value = "api/multiple-image-upload")
	public ResponseEntity<?> uploadMultipleImages(@RequestParam("files") MultipartFile[] files) {
		List<Object> fileDownloadUrls = new ArrayList<>();
		try {
			Arrays.asList(files).stream().forEach(file -> fileDownloadUrls.add(uploadImage(file, "email").getBody()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.ok(fileDownloadUrls);
	}

	@GetMapping(value = "api/download-image/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
		Path path = Paths.get(fileBasePath + fileName);
		byte[] data = null;
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = null;
		try {
			data = Files.readAllBytes(path);
//			 String string = Base64.getEncoder().encodeToString(data);
//			 jsonResult = mapper.writerWithDefaultPrettyPrinter()
//		    		  .writeValueAsString(string);
			jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok(jsonResult); // .headers(headers).body(inputStreamResource);
	}

//	@GetMapping(value = "api/get-all-images")
//	public ResponseEntity<?> getAllImage() {
//		List<AssetsModel> asset_response = assetsRepository.findAll();
//		List<AssetsModel> listImages = new ArrayList<AssetsModel>();
//		for (AssetsModel asset : asset_response) {
//			String file = asset.getUrlData();
//			Path path = Paths.get(file);
//			byte[] data = null;
//			ObjectMapper mapper = new ObjectMapper();
//			String jsonResult = null;
//			try {
//				
//				File f = new File(path.toString()); 
//				if (f.exists()) {
//					data = Files.readAllBytes(path);
//					jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
//					asset.setUrl(jsonResult);
//					listImages.add(asset);
//				}
//				
//			} catch (FileNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		return ResponseEntity.ok(listImages);
//	}

	@DeleteMapping(value = "api/image-delete/{fileName}")
	public ResponseEntity<?> uploadMultipleImages(@PathVariable String fileName) {
		String response = "";
		File file=new File(fileBasePath + fileName);
		if(file.exists()){
			if(file.delete()){
				response = "File deleted successfully";
			}else{
				response = "Fail to delete file";
			}
		}

		return ResponseEntity.ok(response);
	}

}
