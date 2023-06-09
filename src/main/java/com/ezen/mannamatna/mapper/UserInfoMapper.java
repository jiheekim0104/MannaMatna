package com.ezen.mannamatna.mapper;

import java.util.List;

import com.ezen.mannamatna.vo.KakaoUserInfoVO;
import com.ezen.mannamatna.vo.NaverUserInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

public interface UserInfoMapper {
	UserInfoVO selectUserInfo(UserInfoVO userInfoVO);
	
	UserInfoVO selectUserInfoById(UserInfoVO userInfoVO);
	
	UserInfoVO selectUserInfoForLogin(UserInfoVO userInfoVO);
	
	UserInfoVO selectUserInfoByNum(UserInfoVO userInfoVO); 
	
	KakaoUserInfoVO selectKakaoUserInfo(KakaoUserInfoVO kakaoUserInfoVO); //카카오 로그인 유저를 찾을때 사용
	
	KakaoUserInfoVO selectKakaoUserInfoByNum(UserInfoVO userInfoVO);
	
	NaverUserInfoVO selectNaverUserInfo(NaverUserInfoVO naverUserInfoVO);//네이버 로그인 유저를 찾을때 사용
	
	NaverUserInfoVO selectNaverUserInfoByNum(UserInfoVO userInfoVO);
	
	int idChk(UserInfoVO userInfoVO);
	
	int nicknameChk(UserInfoVO userInfoVO);
	
	int insertUserInfo(UserInfoVO userInfoVO);
	
	int insertKakaoUserInfo(KakaoUserInfoVO kakaoUserInfoVO);
	
	int insertNaverUserInfo(NaverUserInfoVO naverUserInfoVO);
	
	int updateUserInfoActive(UserInfoVO userInfoVO);

	int deleteUserInfo(UserInfoVO userInfoVO);

	int updateUserInfo(UserInfoVO userInfoVO);
	
	int updateUiBiNum(UserInfoVO userInfoVO); // 밥상 인서트 후 UserInfoVO에 biNum 업데이트
	
	List<UserInfoVO> selectUserInfos(UserInfoVO userInfoVO); // 회원데이터를 모두 담은 List객체
	
	List<UserInfoVO> selectUserInfosByBiNum(int biNum); // 밥상 biNum으로 uiVO(참가자) 데이터리스트를 가져옴

	UserInfoVO selectUserInfoFromBabsang(int uiNum); // 밥상에 uiNum으로 uiVO(작성자) 데이터 한줄 가져옴
	
	List<UserInfoVO> selectUserInfosByCredat(); // 날짜별 회원가입인원수 조회
	
	List<UserInfoVO> selectUserInfosByUiActive1(UserInfoVO userInfoVO); // 탈퇴요청한 유저리스트
	
	List<UserInfoVO> selectUserInfosByUiActive2(UserInfoVO userInfoVO); // 비정상적인 로그인요청으로 정지당한 유저리스트
}