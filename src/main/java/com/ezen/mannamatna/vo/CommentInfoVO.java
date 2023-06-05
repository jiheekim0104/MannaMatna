package com.ezen.mannamatna.vo;

import lombok.Data;

@Data
public class CommentInfoVO {

	private int ciNum;
	private String ciContent;
	private String ciCredat;
	private String ciCretim;
	private int uiNum;
	private int biNum;
	private UserInfoVO userInfoVO;
	private String uiNickname; // 댓글작성시 유저 닉네임 컬럼추가
	private String uiFilepath; // 댓글 작성시 유저 프로필 컬럼추가
}
