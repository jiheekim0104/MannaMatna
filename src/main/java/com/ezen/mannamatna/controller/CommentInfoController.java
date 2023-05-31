package com.ezen.mannamatna.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ezen.mannamatna.service.CommentInfoService;
import com.ezen.mannamatna.vo.CommentInfoVO;

@Controller
public class CommentInfoController {

	@Autowired
	CommentInfoService commentInfoService;

	@GetMapping("/comment") // 추후 babsang-detail 쪽으로 매핑지어질 예정
	public String goComment(@ModelAttribute CommentInfoVO commentInfoVO, Model m) {
		List<CommentInfoVO> commentList = commentInfoService.getCommentInfos(commentInfoVO); // 서비스 호출
		m.addAttribute("commentList", commentList); // 모델에 댓글리스트 add
		// 이부분은 추후 basang id 와 mapping 예정
		return "babsang/babsang-detail";
	}
}