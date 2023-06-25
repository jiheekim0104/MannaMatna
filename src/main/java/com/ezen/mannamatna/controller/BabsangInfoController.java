package com.ezen.mannamatna.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.mannamatna.service.BabsangInfoService;
import com.ezen.mannamatna.service.UserInfoService;
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class BabsangInfoController {

	@Autowired
	private BabsangInfoService babsangInfoService;
	@Autowired
	private UserInfoService userInfoService;

	/*
	 * 기존 밥상 리스트 나열할 때 사용
	 * 
	 * @GetMapping("/main") public String goMain(BabsangInfoVO babsang, Model m){
	 * List<BabsangInfoVO> babsangList =
	 * babsangInfoService.getBabsangInfoVOs(babsang);
	 * m.addAttribute("babsangList",babsangList); return "babsang/babsang-list"; }
	 */

	/* 페이징 */
	@GetMapping("/main")
	public String pagingBabsangList(@ModelAttribute BabsangInfoVO babsang, Model m) {
		m.addAttribute("page", babsangInfoService.getPagingBansang(babsang));
		return "babsang/babsang-list";
	}
	
	/* 밥상생성 버튼 클릭 시 페이지 이동 */
	@GetMapping("/addBabsang")
	public String goCreateBabsang() {
		return "babsang/babsang-insert";
	}

	/* 밥상생성 - 등록 버튼 클릭 시 작동 */
	@PostMapping("/addBabsang")
	public String insertBabsang(BabsangInfoVO babsang, UserInfoVO userInfoVO, Model m, HttpSession session) {
		UserInfoVO userSession = (UserInfoVO) session.getAttribute("user");

		babsang.setUiNum(userSession.getUiNum()); // 세션의 uiNum >> 받아서 밥상의 uiNum으로 set
		log.info("userInfo_biNum ===> {}", babsang.getUiBiNum()); // 밥상이 생성되지않아 받아올게 없어서 0이 나옴
		String msg = "밥상 등록 실패";
		String url = "/babsang-insert";

		if (babsangInfoService.addBabsangInfo(babsang)) {// 밥상이 insert 되면
			int biNum = babsang.getUiBiNum(); // insert 할 때의 autoIncrement 된 밥상 번호를 받아옴
			log.info("babsang.uiBiNum ===> {}", biNum); // 확인

			userInfoVO.setUiNum(userSession.getUiNum());

			userInfoVO.setBiNum(biNum); // 유저의 biNum >> 밥상 insert 할 때 받아온 biNum으로 set
			log.info("userInfo.biNum ===> {}", userInfoVO.getBiNum()); // 확인
			userSession.setBiNum(biNum); // 세션의 biNum >> 밥상 insert 할 때 받아온 biNum으로 set
			log.info("userSession.biNum ===> {}", userSession.getBiNum()); // 확인

			msg = "밥상이 잘 차려졌어요! 🍛";
			url = "/main";
			userInfoService.updateBiNum(userInfoVO); // insert성공 시 유저서비스의 update 실행
			log.info("==== 밥상 insert 끝 ====");
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

	@GetMapping("/detail/{biNum}") // biNum으로 VO를 가져오는 방식
	public String detailBabsang(@PathVariable int biNum, Model m) {
		// 밥상 상세페이지 컨트롤러 구현
		BabsangInfoVO babsangInfoVO = babsangInfoService.getBabsangInfoVO(biNum);
		m.addAttribute("detail", babsangInfoVO);
		// 모델에 detail로 해당 상세 밥상객체를 넣어준 후
		m.addAttribute("babsangMaker", userInfoService.getUserInfoFromBabsang(babsangInfoVO.getUiNum()));
		// 해당 밥상객체의 uiNum으로 밥상작성자 유저를 모델에 넣어준다.
		m.addAttribute("babsangUserList", userInfoService.getUserInfosByBiNum(biNum));
		// 참가하기를 누르면 UserInfoVO에 biNum이 업데이트 되며, 해당 biNum과 같은 유저리스트(참가자)를 가져온 후
		// 모델에 넣어준다.
		return "babsang/babsang-detail"; // 요청 jsp
	}

	@GetMapping("/deleteBabsang")
	public String deleteBabsang(Model m, @RequestParam("biNum") int biNum, UserInfoVO userInfoVO, HttpSession session) {
		UserInfoVO userSession = (UserInfoVO) session.getAttribute("user");
		String msg = "로그인이 필요합니다!!";
		String url = "/login";
		BabsangInfoVO babsangInfoVO = babsangInfoService.getBabsangInfoVO(biNum); // 상세페이지의 biNum으로 밥상객체 불러옴
		List<UserInfoVO> userList = userInfoService.getUserInfosByBiNum(biNum); // 해당 밥상에 참여중인 userList
		if (userSession != null) {
			// 로그인 유저가 확인됐을 경우에만 삭제 가능
			msg = "이미 마감된 밥상입니다!!";
			url = "/detail/" + biNum; // 해당페이지 redirect
			if (userList.size() > 1) {
				// 해당밥상에 유저리스트에 다른 유저가 존재할 경우 삭제기능x
				msg = "해당 밥상에 이미 참여중인 유저가 존재합니다!";
			} else if (!babsangInfoVO.isBiClosed() || userSession.getUiId().equals("administer")) {
				// biClosed = false 인 경우만 삭제기능 가능
				// 밥상이 마감된 상태면 삭제할 수 없어여!!
				for (UserInfoVO user : userList) {
					// 삭제기능 실행 시
					// 해당 밥상에 참여중인 유저리스트에 모든 biNum을 0으로 초기화
					user.setBiNum(0);
					userSession.setBiNum(0); // session에도 넣어주어 버튼들이 다르게 보이도록 설정
					userInfoService.updateBiNum(user); // delete 성공 시 유저서비스의 update 실행
				}
				// 유저리스트의 biNum 전부 0으로 업데이트 후 삭제!!
				babsangInfoService.deleteBabsangInfo(biNum);
				msg = "밥상 삭제 성공!!";
				url = "/main";
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

	// 비로그인 시 밥상 보려고 할 때
	@GetMapping("/cannotSeeBabsang")
	public String cannotSeeBabsang() {
		return "common/common";
	}

	@GetMapping("/babsangJoin/{biNum}")
	public String joinBabsang(Model m, @PathVariable("biNum") int biNum, HttpSession session) {
		// 참가하기 컨트롤러
		String msg = "로그인해주세요!";
		String url = "/login";
		UserInfoVO userInfoVO = (UserInfoVO) session.getAttribute("user"); // 로그인 중인 유저세션
		if (userInfoVO != null) {
			// 로그인 세션이 확인 되는 경우만
			// 세션에서 uiVO 객체를 제공받은 후 해당 객체로 uiService의 biNum업데이트 실행
			url = "/detail/" + biNum;
			BabsangInfoVO babsangInfoVO = babsangInfoService.getBabsangInfoVO(biNum); // 상세페이지의 밥상객체
			List<UserInfoVO> userList = userInfoService.getUserInfosByBiNum(biNum); // 해당밥상에 참가중인 유저리스트
			if (userList.size() == babsangInfoVO.getBiHeadCnt()) {
				// 만약에 인원이 가득 찼을 경우
				msg = "인원이 가득 찼습니다!!";
			} else if (babsangInfoVO.isBiClosed()) {
				// 밥상이 이미 마감된 경우
				msg = "이미 마감 된 밥상입니다!!";
			} else if (userInfoVO.getBiNum() > 0) {
				// 이미 생성중인 밥상이 있으나 다른 밥상게시물 상세페이지에 들어갔을 경우 참가하기 실행하지 않아야한다.
				// 세션유저객체의 biNum이 0보다 크다면 밥상에 참여중인 유저이거나 밥상작성자이다.
				msg = "이미 참여중인 밥상이 존재합니다!!";
			} else {
				// 인원이 가득 차지 않은 밥상 및 마감된 밥상이 아닐 경우, 참가중인 밥상이 없을 경우에만 실제 참가 기능 실행
				userInfoVO = (UserInfoVO) session.getAttribute("user");
				userInfoVO.setBiNum(biNum);
				userInfoService.updateBiNum(userInfoVO);
				msg = "참가되셨습니다!!";
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

	@GetMapping("/joinCancle/{biNum}")
	public String joinCancle(Model m, @PathVariable("biNum") int biNum, HttpSession session) {
		// 참가취소 비지니스로직
		String msg = "로그인해주세요!";
		String url = "/login";
		BabsangInfoVO babsangInfoVO = babsangInfoService.getBabsangInfoVO(biNum);
		if (session.getAttribute("user") != null) {
			// 세션 로그인상태 유지중 참가하기 취소 누른 후
			// 유저인포 biNum 0으로 업데이트
			url = "/detail/" + biNum; // 해당상세페이지 redirect
			if (babsangInfoVO.isBiClosed()) {
				// biClosed = true
				// 밥상이 이미 마감된 경우
				msg = "이미 마감 된 밥상입니다!!";
			} else {
				// 로그인 상태 중, 밥상이 마감되지 않은 경우
				UserInfoVO userInfoVO = (UserInfoVO) session.getAttribute("user"); // 로그인 유저객체
				userInfoVO.setBiNum(0);
				userInfoService.updateBiNum(userInfoVO); // 유저인포의 biNum 0으로 업데이트
				msg = "참가 취소되셨습니다!!";
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

	@GetMapping("/babsangClose/{biNum}")
	public String closeBabsang(Model m, @PathVariable("biNum") int biNum, HttpSession session) {
		// 밥상 마감하기
		String msg = "로그인해주세요!";
		String url = "/login";
		
		if (session.getAttribute("user") != null) {
			// 세션 로그인상태 유지중 마감하기 누른 후
			// 밥상인포 biClosed 1로 업데이트
			url = "/detail/" + biNum; // 해당 페이지 redirect
			if(userInfoService.getUserInfosByBiNum(biNum).size()==1) {
				// 참여인원이 방장 혼자밖에없는 경우 삭제만 가능!
				msg = "참여인원이 아무도 없습니다! 밥상 삭제를 이용해주세요. 😭";	
			}
			else if (babsangInfoService.blockJoin(biNum)) {
				// 밥상서비스의 마감메소드 정상 실행 시
				msg = "밥상 마감시 더이상 다른 유저가 참여할 수 없습니다!";
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

	@GetMapping("/babsangCloseCancle/{biNum}")
	public String closeBabsangCancle(Model m, @PathVariable("biNum") int biNum, HttpSession session) {
		// 밥상 마감 취소
		String msg = "로그인해주세요!";
		String url = "/login";
		if (session.getAttribute("user") != null) {
			// 세션 로그인상태 유지중 마감취소 누른 후
			// 밥상인포 biClosed 0로 업데이트
			if (babsangInfoService.cancleBlockJoin(biNum)) {
				// 밥상서비스의 마감메소드 정상 실행 시
				msg = "밥상마감취소!!";
				url = "/detail/" + biNum; // 해당 상세페이지 redirect
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

	@GetMapping("/successBabsang/{biNum}")
	public String goMeeting(Model m, @PathVariable("biNum") int biNum, HttpSession session) {
		String msg = "로그인 먼저 해주세요!";
		String url = "/login";
		// 맛남완료 버튼을 누르게되면 삭제와 동일하게 user의 biNum을 전부 0으로 초기화
		List<UserInfoVO> userList = userInfoService.getUserInfosByBiNum(biNum);
		UserInfoVO userSession = (UserInfoVO) session.getAttribute("user"); // 맛남 후 세션에 바로 적용해야하는 객체
		BabsangInfoVO babsangInfoVO = babsangInfoService.getBabsangInfoVO(biNum);
		if (session.getAttribute("user") != null) {
			// 세션 유저객체가 확인이 되었을 경우만
			if (!babsangInfoVO.isBiClosed()) {
				// biClosed = false 인 경우(마감기능안했을시)
				msg = "마감하기를 먼저 진행해주세요!!";
				url = "/detail/" + biNum;
			} else {
				// 마감기능 활성화 확인 시
				for (UserInfoVO user : userList) {
					// 맛남완료기능 실행 시 삭제랑 동일한 비지니스로직 수행
					// 해당 밥상에 참여중인 유저리스트에 모든 biNum을 0으로 초기화
					user.setBiNum(0);
					userSession.setBiNum(0);
					userInfoService.updateBiNum(user); // 맛남 성공 시 유저서비스의 update 실행
				}
				babsangInfoVO.setBiUserCnt(userList.size()); // 유저리스트의 사이즈(현재인원) 밥상에 set
				babsangInfoService.meetingSuccess(babsangInfoVO); // 이후 서비스 업데이트메소드 실행
				msg = "맛남완료!!!!";
				url = "/main";
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}
}