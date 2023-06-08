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
	private String uiNickname; // 댓글작성시 유저 닉네임 추가
	private String uiFilepath; // 댓글 작성시 유저 프로필 추가
}
