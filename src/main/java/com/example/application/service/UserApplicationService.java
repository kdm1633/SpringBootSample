package com.example.application.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MdUser;

@Service
public class UserApplicationService {
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	private String filepath = "D:\\";
	
	private static final String SEPARATOR = File.separator;
	
	public Map<String, Integer> getGenderMap(Locale locale) {
		Map<String, Integer> genderMap = new LinkedHashMap<>();
		String male = messageSource.getMessage("male", null, locale);
		String female = messageSource.getMessage("female", null, locale);
		genderMap.put(male, 1);
		genderMap.put(female, 2);
		
		return genderMap;
	}
	
	public void saveUserCsv(List<MdUser> userList, String filename) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (MdUser user : userList)
			sb.append(user.toCsv());
		
		Path path = Paths.get(filepath + SEPARATOR + filename);
		System.out.println(path.toString());
		
		byte[] bytes = sb.toString().getBytes();
		
		Files.write(path, bytes);
	}
	
	public byte[] getCsv(String fileName) throws IOException {
		String path = "file:" + filepath + SEPARATOR + fileName;
		
		Resource resource = resourceLoader.getResource(path);
		File file = resource.getFile();
		
		return Files.readAllBytes(file.toPath());
	}
	
	public void saveDepartmentCsv(List<MdUser> userList, String filename) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (MdUser user : userList)
			sb.append(user.getDepartment().toCsv());
		
		Path path = Paths.get(filepath + SEPARATOR + filename);
		
		byte[] bytes = sb.toString().getBytes();
		
		Files.write(path, bytes);
	}
	
	public InputStream getInputStream(String filename) throws IOException {
		String path = "file:" + filepath + SEPARATOR + filename;
		
		Resource resource = resourceLoader.getResource(path);
		
		return resource.getInputStream();
	}
}
