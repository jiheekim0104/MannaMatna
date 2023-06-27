package com.ezen.mannamatna.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsResponseVO {
	String requestId;
	LocalDateTime requestTime;
	String statusCode;
	String statusName;
	String smsConfirmNum;
}
