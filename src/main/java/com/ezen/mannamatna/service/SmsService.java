package com.ezen.mannamatna.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ezen.mannamatna.vo.MessageVO;
import com.ezen.mannamatna.vo.SmsRequestVO;
import com.ezen.mannamatna.vo.SmsResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {

	private String accessKey = "인증키";
	
	private String secretKey = "인증키";
	
	private String serviceId = "인증키";
 
	private String phone = ""; // 30대 남성입니다.
	
	
	private String code = ""; // 인증코드
	public String makeSignature(Long time) throws Exception {
		String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;
        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();
 
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
 
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
 
        return encodeBase64String;
	}
	
	public SmsResponseVO sendSms(MessageVO messageVO) throws Exception {
		Long time = System.currentTimeMillis();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", accessKey);
		headers.set("x-ncp-apigw-signature-v2", makeSignature(time));
		
		List<MessageVO> messages = new ArrayList<>();
		messages.add(messageVO);
		
		SmsRequestVO request = SmsRequestVO.builder()
				.type("SMS")
				.contentType("COMM")
				.countryCode("82")
				.from(phone)
				.content("[만나맛나] 인증번호 ["+createSmsKey(this.code)+"]를 입력해주세요.")
				.messages(messages)
				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String body = objectMapper.writeValueAsString(request);
		HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
		
		RestTemplate restTemplate = new RestTemplate();
	    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	    SmsResponseVO response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseVO.class);
	    // 이 서비스에서는 SmsResponseVO 객체에 인증코드난수를 저장하는 이 부분이 포인트이다.
	    response.setSmsConfirmNum(code);
	    return response;
	}
	 public String createSmsKey(String code) {
		 // 난수 생성
	        StringBuffer key = new StringBuffer();
	        Random rnd = new Random();

	        for (int i = 0; i < 6; i++) { // 인증코드 6자리
	            key.append((rnd.nextInt(10)));
	        }
	        this.code = key.toString();
	        return this.code; // 서비스의 필드로 리턴.
	    }
}
