package com.bestow.cms.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.ContentType;
import javax.servlet.ServletContext;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bestow.cms.data.AssetsRepository;
import com.bestow.cms.data.BlogRepository;
import com.bestow.cms.model.AssetsModel;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@CrossOrigin(origins = "*")
public class StorageController {

	private String fileBasePath = "/files/uploads/images/";

	@Autowired
	AssetsRepository assetsRepository;

	@Autowired
	private ApplicationContext applicationContext;

	public File createTempScript() throws IOException {
		File tempScript = File.createTempFile("script", null);

		Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
		PrintWriter printWriter = new PrintWriter(streamWriter);

		printWriter.println("#!/bin/bash");
		printWriter.println("cd /home/nasar/Desktop");
		printWriter.println("mkdir testing-desktop2");
		printWriter.println("ls");
		printWriter.println("echo hello harish");

		printWriter.close();

		return tempScript;
	}

	@GetMapping(value = "api/create-directory")
	public ResponseEntity<?> createDirectory() {
		try {
			File tempScript = createTempScript();
			ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
			pb.inheritIO();
			Process process = pb.start();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = "directory created successfully";
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "api/upload-image")
	public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
//		String fileBasePath = "/files/uploads/images/";
		String command = "mkdir -p " + fileBasePath;
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
		AssetsModel assetsModel = new AssetsModel();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path path = Paths.get(fileBasePath + fileName);
		assetsModel.setFilename(fileName);
		assetsModel.setUrlData(path.toString());
		assetsModel.setAssetType("general uploads");
		assetsModel.setTitle(fileName);
		assetsModel.setFileModifiedDate(new Date());
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		assetsModel.setFileSize(file.getSize());
		assetsRepository.save(assetsModel);
		String jsonResult = null;
		try {
			jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString("successful");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/")
//				.path(fileName).toUriString();
		return ResponseEntity.ok(jsonResult);
	}

	@PostMapping(value = "api/multiple-image-upload")
	public ResponseEntity<?> uploadMultipleImages(@RequestParam("files") MultipartFile[] files) {
		List<Object> fileDownloadUrls = new ArrayList<>();
		try {
			Arrays.asList(files).stream().forEach(file -> fileDownloadUrls.add(uploadImage(file).getBody()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.ok(fileDownloadUrls);
	}

	@GetMapping(value = "api/display-image/{fileName}")
	public ResponseEntity<?> displayImage(@PathVariable String fileName) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		Path path = Paths.get(fileBasePath + fileName);
		byte[] data = null;
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = null;
		try {
			data = Files.readAllBytes(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
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

	@GetMapping(value = "api/get-all-images")
	public ResponseEntity<?> getAllImage() {
//		Path path = Paths.get(fileBasePath);
//		Set<String> fileList = new HashSet();
//		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(fileBasePath))) {
//			for (Path path : stream) {
////				Resource resource = null;
////				resource = new UrlResource(path.toUri());
////				resources.add(resource);
//				if (!Files.isDirectory(path)) {
//					fileList.add(path.getFileName().toString());
//				}
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		List<AssetsModel> asset_response = assetsRepository.findAll();
		List<AssetsModel> listImages = new ArrayList<AssetsModel>();
		for (AssetsModel asset : asset_response) {
			String file = asset.getUrlData();
			Path path = Paths.get(file);
			byte[] data = null;
			ObjectMapper mapper = new ObjectMapper();
			String jsonResult = null;
			try {

				File f = new File(path.toString());
				if (f.exists()) {
					data = Files.readAllBytes(path);
					jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
					asset.setUrl(jsonResult);
					listImages.add(asset);
				}

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ResponseEntity.ok(listImages);
	}

	@PostMapping(value = "api/multiple-image-delete")
	public ResponseEntity<?> uploadMultipleImages(@RequestBody List<AssetsModel> assetsModel) {

		for (AssetsModel asset : assetsModel) {
			File f = new File(asset.getUrlData());
			if (f.exists() && !f.isDirectory()) {
				f.delete();
			}
		}
		assetsRepository.deleteAll(assetsModel);
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = null;
		try {
			jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString("successful");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok(jsonResult);
	}

}
