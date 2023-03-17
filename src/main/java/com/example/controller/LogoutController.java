package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

/** This is no longer used after Ch11 - Spring Security */
@Controller
@Slf4j
public class LogoutController {
	@PostMapping("/logout")
	public String postLogout() {
		log.info("Logout");
		
		return "redirect:/login";
	}
}
