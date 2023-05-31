package com.ezen.mannamatna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ezen.mannamatna.service.BabsangInfoService;
import com.ezen.mannamatna.vo.BabsangInfoVO;

@Controller
public class BabsangInfoController {
	
	@Autowired
	private BabsangInfoService babsangInfoService;
	
	@GetMapping("/main")
	public String goMain(BabsangInfoVO babsang, Model m){
		List<BabsangInfoVO> babsangList = babsangInfoService.getBabsangInfoVOs(babsang);
		m.addAttribute("babsangList", babsangList);
		return "/babsang/babsang-list";
	}
	
	@GetMapping("/createBabsang")
	public String goCreateBabsang(){
		return "/babsang/babsang-insert";
	}
	
	@PostMapping("/babsang-insert")
	public String insertBabsang(BabsangInfoVO board, Model m) {
		return "babsang/babsang-list";
	}
}