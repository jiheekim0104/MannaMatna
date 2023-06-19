package com.ezen.mannamatna.vo;

import lombok.Data;

@Data
public class NaverToken {
	String access_token; //접근 토큰, 발급 후 expires_in 파라미터에 설정된 시간(초)이 지나면 만료됨
	String refresh_token; //갱신 토큰, 접근 토큰이 만료될 경우 접근 토큰을 다시 발급받을 때 사용
	String token_type; //접근 토큰의 타입으로 Bearer와 MAC의 두 가지를 지원
	String expires_in; //접근 토큰의 유효 기간(초 단위)
	String error; //에러 코드
	String error_description; //에러 메시지
}
