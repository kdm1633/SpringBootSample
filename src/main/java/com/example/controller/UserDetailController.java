package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.model.MdUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserDetailForm;

@Controller
@RequestMapping("/user")
public class UserDetailController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/detail/{userId:.+}")
	public String getUser(UserDetailForm form, Model model, @PathVariable("userId") String userId) {
		MdUser user = userService.getUser(form.getUserId());
		user.setPassword(null);
		
		form = modelMapper.map(user, UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());
		System.out.println(user.toString());
		
		model.addAttribute("userDetailForm", form);
		
		return "user/detail";
	}
	
	@PostMapping(value="/detail", params="update")
	public String updateUser(UserDetailForm form, Model model) {
		userService.updateUser(form.getUserId(), form.getPassword(), form.getUserName());
		
		return "redirect:/user/list";
	}
	
	@PostMapping(value="/detail", params="delete")
	public String deleteUser(UserDetailForm form, Model model) {
		userService.deleteUser(form.getUserId());
		
		return "redirect:/user/list";
	}
}
