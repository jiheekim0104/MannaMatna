package com.ezen.mannamatna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserInfoController {
	@GetMapping("/") 
	public String home(Model m) {
		return "index"; 
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String gologin() {
		return "index";
	}
	
	@GetMapping("/join") 
	public String join(Model m) {
		return "user/join"; 
	}
}
