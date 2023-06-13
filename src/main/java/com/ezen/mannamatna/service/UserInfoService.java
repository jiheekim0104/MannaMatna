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
import java.net.HttpURLConnection;
import java.net.URL;
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
import com.ezen.mannamatna.vo.UserInfoVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserInfoService {
	private final String uploadFilePath = "C:\\works\\workspace\\mannamatna\\src\\main\\webapp\\resources\\upload";
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
		userInfoVO = uiMapper.selectUserInfo(userInfoVO);
		if (userInfoVO != null) {
			session.setAttribute("user", userInfoVO);
			return true;
		}
		return false;
	}
	
	public boolean kakaoLogin(HttpSession session, KakaoUserInfoVO kakaoUserInfoVO) {
		kakaoUserInfoVO = uiMapper.selectKakaoUserInfo(kakaoUserInfoVO);
		if (kakaoUserInfoVO != null) {
			UserInfoVO userInfoVO = null;
			userInfoVO.setUiNum(kakaoUserInfoVO.getUiNum()); // 카카오 로그인 유저의 유저번호를 userInfoVO에 담기
			log.info("userInfoVO=>{}",userInfoVO);
			session.setAttribute("user", userInfoVO);
			return true;
		}
		return false;
	}
	
	public boolean join(UserInfoVO userInfoVO) throws IllegalStateException, IOException {
		String fileName = null;
		if(userInfoVO.getUiFile()==null) {
			fileName = userInfoVO.getUiFilepath().replace("C:\\works\\workspace\\mannamatna\\src\\main\\webapp","");
			userInfoVO.setUiFilepath(fileName);
			userInfoVO.setUiPwd("0000");
			return uiMapper.insertUserInfo(userInfoVO) == 1;
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
			String redirectURI = "&redirect_uri=http://localhost"+addURI;
			sb.append("grant_type=authorization_code"); // grant_type를 authorization_code로 고정등록해야함
			sb.append("&client_id=b288a9632f49edf850cff8d6eb985755");
			sb.append(redirectURI); //이부분이 바뀌어야하는건가? join과 login시에 url이 다르니까?
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
			String nickname = (String) properties.get("nickname");

			// 결과json 안에 kakao_account key는 json Object를 value로 가짐
			HashMap<String, Object> kakao_account = (HashMap<String, Object>) resultMap.get("kakao_account");

			String age_range = (String) kakao_account.get("age_range");
			String gender = (String) kakao_account.get("gender");

			// 유저정보 세팅
			userInfoVO.setUiId(Long.toString(id));
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

			URL imageUrl = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			String name = null;
			try {
				imageUrl = new URL(profile_image);
				inputStream = imageUrl.openStream();
				name = UUID.randomUUID().toString();
				outputStream = new FileOutputStream(uploadFilePath+"\\"+name+".jpg");

				while (true) {
					int data = inputStream.read();
					if (data == -1) {
						break;
					}
					outputStream.write(data); // 이미지 데이터값을 컴퓨터 또는 서버공간에 저장
				}

				inputStream.close();
				outputStream.close();

			} catch (Exception e) {
				// 예외처리
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			}

			userInfoVO.setUiFilepath(uploadFilePath+"\\"+name+".jpg");// 사진

			log.info("resultMap= {}", resultMap);
			log.info("properties= {}", properties);
			log.info("userInfoVO={}", userInfoVO);

		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("서비스에서 받은 카카오 유저={}",userInfoVO); // 유저 어디갔노..^0^;; 정보가 안오네;;ㅎ
		return userInfoVO;
	}
	
	public List<UserInfoVO> getUserInfos(UserInfoVO userInfoVO, HttpSession session){
		// 회원데이터를 모두 담은 객체 get
		// session데이터로 추후 관리자가 아닐 경우 검사
		return uiMapper.selectUserInfos(userInfoVO);
	}
}
