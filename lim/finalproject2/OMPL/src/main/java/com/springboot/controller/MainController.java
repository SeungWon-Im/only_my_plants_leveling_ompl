package com.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller 
public class MainController {
	@GetMapping("/home")
	public String mainFrom() {
		return "main";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signupFrom() {
		return "signup";
	}
	
	@GetMapping("/plantnew")
	public String registraionFrom() {
		return "registraion";
	}
	
	@GetMapping("/plantlist")
	public String plantlistFrom() {
		return "plant_list";
	}
}
