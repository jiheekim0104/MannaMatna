package com.ezen.mannamatna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/chart")
	public String goChart() {
		return "/admin/chart";
	}
}
