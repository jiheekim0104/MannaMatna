package com.ezen.mannamatna.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.ezen.mannamatna.vo.KakaoToken;
import com.ezen.mannamatna.vo.KakaoUserInfoVO;
import com.ezen.mannamatna.vo.NaverToken;
import com.ezen.mannamatna.vo.NaverUserInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserInfoService {
	private final String absolutePath = System.getProperty("user.dir"); // 시스템속성으로 프로젝트경로불러옴
	private final String uploadFilePath = absolutePath + "\\src\\main\\webapp\\resources\\upload";
	
	@Autowired
	private UserInfoMapper uiMapper;

	public int idChk(UserInfoVO userInfoVO) { //중복아이디를 찾음
		log.info("여기는서비스=====>{}", userInfoVO);
		return uiMapper.idChk(userInfoVO); //중복되는 수를 리턴
	}

	public int nicknameChk(UserInfoVO userInfoVO) { //중복닉네임을 찾음
		// TODO Auto-generated method stub
		return uiMapper.nicknameChk(userInfoVO); //중복되는 수를 리턴
	}

	public boolean login(UserInfoVO userInfoVO, HttpSession session) {
		log.info("userInfoVO=====>{}", userInfoVO);
		if (userInfoVO.getUiPwd().equals("0000") && userInfoVO.getUiId() == null) {//SNS로만 가입한 계정이 로그인할떄
			session.setAttribute("user", userInfoVO);
			return true;
		}
		if (uiMapper.selectUserInfoById(userInfoVO) != null) { // 입력한 id와 일치하는 유저가 DB에 있다면
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
			String inputPwd = userInfoVO.getUiPwd(); // 입력한 pwd
			if (userInfoVO.getUiId() == null) { //일반적인 로그인에서 쓰는건 아니고, sns 연동 계정이 본인 확인할때 사용함
				userInfoVO = uiMapper.selectUserInfoByNum(userInfoVO); //고유번호로 유저를 찾음
			} else {
				userInfoVO = uiMapper.selectUserInfoById(userInfoVO); //ID로 유저를 찾음
			}

			log.info("matches=====>{}", passwordEncoder.matches(inputPwd, userInfoVO.getUiPwd()));
			// visible
			// 딜레이
			// 히든
			if (passwordEncoder.matches(inputPwd, userInfoVO.getUiPwd())) { // 입력한 PWD와 DB에 있는 유저의 PWD가 일치하는지 확인함
				session.setAttribute("user", userInfoVO); //일치하면 세션으로
				if(findKakaoUser(userInfoVO)!=0) { //만약 카카오 DB에 일치하는 고유번호가 있다면 (일반유저+카카오연동)
					userInfoVO.setKuiId(findKakaoUser(userInfoVO)); //세션으로 넘어올때 카카오 고유번호도 담아줌
				} else if (findNaverUser(userInfoVO)!=null){//또는 네이버 DB에 일치하는 고유번호가 있다면 (일반유저+네이버연동)
					userInfoVO.setNuiId((findNaverUser(userInfoVO)));//세션으로 넘어올때 카카오 고유번호도 담아줌
				}
				return true;
			}
		}
		// visible
		// 딜레이
		// 히든

		return false; //입력한 ID가 없거나, 비번이 틀렸거나

	}

	public boolean findUser(UserInfoVO userInfoVO) { //ID로 유저찾기
		if (uiMapper.selectUserInfoById(userInfoVO) != null) {
			return true;
		}
		return false;
	}

	public long findKakaoUser(UserInfoVO userInfoVO) { // 일반회원->카카오연동가입시 카카오 고유번호를 받기위해 사용
		if (uiMapper.selectKakaoUserInfoByNum(userInfoVO) != null) { //카카오DB에서 일반회원의 고유번호와 일치하는 번호를 갖는 카카오유저가있는지확인하고
			return uiMapper.selectKakaoUserInfoByNum(userInfoVO).getKuiId(); //일치하는 번호를 가진 유저의 카카오 고유번호를 리턴
		}
		return 0;
	}

	public String findNaverUser(UserInfoVO userInfoVO) { // 일반회원->네이버연동가입시 카카오 고유번호를 받기위해 사용
		if (uiMapper.selectNaverUserInfoByNum(userInfoVO) != null) {//네이버DB에서 일반회원의 고유번호와 일치하는 번호를 갖는 네이버유저가있는지확인하고
			return uiMapper.selectNaverUserInfoByNum(userInfoVO).getNuiId(); //일치하는 번호를 가진 유저의 카카오 고유번호를 리턴
		}
		return null;
	}

	public boolean kakaoLogin(KakaoUserInfoVO kakaoUserInfoVO, HttpSession session) { //카카오 로그인
		String kakaoImgPath = kakaoUserInfoVO.getKakaoImgPath(); //카카오 프로필사진을 저장없이 바로 path로 받음
		kakaoUserInfoVO = uiMapper.selectKakaoUserInfo(kakaoUserInfoVO); //해당 카카오고유번호랑 일치하는 유저를 찾음
		
		if (kakaoUserInfoVO != null) { // 일치하는 유저가있다면
			UserInfoVO userInfoVO = new UserInfoVO(); 
			userInfoVO.setUiNum(kakaoUserInfoVO.getUiNum()); // 카카오 로그인 유저의 유저번호를 userInfoVO에 담기
			UserInfoVO newUserInfoVO = uiMapper.selectUserInfoByNum(userInfoVO);//그번호와 일치하는 userInfoVO를 찾아서 담고
			newUserInfoVO.setKuiId(kakaoUserInfoVO.getKuiId()); // 카카오 고유번호 추가함
			newUserInfoVO.setKakaoImgPath(kakaoImgPath); // 카카오 사진 경로 추가함
			// uiNum 정보만 가지고 있는 VO를 넣고 리턴은 다시 셀렉트문으로 찾은 모든 정보를 가지고 있는 uiVO 객체를 다시 돌려받는다.
			// uiVO와 kakaoVO가 연결되는것은 uiNum 인데 찾은 uiNum으로 uiVO를 셀렉트해서 찾는 쿼리문이 없었음
			// 매퍼에 해당 메소드 및 쿼리문 추가하여 kakaoLogin()에 추가함
			// 객체를 두개 생성하는게 비효율적이면 같은 userInfoVO에 계속 담아도 됨. 정상작동확인함
			log.info("카카오 로그인 서비스 =>{}", newUserInfoVO);
			session.setAttribute("user", newUserInfoVO);
			log.info("서비스에서 카카오 세션값확인={}", session.getAttribute("user"));
			return true;
		}
		return false;
	}

	public boolean naverLogin(NaverUserInfoVO naverUserInfoVO, HttpSession session) {
		String naverImgPath = naverUserInfoVO.getNaverImgPath();//네이버 프로필사진을 저장없이 바로 path로 받음
		naverUserInfoVO = uiMapper.selectNaverUserInfo(naverUserInfoVO);//해당 네이버고유번호랑 일치하는 유저를 찾음

		if (naverUserInfoVO != null) {// 일치하는 유저가있다면
			UserInfoVO userInfoVO = new UserInfoVO();
			userInfoVO.setUiNum(naverUserInfoVO.getUiNum()); // 네이버 로그인 유저의 유저번호를 userInfoVO에 담기
			UserInfoVO newUserInfoVO = uiMapper.selectUserInfoByNum(userInfoVO);//그번호와 일치하는 userInfoVO를 찾아서 담고
			newUserInfoVO.setNuiId(naverUserInfoVO.getNuiId());// 네이버 고유번호 추가함
			newUserInfoVO.setNaverImgPath(naverImgPath); // 카카오 사진 경로 추가함
			log.info("네이버 로그인 서비스 =>{}", newUserInfoVO);
			session.setAttribute("user", newUserInfoVO);
			log.info("서비스에서 네이버 세션값확인={}", session.getAttribute("user"));
			return true;
		}
		return false;
	}

	public boolean join(UserInfoVO userInfoVO) throws IllegalStateException, IOException {
		String fileName = null;
		if (userInfoVO.getUiId() == null) { // (1) SNS 연동으로 최초 가입하는 유저
			/*
			 * fileName = userInfoVO.getUiFilepath().replace(absolutePath +
			 * "\\src\\main\\webapp", ""); // 각 시스템환경마다 경로 // 공유되도록 수정
			 * userInfoVO.setUiFilepath(fileName);
			 */
			userInfoVO.setUiPwd("0000"); // null일수 없어서 초기값 설정해줌
			log.info("프로젝트절대경로===={}", System.getProperty("user.dir"));
			log.info("일반db에 넣기전={}", userInfoVO);
			long kakaoId = userInfoVO.getKuiId(); 
			log.info("kakaoId={}", kakaoId);
			String naverId = userInfoVO.getNuiId();
			log.info("naverId={}", naverId);
			if (kakaoId != 0) { // 카카오로 최초 가입 유저인경우
				if (uiMapper.insertUserInfo(userInfoVO) == 1) { // 일반 유저 테이블에 넣고 (이때 일반회원 고유 번호가 생김)
					userInfoVO = uiMapper.selectUserInfo(userInfoVO); // 넣은걸 가져와서
					log.info("일반db에 추가된거+카카오번호 추가한거={}", userInfoVO);
					userInfoVO.setKuiId(kakaoId); // 카카오 고유 아이디를 userInfoVO에 추가해줌
					log.info("일반db에 추가된거+카카오번호 추가한거={}", userInfoVO);
					KakaoUserInfoVO kakaoUserInfoVO = new KakaoUserInfoVO(); // 카카오 유저 객체를 만들어서
					kakaoUserInfoVO.setKuiId(userInfoVO.getKuiId()); // 카카오 고유 아이디를 kakaoUserInfoVO에 추가해줌
					kakaoUserInfoVO.setUiNum(userInfoVO.getUiNum()); // 일반회원 고유 번호를 kakaoUserInfoVO에 추가해줌
					log.info("카카오db에추가할거임={}", kakaoUserInfoVO);
					return uiMapper.insertKakaoUserInfo(kakaoUserInfoVO) == 1; // 카카오 유저 테이블에 인서트
				}
				return false;
			} else if (naverId != null) { // 네이버로 최초 가입 유저인경우
				if (uiMapper.insertUserInfo(userInfoVO) == 1) { // 일반 유저 테이블에 넣고 (이때 일반회원 고유 번호가 생김)
					userInfoVO = uiMapper.selectUserInfo(userInfoVO); // 넣은걸 가져와서
					log.info("일반db에 추가된거+네이버번호 추가한거={}", userInfoVO);
					userInfoVO.setNuiId(naverId); // 네이버 고유 아이디를 userInfoVO에 추가해줌
					log.info("일반db에 추가된거+네이버번호 추가한거={}", userInfoVO); 
					NaverUserInfoVO naverUserInfoVO = new NaverUserInfoVO(); // 네이버 유저 객체에 넣어주고
					naverUserInfoVO.setNuiId(userInfoVO.getNuiId()); // 네이버 고유 아이디를 kakaoUserInfoVO에 추가해줌
					naverUserInfoVO.setUiNum(userInfoVO.getUiNum()); // 일반회원 고유 번호를 kakaoUserInfoVO에 추가해줌
					log.info("네이버db에추가할거임={}", naverUserInfoVO);
					return uiMapper.insertNaverUserInfo(naverUserInfoVO) == 1; // 네이버 유저 테이블에 인서트
				}
				return false;
			}
			return false;
		} else if (userInfoVO.getUiId()!= null && (userInfoVO.getKuiId()!=0 ||userInfoVO.getNuiId()!=null)) {// (2) 일반가입 유저가 sns 연동 버튼을 누른경우
			long kakaoId = userInfoVO.getKuiId();
			log.info("kakaoId={}", kakaoId);
			String naverId = userInfoVO.getNuiId();
			log.info("naverId={}", naverId);
			if (kakaoId != 0) { // 일반가임 + 카카오 연동을 누른경우
				KakaoUserInfoVO kakaoUserInfoVO = new KakaoUserInfoVO(); // 카카오 유저 객체에 넣어주고
				kakaoUserInfoVO.setKuiId(userInfoVO.getKuiId());
				kakaoUserInfoVO.setUiNum(userInfoVO.getUiNum());
				log.info("카카오db에추가할거임={}", kakaoUserInfoVO);
				return uiMapper.insertKakaoUserInfo(kakaoUserInfoVO) == 1; // 카카오 유저 테이블에 인서트
			} else if (naverId != null) { // 일반가임 + 네이버 연동을 누른경우
				NaverUserInfoVO naverUserInfoVO = new NaverUserInfoVO(); // 네이버 유저 객체에 넣어주고
				naverUserInfoVO.setNuiId(userInfoVO.getNuiId());
				naverUserInfoVO.setUiNum(userInfoVO.getUiNum());
				log.info("네이버db에추가할거임={}", naverUserInfoVO);
				return uiMapper.insertNaverUserInfo(naverUserInfoVO) == 1; // 네이버 유저 테이블에 인서트
			}
			return false;
		} else {
			fileName = userInfoVO.getUiFile().getOriginalFilename(); // (3) 일반가입인 경우
			if ("".equals(fileName)) { // 가입시 등록한 사진이 없다면
				userInfoVO.setUiFilepath("/resources/upload/nophoto.png");// 기본사진을 경로로 추가함
			} else if (!"".equals(fileName)) { // 등록한 사진이 있다면
				int idx = fileName.lastIndexOf("."); //뒤에서 .의 위치를 찾음
				String extName = "";
				if (idx != -1) {
					extName = fileName.substring(idx); //. 위치 뒷부분 자른것(파일확장자명)
				}
				String name = UUID.randomUUID().toString(); // 랜덤하게 번호 생성
				File file = new File(uploadFilePath, name + extName); // uploadFilePath에 랜덤 번호 + . 위치 뒷부분 자른것(파일확장자명)으로 파일을 만들어서
				userInfoVO.getUiFile().transferTo(file);//지정경로에 저장
				userInfoVO.setUiFilepath("/resources/upload/" + name + extName);//경로도 저장
			}
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userInfoVO.setUiPwd(passwordEncoder.encode(userInfoVO.getUiPwd()));//입력한 비밀번호를 암호화한다음
		return uiMapper.insertUserInfo(userInfoVO) == 1; //일반유저 DB에 인서트
	}

	public boolean updateActive(UserInfoVO userInfoVO, HttpSession session) { // 고유번호와 일치하는 유저의 액티브만 변경 (비정상적 로그인시 사용)
		return uiMapper.updateUserInfoActive(userInfoVO) == 1;
	}

	public boolean delete(UserInfoVO userInfoVO, HttpSession session) { // 고유번호와 일치하는 유저의 액티브와 탈퇴사유 변경
		return uiMapper.deleteUserInfo(userInfoVO) == 1;
	}

	public boolean update(@ModelAttribute UserInfoVO userInfoVO, HttpSession session)
			throws IllegalStateException, IOException {
		UserInfoVO sessionUserInfo = (UserInfoVO) session.getAttribute("user"); //세션에 있는 유저(변경되기 이전의 정보를 가지고있음)를 가져오고
		log.info("누구시죠! ={}",sessionUserInfo);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userInfoVO.setUiPwd(passwordEncoder.encode(userInfoVO.getUiPwd())); // 이전페이지에서 바꾸려고 입력한 pwd를 시큐리티로 암호화해서 다시 지정함 (그냥 일반적인 숫자로 들어가면 로그인 할수없음, 로그인과정에서 암호화가 또 일어나기때문에 match flase됨)
		if ("".equals(userInfoVO.getUiFilepath())) {//(1) 프로필 변경시 사진을 업로드하지않음, 원래 사진을 그대로 쓰는경우
			if(sessionUserInfo.getUiId()==null) { //sns 만 연동된 계정이 업데이트를 한경우
				if(sessionUserInfo.getUiFilepath()==null) { //원래도 사진이 없었으면 
					if(sessionUserInfo.getKuiId()!=0){
						userInfoVO.setUiFilepath(sessionUserInfo.getKakaoImgPath());
					} else if(sessionUserInfo.getNuiId()!=null) {
						userInfoVO.setUiFilepath(sessionUserInfo.getNaverImgPath());
					}
				} else {
					userInfoVO.setUiFilepath(sessionUserInfo.getUiFilepath());
				}
				
			} else if(sessionUserInfo.getUiId()!=null&&(sessionUserInfo.getKuiId()!=0||sessionUserInfo.getNuiId()!=null)){//일반+ 연동계정이 업데이트를 한경우
					userInfoVO.setUiFilepath(sessionUserInfo.getUiFilepath());
			} else userInfoVO.setUiFilepath(sessionUserInfo.getUiFilepath()); //변경되기 이전의 유저가 가지고있는 filepath를 그대로 지정해줌
			log.info("바꿧음! ={}",sessionUserInfo.getUiFilepath());
			log.info("바꿧음! ={}",userInfoVO);
		} else if (userInfoVO.getUiFile() != null) {//(2) 이전에 사진과 다른 어떤 사진을 업로드 한 경우
			String fileName = userInfoVO.getUiFile().getOriginalFilename();
			int idx = fileName.lastIndexOf("."); //뒤에서 .의 위치를 찾음
			String extName = "";
			if (idx != -1) {
				extName = fileName.substring(idx); //. 위치 뒷부분 자른것(파일확장자명)
			}
			String name = UUID.randomUUID().toString(); // 랜덤하게 번호 생성
			File file = new File(uploadFilePath, name + extName); // uploadFilePath에 랜덤 번호 + . 위치 뒷부분 자른것(파일확장자명)으로 파일을 만들어서
			userInfoVO.getUiFile().transferTo(file); //지정경로에 저장
			userInfoVO.setUiFilepath("/resources/upload/" + name + extName); //경로도 저장
			if(sessionUserInfo.getKuiId()!=0){
				userInfoVO.setKuiId(sessionUserInfo.getKuiId());
			} else if(sessionUserInfo.getNuiId()!=null) {
				userInfoVO.setNuiId(sessionUserInfo.getNuiId());
			}
		}
		log.info("서비스/업데이트==>{}", userInfoVO);
		return uiMapper.updateUserInfo(userInfoVO) == 1; // 유저정보를 전체 업데이트함
	}

	public boolean updateBiNum(UserInfoVO userInfoVO) {
		// 인서트 후 넘겨받은 BabsangInfoVO의 biNum을 userInfoVO에 업데이트
		return uiMapper.updateUiBiNum(userInfoVO) == 1;
	}

	public KakaoToken requestKakaoToken(String addURI, String code) { // 인증코드로 token요청하기
		// addURI : Redirect URI에서 http://localhost 뒤에 붙는 부분을 달리해주기 위해 추가함
		// code : https://kauth.kakao.com/oauth/authorize? ~ 에서 넘겨줌
		String strUrl = "https://kauth.kakao.com/oauth/token"; // request를 보낼 주소(토큰발급용)
		KakaoToken kakaoToken = new KakaoToken(); // response를 받을 객체
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 파라미터 세팅
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			String redirectURI = "&redirect_uri=http://localhost" + addURI; 
			sb.append("grant_type=authorization_code"); // grant_type를 authorization_code로 고정등록해야함
			sb.append("&client_id=b288a9632f49edf850cff8d6eb985755"); // 카카오 개발자에서 발급받은 클라이언트 아이디
			sb.append(redirectURI);
			sb.append("&code=" + code); // 인자로 받아온 인증코드
			bw.write(sb.toString());
			bw.flush();// 실제 요청을 보내는 부분

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("responsecode(200이면성공): {}", responseCode);
			//여기서 400이면 Redirect URI가 일치하지 않는경우임

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// kakaoToken에 result를 KakaoToken.class 형식으로 변환하여 저장
			kakaoToken = mapper.readValue(result, KakaoToken.class);

			// api호출용 access token
			String access_Token = kakaoToken.getAccess_token();
			// access 토큰 만료되면 refresh token사용(유효기간 더 김)
			String refresh_Token = kakaoToken.getRefresh_token();

			log.info("access_token = {}", access_Token);
			log.info("refresh_token = {}", refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("카카오토큰생성완료>>>{}", kakaoToken);
		return kakaoToken;
	}

	public UserInfoVO requestKakaoUser(String accessToken) { // 카카오 유저 정보 요청
		log.info("requestUser 시작");
		String strUrl = "https://kapi.kakao.com/v2/user/me"; // request를 보낼 주소(유저요청용)
		UserInfoVO userInfoVO = new UserInfoVO(); // response를 받을 객체
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 전송할 header 작성, 인자로 받은 access_token전송
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("requestUser의 responsecode(200이면성공): {}", responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();

			log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// 결과 json을 HashMap 형태로 변환하여 resultMap에 담음
			HashMap<String, Object> resultMap = mapper.readValue(result, HashMap.class);
			// json 파싱하여 id 가져오기
			Long id = Long.valueOf(String.valueOf(resultMap.get("id")));

			// 결과json 안에 properties key는 json Object를 value로 가짐
			HashMap<String, Object> properties = (HashMap<String, Object>) resultMap.get("properties");
			String profile_image = (String) properties.get("profile_image");
			userInfoVO.setKakaoImgPath(profile_image); //저장하지않고 바로 경로를 담아줌
			String nickname = (String) properties.get("nickname");

			// 결과json 안에 kakao_account key는 json Object를 value로 가짐
			HashMap<String, Object> kakao_account = (HashMap<String, Object>) resultMap.get("kakao_account");

			String age_range = (String) kakao_account.get("age_range"); // 10~19 형태로 줌
			String gender = (String) kakao_account.get("gender"); // T or F

			// 유저정보 세팅
			userInfoVO.setKuiId(id); // 카카오 고유번호 
			userInfoVO.setUiNickname(nickname);// 닉네임

			int uiAge = 0;  // 연령대에 맞춰서 나눠서 저장
			if (age_range.startsWith("0") || age_range.startsWith("1")) {
				uiAge = 10;
			} else if (age_range.startsWith("2")) {
				uiAge = 20;
			} else if (age_range.startsWith("3")) {
				uiAge = 30;
			} else if (age_range.startsWith("4")) {
				uiAge = 40;
			} else {
				uiAge = 50;
			}

			userInfoVO.setUiAge(uiAge); // 연령대
			
			boolean uiGender = true;
			if (gender.equals("female")) {
				uiGender = false;
			}
			userInfoVO.setUiGender(uiGender);// 성별
			
//			원래는 사진을 저장하는 방법을 썼으나 바로 불러오는 방법으로 수정함	
//			URL imageUrl = null;
//			InputStream inputStream = null;
//			OutputStream outputStream = null;
//			String name = null;
//			try {
//				imageUrl = new URL(profile_image);
//				inputStream = imageUrl.openStream();
//				name = UUID.randomUUID().toString();
//				outputStream = new FileOutputStream(uploadFilePath + "\\" + name + ".jpg");
//				while (true) {
//					int data = inputStream.read();
//					if (data == -1) {
//						break;
//					}
//					outputStream.write(data); // 이미지 데이터값을 컴퓨터 또는 서버공간에 저장
//				}
//				inputStream.close();
//				outputStream.close();
//			} catch (Exception e) {
//				// 예외처리
//				e.printStackTrace();
//			} finally {
//				if (inputStream != null) {
//					inputStream.close();
//				}
//				if (outputStream != null) {
//					outputStream.close();
//				}
//			}
//			userInfoVO.setUiFilepath(uploadFilePath + "\\" + name + ".jpg");// 사진

			log.info("resultMap= {}", resultMap);
			log.info("properties= {}", properties);
			log.info("userInfoVO={}", userInfoVO);

		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("서비스에서 받은 카카오 유저={}", userInfoVO);
		return userInfoVO;
	}

	public long requestUserForKuiId(String accessToken) { // 카카오 유저 정보 요청인데 카카오 고유번호만 필요한 경우 ( 일반회원 + 카카오 연동시 사용)
		log.info("카카오 requestUser 시작");
		String strUrl = "https://kapi.kakao.com/v2/user/me"; // request를 보낼 주소
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 전송할 header 작성, 인자로 받은 access_token전송
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("requestUser의 responsecode(200이면성공): {}", responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();

			log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// 결과 json을 HashMap 형태로 변환하여 resultMap에 담음
			HashMap<String, Object> resultMap = mapper.readValue(result, HashMap.class);
			// json 파싱하여 id 가져오기
			return Long.valueOf(String.valueOf(resultMap.get("id")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public NaverToken requestNaverToken(String addURI, String code, String state) throws UnsupportedEncodingException { // 인증코드로 token요청하기
		// addURI : Redirect URI에서 http://localhost 뒤에 붙는 부분을 달리해주기 위해 추가함
		// state :  세션 유지 및 위조 방지용 상태 토큰, 정상적인 요청인지 비정상적인 요청인지 확인가능
		NaverToken naverToken = new NaverToken();
		String clientId = "BSeMnF9B1CusMX9DeEg8";// 애플리케이션 클라이언트 아이디값
		String clientSecret = "fpEWA5y2fc";// 애플리케이션 클라이언트 시크릿값
//	    code = request.getParameter("code");
//	    state = request.getParameter("state");
		String redirectURI = URLEncoder.encode("http://localhost" + addURI, "UTF-8");
		String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code" + "&client_id=" + clientId
				+ "&client_secret=" + clientSecret + "&redirect_uri=" + redirectURI + "&code=" + code + "&state="
				+ state;
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuilder res = new StringBuilder();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			String result = res.toString();
			if (responseCode == 200) {
				log.info("res = {}", res);
			}
			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// kakaoToken에 result를 KakaoToken.class 형식으로 변환하여 저장
			log.info("NaverToken.class = {}", NaverToken.class);
			naverToken = mapper.readValue(result, NaverToken.class);
			log.info("naverToken = {}", naverToken);
			String access_Token = naverToken.getAccess_token();
			String refresh_Token = naverToken.getRefresh_token();
			log.info("access_token = {}", access_Token);
			log.info("refresh_token = {}", refresh_Token);
			br.close();
		} catch (Exception e) {
			// Exception 로깅
		}
		log.info("네이버토큰생성완료>>>{}", naverToken);
		return naverToken;
	}
		
	public UserInfoVO requestNaverUser(String access_token) { // 네이버 유저 정보 요청
		log.info("네이버 requestUser 시작");
		String strUrl = "https://openapi.naver.com/v1/nid/me"; // request를 보낼 주소
		UserInfoVO userInfoVO = new UserInfoVO(); // response를 받을 객체
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 전송할 header 작성, 인자로 받은 access_token전송
			conn.setRequestProperty("Authorization", "Bearer " + access_token);

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("requestUser의 responsecode(200이면성공): {}", responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();

			log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// 결과 json을 HashMap 형태로 변환하여 resultMap에 담음
			HashMap<String, Object> resultMap = mapper.readValue(result, HashMap.class);

			// 결과json 안에 response key는 json Object를 value로 가짐
			HashMap<String, Object> response = (HashMap<String, Object>) resultMap.get("response");
			String id = (String) response.get("id");
			String nickname = (String) response.get("nickname"); // 유니코드 형태
			String profile_image = (String) response.get("profile_image"); // http 주소
			userInfoVO.setNaverImgPath(profile_image);  //저장하지않고 바로 경로를 담아줌
			String age = (String) response.get("age"); // 나이 범위
			String gender = (String) response.get("gender"); // M or F

			// 유저정보 세팅
			userInfoVO.setNuiId(id); // 네이버 고유번호 
			
			
			StringBuffer resultNickname = new StringBuffer(); // 유니코드인 닉네임 변환 과정

			for (int i = 0; i < nickname.length(); i++) {
				if (nickname.charAt(i) == '\\' && nickname.charAt(i + 1) == 'u') {
					Character c = (char) Integer.parseInt(nickname.substring(i + 2, i + 6), 16);
					resultNickname.append(c);
					i += 5;
				} else {
					resultNickname.append(nickname.charAt(i));
				}
			}

			userInfoVO.setUiNickname(resultNickname.toString());// 닉네임

			int uiAge = 0;  // 연령대에 맞춰서 나눠서 저장
			if (age.startsWith("0") || age.startsWith("1")) {
				uiAge = 10;
			} else if (age.startsWith("2")) {
				uiAge = 20;
			} else if (age.startsWith("3")) {
				uiAge = 30;
			} else if (age.startsWith("4")) {
				uiAge = 40;
			} else {
				uiAge = 50;
			}

			userInfoVO.setUiAge(uiAge); // 연령대

			boolean uiGender = true;
			if (gender.equals("F")) {
				uiGender = false;
			}
			userInfoVO.setUiGender(uiGender);// 성별
//			원래는 사진을 저장하는 방법을 썼으나 바로 불러오는 방법으로 수정함			
//			URL imageUrl = null;
//			InputStream inputStream = null;
//			OutputStream outputStream = null;
//			String name = null;
//			try {
//				imageUrl = new URL(profile_image);
//				inputStream = imageUrl.openStream();
//				name = UUID.randomUUID().toString();
//				outputStream = new FileOutputStream(uploadFilePath + "\\" + name + ".jpg");
//
//				while (true) {
//					int data = inputStream.read();
//					if (data == -1) {
//						break;
//					}
//					outputStream.write(data); // 이미지 데이터값을 컴퓨터 또는 서버공간에 저장
//				}
//
//				inputStream.close();
//				outputStream.close();
//
//			} catch (Exception e) {
//				// 예외처리
//				e.printStackTrace();
//			} finally {
//				if (inputStream != null) {
//					inputStream.close();
//				}
//				if (outputStream != null) {
//					outputStream.close();
//				}
//			}
//
//			userInfoVO.setUiFilepath(uploadFilePath + "\\" + name + ".jpg");// 사진

			log.info("resultMap= {}", resultMap);
			log.info("response= {}", response);
			log.info("userInfoVO={}", userInfoVO);

		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("서비스에서 받을 네이버 유저={}", userInfoVO);
		return userInfoVO;
	}

	public String requestNaverUserForNuiId(String access_token) { // 네이버 유저 정보 요청인데 네이버 고유번호만 필요한 경우 ( 일반회원 + 네이버 연동시 사용)
		log.info("네이버 requestUser 시작");
		String strUrl = "https://openapi.naver.com/v1/nid/me"; // request를 보낼 주소
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 전송할 header 작성, 인자로 받은 access_token전송
			conn.setRequestProperty("Authorization", "Bearer " + access_token);

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("requestUser의 responsecode(200이면성공): {}", responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();

			log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// 결과 json을 HashMap 형태로 변환하여 resultMap에 담음
			HashMap<String, Object> resultMap = mapper.readValue(result, HashMap.class);

			
			// 결과json 안에 response key는 json Object를 value로 가짐
			HashMap<String, Object> response = (HashMap<String, Object>) resultMap.get("response");
			return (String)response.get("id");
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<UserInfoVO> getUserInfos(UserInfoVO userInfoVO, HttpSession session) {
		// 회원데이터를 모두 담은 객체 get
		// session데이터로 추후 관리자가 아닐 경우 검사
		return uiMapper.selectUserInfos(userInfoVO);
	}

	public List<UserInfoVO> getUserInfosByBiNum(int biNum) {
		// 밥상등록시 ui 테이블에 biNum을 업데이트했다.
		// 해당 메소드 실행시 밥상작성자로 판단하게된 uiVO객체를 리턴받는다.
		return uiMapper.selectUserInfosByBiNum(biNum);
	}

	public UserInfoVO getUserInfoFromBabsang(int uiNum) {
		// 밥상등록 시 밥상정보에 등록된 uiNum으로 유저리스트에서 해당유저(작성자)정보를 찾는다.
		// 해당 메소드 밥상상세의 다른 유저 프로필보기 기능 수행시에도 사용
		return uiMapper.selectUserInfoFromBabsang(uiNum);
	}

	public List<UserInfoVO> getUserInfosByCredat(HttpSession session) {
		// 날짜별 가입인원수 조회서비스
		List<UserInfoVO> userList = uiMapper.selectUserInfosByCredat();
		log.info("서비스에서 유저리스트의 정보 {}", userList);
		return uiMapper.selectUserInfosByCredat();
	}
	// 탈퇴신청 유저 페이징
	public PageInfo<UserInfoVO> getPagingUiActive1(UserInfoVO userInfoVO) {
		PageHelper.startPage(userInfoVO.getPage(), userInfoVO.getRows());
		return new PageInfo<>(uiMapper.selectUserInfosByUiActive1(userInfoVO));
	}
	// 정지유저 페이징
	public PageInfo<UserInfoVO> getPagingUiActive2(UserInfoVO userInfoVO) {
		PageHelper.startPage(userInfoVO.getPage(), userInfoVO.getRows());
		return new PageInfo<>(uiMapper.selectUserInfosByUiActive2(userInfoVO));
	}

}
