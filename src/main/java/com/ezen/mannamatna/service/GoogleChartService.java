package com.ezen.mannamatna.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GoogleChartService {

	@Autowired
	UserInfoService userInfoService; // 유저인포서비스 의존주입

	public JSONObject getChartData(UserInfoVO userInfoVO, HttpSession session) {

		List<UserInfoVO> items = userInfoService.getUserInfos(userInfoVO, session);
		log.info("구글차트서비스에서 의존주입받은 유저서비스 메소드실행 후 유저리스트==>{}", items); // 유저서비스 메소드 정상실행 확인
		JSONObject data = new JSONObject(); // 해당 서비스에서 jsonObject를 컨트롤러에 리턴한다.

		int countMale = 0; // 남사유저수를 담을 변수
		int countFemale = 0; // 여자유저수를 담을 변수
		int totalCount = items.size(); // 리스트의 사이즈 = 총 유저수
		for (UserInfoVO uiVO : items) {

			if (uiVO.getUiGender()) {
				// 참이면 남자
				countMale++;
			} else {
				// 거짓인경우 여자
				countFemale++;
			}
		}
		// 샘플데이터 활용한 다른 방법..내일본다.....
        JSONObject jsonData = new JSONObject();

        // Create a JSONArray to hold the columns
        JSONArray columns = new JSONArray();

        // Create the "Sex" column
        JSONObject cols1 = new JSONObject();
        cols1.put("label", "User");
        cols1.put("type", "string");
        columns.add(cols1);

        // Create the "Membership" column
        JSONObject cols2 = new JSONObject();
        cols2.put("label", "Gender");
        cols2.put("type", "number");
        columns.add(cols2);

        // Add the columns to the main JSONObject
        jsonData.put("cols", columns);

        // Create a JSONArray to hold the data rows
        JSONArray dataRows = new JSONArray();

        // Add the data rows as JSONObjects
        JSONObject dataRow1 = new JSONObject();
        JSONArray dataRow1Values = new JSONArray();
        JSONObject dataRow1Value1 = new JSONObject();
        dataRow1Value1.put("v", "Male");
        dataRow1Values.add(dataRow1Value1);
        JSONObject dataRow1Value2 = new JSONObject();
        dataRow1Value2.put("v", countMale);
        dataRow1Values.add(dataRow1Value2);
        dataRow1.put("c", dataRow1Values);
        dataRows.add(dataRow1);

        JSONObject dataRow2 = new JSONObject();
        JSONArray dataRow2Values = new JSONArray();
        JSONObject dataRow2Value1 = new JSONObject();
        dataRow2Value1.put("v", "Female");
        dataRow2Values.add(dataRow2Value1);
        JSONObject dataRow2Value2 = new JSONObject();
        dataRow2Value2.put("v", countFemale);
        dataRow2Values.add(dataRow2Value2);
        dataRow2.put("c", dataRow2Values);
        dataRows.add(dataRow2);
        
        // Add the data rows to the main JSONObject
        jsonData.put("rows", dataRows);
		return jsonData; // 이 데이터가 넘어가면 json형식으로 넘어가게되서 json이 만들어지게 된다.
	}
}