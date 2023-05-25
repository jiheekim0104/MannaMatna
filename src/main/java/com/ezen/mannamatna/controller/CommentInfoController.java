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

	@GetMapping("/comment")
	public String goComment(@ModelAttribute CommentInfoVO commentInfoVO, Model m) {
		List<CommentInfoVO> commentList = commentInfoService.getCommentInfos(commentInfoVO);
		m.addAttribute("commentList", commentList);
		return "babsang/babsang-detail";
	}
}
