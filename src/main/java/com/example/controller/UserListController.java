package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MdUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserListForm;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserListController {
	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserApplicationService appService;
	
	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form, Model model) {
		MdUser user = modelMapper.map(form, MdUser.class);
		
		List<MdUser> userList = userService.getUsers(user);
		
		model.addAttribute("userList", userList);
		
		return "user/list";
	}
	
	@PostMapping("/list")
	public String postUserList(@ModelAttribute UserListForm form, Model model) {
		MdUser user = modelMapper.map(form, MdUser.class);
		
		List<MdUser> userList = userService.getUsers(user);
		
		model.addAttribute("userList", userList);
		
		return "user/list";
	}
	
	@PostMapping("/list/download")
	public ResponseEntity<byte[]> downloadUserList(@ModelAttribute UserListForm form) throws IOException {
		MdUser user = modelMapper.map(form, MdUser.class);
		
		List<MdUser> userList = userService.getUsers(user);
		
		String fileName = "user.csv";
		appService.saveUserCsv(userList, fileName);
		
		byte[] bytes = appService.getCsv(fileName);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", MediaType.ALL_VALUE + "; charset=UTF-8");
		header.setContentDispositionFormData("filename", fileName);
		
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}
	
	@PostMapping("/list/download/zip")
	public void downloadZip(@ModelAttribute UserListForm form, HttpServletResponse response) throws IOException {
		MdUser user = modelMapper.map(form, MdUser.class);
		List<MdUser> userList = userService.getUsers(user);
		
		List<String> filenameList = new ArrayList<>();
		
		String userFilename = "user.csv";
		appService.saveUserCsv(userList, userFilename);
		filenameList.add(userFilename);
		
		String departmentFilename = "department.csv";
		appService.saveUserCsv(userList, departmentFilename);
		filenameList.add(departmentFilename);
		
		String zipFilename = "sample.zip";
		response.setHeader(HttpHeaders.CONTENT_TYPE,
			MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=" + zipFilename);
		
		try(ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
			for (String filename : filenameList) {
				try(InputStream is = appService.getInputStream(filename)) {
					zos.putNextEntry(new ZipEntry(filename));
					StreamUtils.copy(is, zos);
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}