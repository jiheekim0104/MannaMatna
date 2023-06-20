package com.ezen.mannamatna.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.mannamatna.service.UserInfoService;
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.ezen.mannamatna.vo.KakaoToken;
import com.ezen.mannamatna.vo.KakaoUserInfoVO;
import com.ezen.mannamatna.vo.NaverToken;
import com.ezen.mannamatna.vo.NaverUserInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class UserInfoController {
	
	@Autowired
	UserInfoService uiService;
	
	@GetMapping("/") 
	public String home(@ModelAttribute UserInfoVO userInfoVO, Model m) {
		return "index"; 
	}
	
	@PostMapping("/") 
	public String home() {
		return "index"; 
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String gologin(@ModelAttribute UserInfoVO userInfoVO, BabsangInfoVO babsang, HttpSession session, Model m) {
		log.info("=============1>{}",userInfoVO);
		log.info("=============1>{}",uiService.login(userInfoVO, session));
		if(uiService.login(userInfoVO, session)) {
			log.info("=============2>{}",userInfoVO);
			userInfoVO = (UserInfoVO) session.getAttribute("user");
			log.info("=============>{}",userInfoVO);
			if(userInfoVO.getUiActive()==1) {
				m.addAttribute("msg","탈퇴처리된 계정입니다.");
				session.invalidate();
				return "user/login";
			}
			m.addAttribute("url", "/main");
			m.addAttribute("msg", "로그인성공");
			return "common/msg";
	}
	m.addAttribute("msg","아이디나 비밀번호가 잘못되었습니다.");
	return "user/login";
}
	
	@GetMapping("/kakaoPost")
	  public String kakaoJoin(@RequestParam(value = "code",required = false) String code, HttpSession session,  Model m) throws IllegalStateException, IOException{
		UserInfoVO userInfoVO = null;
		if(code!=null){//카카오측에서 보내준 code가 있다면 출력
            System.out.println("code = " + code);
            KakaoToken kakaoToken = uiService.requestToken("/kakaoPost/",code); //카카오 토큰 요청
            userInfoVO = uiService.requestUser(kakaoToken.getAccess_token()); //유저정보 요청
            log.info("user = {}",userInfoVO);
            log.info("kakoToken = {}", kakaoToken);
//			session.setAttribute("user", userInfoVO); 
        }
		if(uiService.join(userInfoVO)) {
			m.addAttribute("msg","회원가입에 성공하셨습니다.");
			return "user/login";
		}
		return "user/kakaoPost";
	}
	
	@GetMapping("/kakaoLogin") 
	public String kakaoLogin(@RequestParam(value = "code",required = false) String code, HttpSession session,  Model m) throws IllegalStateException, IOException{
		UserInfoVO userInfoVO = null;
		KakaoUserInfoVO kakaoUserInfoVO = new KakaoUserInfoVO();
		// 카카오 로그인해서 id 돌려받고, 그 아이디를 가진 유저가 있는지 인포 돌아서 확인 이때 비번은 0000 고정임
		
		if(code!=null){//카카오측에서 보내준 code가 있다면 출력
            System.out.println("code = " + code);
            KakaoToken kakaoToken = uiService.requestToken("/kakaoLogin/",code); //카카오 토큰 요청
            log.info("여기서 들어감! kakaoUserInfoVO={}",kakaoUserInfoVO);
            userInfoVO = uiService.requestUser(kakaoToken.getAccess_token()); //유저정보 요청
            kakaoUserInfoVO.setKuiId(userInfoVO.getKuiId()); // userInfoVO가 가지고있는 카카오 id값을 kakaoUserInfoVO에 넣음
            kakaoUserInfoVO.setKakaoImgPath(userInfoVO.getKakaoImgPath());
            log.info("로그인요청한 kakaoUserInfoVO={}",kakaoUserInfoVO);
            if(uiService.kakaoLogin(kakaoUserInfoVO, session)) { // 카카오유저테이블에 그 id를 가지는 카카오유저가있다면
            	 m.addAttribute("url","/main"); 
            	 m.addAttribute("msg", "로그인성공");
            	 return "common/msg";
            }
            m.addAttribute("msg","카카오 가입 유저가 아닙니다.");
    		return "user/login";
        }
	
		m.addAttribute("msg","아이디나 비밀번호가 잘못되었습니다.");
		return "user/login";
}
	
	
	@GetMapping("/naverPost")
	public String NaverJoin(@RequestParam(value = "code",required = false) String code,@RequestParam(value = "state",required = false) String state, HttpSession session,  Model m) throws IllegalStateException, IOException {
        log.info("callback controller");
        UserInfoVO userInfoVO = null;
        if(code!=null){//네이버측에서 보내준 code가 있다면 출력
            System.out.println("code = " + code);
            NaverToken naverToken = uiService.requestNaverToken("/naverPost/",code,state); //네이버 토큰 요청
            userInfoVO = uiService.requestNaverUser(naverToken.getAccess_token()); //유저정보 요청
            log.info("user = {}",userInfoVO);
            log.info("naverToken = {}", naverToken);
        }
		if(uiService.join(userInfoVO)) {
			m.addAttribute("msg","회원가입에 성공하셨습니다.");
			return "user/login";
		}
        return "user/callback";
    }
	
	@GetMapping("/naverLogin")
	public String naverLogin(@RequestParam(value = "code",required = false) String code,@RequestParam(value = "state",required = false) String state, HttpSession session,  Model m) throws IllegalStateException, IOException{
		UserInfoVO userInfoVO = null;
		NaverUserInfoVO naverUserInfoVO = new NaverUserInfoVO();
		// 네이버 로그인해서 id 돌려받고, 그 아이디를 가진 유저가 있는지 인포 돌아서 확인 이때 비번은 0000 고정임
		if(code!=null){//네이버측에서 보내준 code가 있다면 출력
            System.out.println("code = " + code);
            NaverToken naverToken = uiService.requestNaverToken("/naverLogin/",code,state); //네이버 토큰 요청
            log.info("여기서 들어감! naverUserInfoVO={}",naverUserInfoVO);
            userInfoVO = uiService.requestNaverUser(naverToken.getAccess_token()); //유저정보 요청
            naverUserInfoVO.setNuiId(userInfoVO.getNuiId()); // userInfoVO가 가지고있는 네이버 id값을 naverUserInfoVO에 넣음
            naverUserInfoVO.setNaverImgPath(userInfoVO.getNaverImgPath());
            log.info("로그인요청한 naverUserInfoVO={}",naverUserInfoVO);
            if(uiService.naverLogin(naverUserInfoVO, session)) { // 네이버유저테이블에 그 id를 가지는 네이버유저가있다면
            	 m.addAttribute("url","/main"); 
            	 m.addAttribute("msg", "로그인성공");
            	 return "common/msg";
            }
            m.addAttribute("msg","네이버 가입 유저가 아닙니다.");
    		return "user/login";
        }
		m.addAttribute("msg","아이디나 비밀번호가 잘못되었습니다.");
		return "user/login";
}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "user/login";
	}
	
	@GetMapping("/join") 
	public String join() {
		return "user/join"; 
	}
	
	@PostMapping("/join-ok")
	public String joinOk(@ModelAttribute UserInfoVO userInfoVO,HttpSession session, Model m) throws IllegalStateException, IOException {
		log.info("조인ok=====>{}",userInfoVO);
		if(uiService.join(userInfoVO)) {
			m.addAttribute("msg","회원가입에 성공하셨습니다.");
			return "user/login";
		}
		return "user/join";
	}
	
	@PostMapping("/idChk")
	@ResponseBody
	public Map<String, Integer> idChk(@ModelAttribute UserInfoVO userInfoVO, @RequestBody Map<String, String> checkMap) {
		Map<String, Integer> map = new HashMap<>();
		userInfoVO.setUiId(checkMap.get("uiId"));
		log.info("여기는 컨트롤러1===========>{}",userInfoVO.getUiId());
		int result = uiService.idChk(userInfoVO);
		log.info("여기는 컨트롤러2===========>{}",result);
		map.put("result", result);
		return map; 
	}
	
	@PostMapping("/nicknameChk")
	@ResponseBody
	public Map<String, Integer> nicknameChk(@ModelAttribute UserInfoVO userInfoVO, @RequestBody Map<String, String> checkMap) {
		userInfoVO.setUiNickname(checkMap.get("uiNickname"));
		log.info("여기는 컨트롤러1===========>{}",userInfoVO.getUiNickname());
		int result = uiService.nicknameChk(userInfoVO);
		log.info("여기는 컨트롤러2===========>{}",result);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map; 
	}
	
	
	@GetMapping("/profile") // 프로필 화면 연결
	public String profile(@ModelAttribute UserInfoVO userInfoVO, HttpSession session) {
		userInfoVO = (UserInfoVO) session.getAttribute("user");
		log.info("컨트롤러/프로필요청 ==>{}",userInfoVO);
		return "user/user-profile";
	}
	
	@GetMapping("/profile/{uiNum}")
	public String profileByUiNum(@PathVariable("uiNum") int uiNum, HttpSession session, Model m) {
		// uiNum을 파라미터로 하는 프로필 화면 전환
		UserInfoVO userInfoVO = uiService.getUserInfoFromBabsang(uiNum); // 상세페이지의 밥상메이커(작성자)객체의 uiNum을 파라미터로 받았다.
		m.addAttribute("user", userInfoVO); // 해당객체를 모델에 넣어준 후 프로필화면으로 보내준다.
		return "user/user-profile";
	}
	
	@GetMapping("/check-update") // 프로필 수정 버튼을 누른 경우 
	public String checkUpdate(Model m) {
		return "user/user-check-update";
	}
	
	@PostMapping("/check-update") // 수정 버튼을 누르고 비밀번호가 일치하는지 확인
	public String checkUpdateOk(@ModelAttribute UserInfoVO userInfoVO, HttpSession session,Model m) {
		String inputPwd = userInfoVO.getUiPwd();
		userInfoVO = (UserInfoVO) session.getAttribute("user");
		userInfoVO.setUiPwd(inputPwd);
		if(uiService.login(userInfoVO, session)) { //유저를 찾는 로직이 같아서 login 씀
			return "/user/user-profile-update";
		}
		m.addAttribute("msg","비밀번호가 잘못되었습니다.");	
		return "user/user-check-update";
	}
	
	@GetMapping("/user/user-profile-update")
	public String updateProfile() {
		return "user/user-profile-update";
	}
	
	@PostMapping("/profile-update")
	public String updateProfileOk(@ModelAttribute UserInfoVO userInfoVO, HttpSession session, Model m) throws IllegalStateException, IOException {
		UserInfoVO sessionUserInfo = (UserInfoVO) session.getAttribute("user");
		log.info("sessionUserInfo 확인={}",sessionUserInfo);
		userInfoVO.setUiNum(sessionUserInfo.getUiNum());
		userInfoVO.setBiNum(sessionUserInfo.getBiNum());
		userInfoVO.setUiCredat(sessionUserInfo.getUiCredat());
		userInfoVO.setUiId(sessionUserInfo.getUiId());
		userInfoVO.setUiActive(sessionUserInfo.getUiActive());
		userInfoVO.setUiDel(sessionUserInfo.getUiDel());
		if(uiService.update(userInfoVO, session)) {
			m.addAttribute("msg","정보수정에 성공하셨습니다.");
			session.setAttribute("user", userInfoVO);
			return "user/user-profile";
		}
		m.addAttribute("msg","정보수정에 실패하였습니다.");
		return "user/user-profile";
	}
	
	
	
	@GetMapping("/withdraw")
	public String withdraw() {// 사유씀 
		return "user/user-withdraw"; 
	}
	
	@PostMapping("/withdraw") // 탈퇴사유를 입력하고 확인을 누른 경우 
	public String goWithdraw(@ModelAttribute UserInfoVO userInfoVO,HttpSession session) {
		UserInfoVO sessionUserInfo = (UserInfoVO) session.getAttribute("user");
		sessionUserInfo.setUiDel(userInfoVO.getUiDel()); //사유입력한거 일단 임시 저장
		userInfoVO = sessionUserInfo;
		return "user/user-check-withdraw";
	}
	
	@GetMapping("/user/user-check-withdraw") // 비밀번호 재확인
	public String checkWithdraw() {
		return "user/user-check-withdraw";
	}
	
	@PostMapping("/check-withdraw")
	public String checkWithdrawOk(@ModelAttribute UserInfoVO userInfoVO,HttpSession session,Model m) {
		log.info("userInfoVO={}",userInfoVO);
		String inputPwd = userInfoVO.getUiPwd();
		userInfoVO = (UserInfoVO) session.getAttribute("user");
		userInfoVO.setUiPwd(inputPwd);
		log.info("넘어갈userInfoVO={}",userInfoVO);
		if(uiService.login(userInfoVO, session)) { //유저를 찾는 로직이 같아서 login 씀
			log.info("컨트롤러 체크-윗드로우 ==>{}",userInfoVO);
			userInfoVO.setUiActive(1); //엑티브 1처리
			if(uiService.delete(userInfoVO, session)) { // 앞에서 입력한 사유를 이때 저장함 (업데이트)
				m.addAttribute("msg","정상적으로 탈퇴처리되었습니다.");
				session.invalidate();
			}	
			return "babsang/babsang-list";
		}
		m.addAttribute("msg","비밀번호가 잘못되었습니다.");	
		return "user/user-check-withdraw";
		
		
//		UserInfoVO sessionUserInfo = (UserInfoVO) session.getAttribute("user");
//		
//		sessionUserInfo.setUiActive(1); //엑티브 1처리
//		userInfoVO = sessionUserInfo;
	
	}
	
	
	
}
