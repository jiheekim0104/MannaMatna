package com.ezen.mannamatna.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import com.ezen.mannamatna.vo.KakaoToken;
import com.ezen.mannamatna.vo.KakaoUserInfoVO;
import com.ezen.mannamatna.vo.NaverToken;
import com.ezen.mannamatna.vo.NaverUserInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserInfoService {
	private final String absolutePath = System.getProperty("user.dir"); // 시스템속성으로 프로젝트경로불러옴
	private final String uploadFilePath = absolutePath + "\\src\\main\\webapp\\resources\\upload";
	@Autowired
	private UserInfoMapper uiMapper;

	public int idChk(UserInfoVO userInfoVO) {
		log.info("여기는서비스=====>{}", userInfoVO);
		return uiMapper.idChk(userInfoVO);
	}

	public int nicknameChk(UserInfoVO userInfoVO) {
		// TODO Auto-generated method stub
		return uiMapper.nicknameChk(userInfoVO);
	}

	public boolean login(UserInfoVO userInfoVO, HttpSession session) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String inputPwd = userInfoVO.getUiPwd();
		userInfoVO = uiMapper.selectUserInfoById(userInfoVO);
		if(passwordEncoder.matches(inputPwd, userInfoVO.getUiPwd())) {
			log.info("확인하려는 유저 =>{}", userInfoVO);
			session.setAttribute("user", userInfoVO);
			return true;
		}
//		userInfoVO = uiMapper.selectUserInfoForLogin(userInfoVO);
		

		return false;
	}

	public boolean kakaoLogin(KakaoUserInfoVO kakaoUserInfoVO, HttpSession session) { // 여기서 문제가 생기는듯 ㅇㅅ ㅇ?

		log.info("확인하려는 유저 =>{}", kakaoUserInfoVO);
		String kakaoImgPath = kakaoUserInfoVO.getKakaoImgPath();
		kakaoUserInfoVO = uiMapper.selectKakaoUserInfo(kakaoUserInfoVO);
		log.info("돌려받은 유저 =>{}", kakaoUserInfoVO); // 카db에 제대로 안올라갔으니까 여기서 못찾아온거같음ㅇㅇ
		if (kakaoUserInfoVO != null) {
			UserInfoVO userInfoVO = new UserInfoVO();
			userInfoVO.setUiNum(kakaoUserInfoVO.getUiNum()); // 카카오 로그인 유저의 유저번호를 userInfoVO에 담기
			UserInfoVO newUserInfoVO = uiMapper.selectUserInfoByKakao(userInfoVO);
			newUserInfoVO.setKakaoImgPath(kakaoImgPath);
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
	
	public boolean naverLogin(NaverUserInfoVO naverUserInfoVO, HttpSession session) { // 여기서 문제가 생기는듯 ㅇㅅ ㅇ?

		log.info("확인하려는 유저 =>{}", naverUserInfoVO);
		String naverImgPath = naverUserInfoVO.getNaverImgPath();
		naverUserInfoVO = uiMapper.selectNaverUserInfo(naverUserInfoVO);
		
		log.info("돌려받은 유저 =>{}", naverUserInfoVO); 
		if (naverUserInfoVO != null) {
			UserInfoVO userInfoVO = new UserInfoVO();
			userInfoVO.setUiNum(naverUserInfoVO.getUiNum()); // 네이버 로그인 유저의 유저번호를 userInfoVO에 담기
			UserInfoVO newUserInfoVO = uiMapper.selectUserInfoByKakao(userInfoVO);
			newUserInfoVO.setNaverImgPath(naverImgPath);
			log.info("네이버 로그인 서비스 =>{}", newUserInfoVO);
			session.setAttribute("user", newUserInfoVO);
			log.info("서비스에서 네이버 세션값확인={}", session.getAttribute("user"));
			return true;
		}
		return false;
	}
	
	

	public boolean join(UserInfoVO userInfoVO) throws IllegalStateException, IOException {
		String fileName = null;
		if (userInfoVO.getUiFile() == null) { // sns 연동 유저
			/*
			 * fileName = userInfoVO.getUiFilepath().replace(absolutePath +
			 * "\\src\\main\\webapp", ""); // 각 시스템환경마다 경로 // 공유되도록 수정
			 * userInfoVO.setUiFilepath(fileName);
			 */
			userInfoVO.setUiPwd("0000"); // null일수 없어서 넣어둠
			log.info("프로젝트절대경로===={}", System.getProperty("user.dir"));
			log.info("일반db에 넣기전={}", userInfoVO);
			long kakaoId = userInfoVO.getKuiId();
			log.info("kakaoId={}", kakaoId);
			String naverId = userInfoVO.getNuiId();
			log.info("naverId={}", naverId);
			if(kakaoId!=0) { // 카카오 가입 유저인경우
				if (uiMapper.insertUserInfo(userInfoVO) == 1) { // 일반 유저 테이블에 넣고
					userInfoVO = uiMapper.selectUserInfo(userInfoVO); // 넣은걸 가져와서

					log.info("일반db에 추가된거+카카오번호 추가한거={}", userInfoVO);
					userInfoVO.setKuiId(kakaoId);
					log.info("일반db에 추가된거+카카오번호 추가한거={}", userInfoVO);
					KakaoUserInfoVO kakaoUserInfoVO = new KakaoUserInfoVO(); // 카카오 유저 객체에 넣어주고
					kakaoUserInfoVO.setKuiId(userInfoVO.getKuiId());
					kakaoUserInfoVO.setUiNum(userInfoVO.getUiNum());
					log.info("카카오db에추가할거임={}", kakaoUserInfoVO);
					return uiMapper.insertKakaoUserInfo(kakaoUserInfoVO) == 1; // 카카오 유저 테이블에 인서트
				}
				return false;
			} else if(naverId!=null) { // 네이버 가입 유저인경우
				if (uiMapper.insertUserInfo(userInfoVO) == 1) { // 일반 유저 테이블에 넣고
					userInfoVO = uiMapper.selectUserInfo(userInfoVO); // 넣은걸 가져와서
					log.info("일반db에 추가된거+네이버번호 추가한거={}", userInfoVO);
					userInfoVO.setNuiId(naverId);
					log.info("일반db에 추가된거+네이버번호 추가한거={}", userInfoVO);
					NaverUserInfoVO naverUserInfoVO = new NaverUserInfoVO(); // 네이버 유저 객체에 넣어주고
					naverUserInfoVO.setNuiId(userInfoVO.getNuiId());
					naverUserInfoVO.setUiNum(userInfoVO.getUiNum());
					log.info("네이버db에추가할거임={}", naverUserInfoVO);
					return uiMapper.insertNaverUserInfo(naverUserInfoVO) == 1; // 네이버 유저 테이블에 인서트
				}
				return false;
			}
			return false;
		} else {
			fileName = userInfoVO.getUiFile().getOriginalFilename();
			if ("".equals(fileName)) {
				userInfoVO.setUiFilepath("/resources/upload/nophoto.png");
			} else if (!"".equals(fileName)) {
				int idx = fileName.lastIndexOf(".");
				String extName = "";
				if (idx != -1) {
					extName = fileName.substring(idx);
				}
				String name = UUID.randomUUID().toString();
				log.info("name====>{}", name);
				File file = new File(uploadFilePath, name + extName);
				userInfoVO.getUiFile().transferTo(file);
				userInfoVO.setUiFilepath("/resources/upload/" + name + extName);
				log.info("저장됨====>{}", userInfoVO);
			}
		}
		log.info("fileName====>{}", fileName);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userInfoVO.setUiPwd(passwordEncoder.encode(userInfoVO.getUiPwd()));
		return uiMapper.insertUserInfo(userInfoVO) == 1;
	}

	public boolean delete(UserInfoVO userInfoVO, HttpSession session) {
		return uiMapper.deleteUserInfo(userInfoVO) == 1;
	}

	public boolean update(@ModelAttribute UserInfoVO userInfoVO, HttpSession session)
			throws IllegalStateException, IOException {
		log.info("userInfoVO====>{}", userInfoVO);
		String fileName = userInfoVO.getUiFile().getOriginalFilename();
		log.info("fileName====>{}", fileName);
		if ("".equals(fileName)) {
			userInfoVO.setUiFilepath("/resources/upload/nophoto.png");
		} else if (!"".equals(fileName)) {
			int idx = fileName.lastIndexOf(".");
			String extName = "";
			if (idx != -1) {
				extName = fileName.substring(idx);
			}
			String name = UUID.randomUUID().toString();
			log.info("name====>{}", name);
			File file = new File(uploadFilePath, name + extName);
			userInfoVO.getUiFile().transferTo(file);
			userInfoVO.setUiFilepath("/resources/upload/" + name + extName);
			log.info("저장됨====>{}", userInfoVO);
		}
		log.info("서비스/업데이트==>{}", userInfoVO);
		return uiMapper.updateUserInfo(userInfoVO) == 1;
	}

	public boolean updateBiNum(UserInfoVO userInfoVO) {
		// 인서트 후 넘겨받은 BabsangInfoVO의 biNum을 userInfoVO에 업데이트
		return uiMapper.updateUiBiNum(userInfoVO) == 1;
	}

	// 인증코드로 token요청하기
	public KakaoToken requestToken(String addURI, String code) {
		String strUrl = "https://kauth.kakao.com/oauth/token"; // request를 보낼 주소
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
			sb.append("&client_id=b288a9632f49edf850cff8d6eb985755");
			sb.append(redirectURI); // 이부분이 바뀌어야하는건가? join과 login시에 url이 다르니까?
			sb.append("&code=" + code); // 인자로 받아온 인증코드
			bw.write(sb.toString());
			bw.flush();// 실제 요청을 보내는 부분

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("responsecode(200이면성공): {}", responseCode);

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

	public UserInfoVO requestUser(String accessToken) { // 카카오 유저 정보
		log.info("requestUser 시작");
		String strUrl = "https://kapi.kakao.com/v2/user/me"; // request를 보낼 주소
		UserInfoVO userInfoVO = new UserInfoVO(); // response를 받을 객체
		KakaoUserInfoVO kakaoUserInfoVO = new KakaoUserInfoVO(); // response를 받을 카카오 유저 객체
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
			userInfoVO.setKakaoImgPath(profile_image);
			String nickname = (String) properties.get("nickname");

			// 결과json 안에 kakao_account key는 json Object를 value로 가짐
			HashMap<String, Object> kakao_account = (HashMap<String, Object>) resultMap.get("kakao_account");

			String age_range = (String) kakao_account.get("age_range");
			String gender = (String) kakao_account.get("gender");

			// 카카오 유저인포에 인서트하는 내용이 들어가야함!
			/*
			 * kakaoUserInfoVO.setKuiId(id); uiMapper.insertKakaoUserInfo(kakaoUserInfoVO);
			 */

			// 유저정보 세팅
			userInfoVO.setKuiId(id);
			userInfoVO.setUiNickname(nickname);// 닉네임

			int uiAge = 0;
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
			log.info("properties= {}", properties);
			log.info("userInfoVO={}", userInfoVO);

		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("서비스에서 받은 카카오 유저={}", userInfoVO);
		return userInfoVO;
	}

	public NaverToken requestNaverToken(String addURI,String code, String state) throws UnsupportedEncodingException {
		NaverToken naverToken = new NaverToken();
		String clientId = "BSeMnF9B1CusMX9DeEg8";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "fpEWA5y2fc";// 애플리케이션 클라이언트 시크릿값";
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

	public UserInfoVO requestNaverUser(String access_token) {
		log.info("네이버 requestUser 시작");
		String strUrl = "https://openapi.naver.com/v1/nid/me"; // request를 보낼 주소
		UserInfoVO userInfoVO = new UserInfoVO(); // response를 받을 객체
		NaverUserInfoVO naverUserInfoVO = new NaverUserInfoVO(); // response를 받을 카카오 유저 객체
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
			//--------------------------------------------------
			
		
	
			// 결과json 안에 response key는 json Object를 value로 가짐
			HashMap<String, Object> response = (HashMap<String, Object>) resultMap.get("response");
			String id = (String)response.get("id");
			String nickname = (String) response.get("nickname"); // 유니코드 형태
			String profile_image = (String) response.get("profile_image"); //http 주소
			userInfoVO.setNaverImgPath(profile_image);
			String age = (String) response.get("age"); // 나이 범위
			String gender = (String) response.get("gender"); // M or F
			
			// 유저정보 세팅
			userInfoVO.setNuiId(id); // 고유 ID 
			
			StringBuffer resultNickname = new StringBuffer(); // 유니코드인 닉네임 변환 과정
		    
		    for(int i=0; i<nickname.length(); i++){
		        if(nickname.charAt(i) == '\\' &&  nickname.charAt(i+1) == 'u'){    
		            Character c = (char)Integer.parseInt(nickname.substring(i+2, i+6), 16);
		            resultNickname.append(c);
		            i+=5;
		        }else{
		        	resultNickname.append(nickname.charAt(i));
		        }
		    }
		    
			userInfoVO.setUiNickname(resultNickname.toString());// 닉네임

			int uiAge = 0;
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
			//--------------------------------------------------
			
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

}
