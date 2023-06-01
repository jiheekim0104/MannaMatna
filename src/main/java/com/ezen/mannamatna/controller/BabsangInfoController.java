package com.ezen.mannamatna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.ezen.mannamatna.service.BabsangInfoService;
import com.ezen.mannamatna.vo.BabsangInfoVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
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
	
	@GetMapping("/detail/{biNum}") // biNum으로 VO를 가져오는 방식
    public String detailBabsang(@PathVariable int biNum, Model m){
		// 밥상 상세페이지 컨트롤러 구현
        m.addAttribute("detail", babsangInfoService.getBabsangInfoVO(biNum));
        return "babsang/babsang-detail"; // 요청 jsp
    }
}