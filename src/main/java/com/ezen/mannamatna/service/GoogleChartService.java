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

	public JSONObject getGenderChart(UserInfoVO userInfoVO, HttpSession session) {
		// 성별차트 서비스
		List<UserInfoVO> items = userInfoService.getUserInfos(userInfoVO, session);
		log.info("구글차트서비스에서 의존주입받은 유저서비스 메소드실행 후 유저리스트==>{}", items); // 유저서비스 메소드 정상실행 확인

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
		JSONObject jsonData = new JSONObject(); // 리턴할 JSONObject 생성
		JSONArray columns = new JSONArray(); // 컬럼 생성

		// Create the "Sex" column
		JSONObject cols1 = new JSONObject();
		cols1.put("label", "유저성별");
		cols1.put("type", "string");
		columns.add(cols1);

		// Create the "Membership" column
		JSONObject cols2 = new JSONObject();
		cols2.put("label", "성별회원수");
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
		dataRow1Value1.put("v", "남자");
		dataRow1Values.add(dataRow1Value1);
		JSONObject dataRow1Value2 = new JSONObject();
		dataRow1Value2.put("v", countMale);
		dataRow1Values.add(dataRow1Value2);
		dataRow1.put("c", dataRow1Values);
		dataRows.add(dataRow1);

		JSONObject dataRow2 = new JSONObject();
		JSONArray dataRow2Values = new JSONArray();
		JSONObject dataRow2Value1 = new JSONObject();
		dataRow2Value1.put("v", "여자");
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

	public JSONObject getAgeChart(UserInfoVO userInfoVO, HttpSession session) {
		// 연령대 차트 서비스
		List<UserInfoVO> items = userInfoService.getUserInfos(userInfoVO, session);
		log.info("구글차트서비스에서 의존주입받은 유저서비스 메소드실행 후 유저리스트==>{}", items); // 유저서비스 메소드 정상실행 확인
		int teenager = 0;
		int twenties = 0;
		int thirties = 0;
		int forties = 0;
		int fifties = 0;
		for (UserInfoVO user : items) {
			if (user.getUiAge() == 10) {
				teenager++;
				continue;
			}
			if (user.getUiAge() == 20) {
				twenties++;
				continue;
			}
			if (user.getUiAge() == 30) {
				thirties++;
				continue;
			}
			if (user.getUiAge() == 40) {
				forties++;
				continue;
			}
			if (user.getUiAge() == 50) {
				fifties++;
				continue;
			}
		}
		JSONObject jsonData = new JSONObject(); // 해당 서비스에서 jsonObject를 컨트롤러에 리턴한다.
		JSONArray columns = new JSONArray(); // 컬럼 생성

		JSONObject cols1 = new JSONObject(); // 첫번째 컬럼
		cols1.put("label", "유저연령대");
		cols1.put("type", "string");
		columns.add(cols1);

		// Create the "Membership" column
		JSONObject cols2 = new JSONObject();
		cols2.put("label", "연령대별회원");
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

		dataRow1Value1.put("v", "10대");
		dataRow1Values.add(dataRow1Value1);
		JSONObject dataRow1Value2 = new JSONObject();
		dataRow1Value2.put("v", teenager);
		dataRow1Values.add(dataRow1Value2);
		dataRow1.put("c", dataRow1Values);
		dataRows.add(dataRow1);

		JSONObject dataRow2 = new JSONObject();
		JSONArray dataRow2Values = new JSONArray();
		JSONObject dataRow2Value1 = new JSONObject();

		dataRow2Value1.put("v", "20대");
		dataRow2Values.add(dataRow2Value1);
		JSONObject dataRow2Value2 = new JSONObject();
		dataRow2Value2.put("v", twenties);
		dataRow2Values.add(dataRow2Value2);
		dataRow2.put("c", dataRow2Values);
		dataRows.add(dataRow2);

		JSONObject dataRow3 = new JSONObject();
		JSONArray dataRow3Values = new JSONArray();
		JSONObject dataRow3Value1 = new JSONObject();

		dataRow3Value1.put("v", "30대");
		dataRow3Values.add(dataRow3Value1);
		JSONObject dataRow3Value2 = new JSONObject();
		dataRow3Value2.put("v", thirties);
		dataRow3Values.add(dataRow3Value2);
		dataRow3.put("c", dataRow3Values);
		dataRows.add(dataRow3);

		JSONObject dataRow4 = new JSONObject();
		JSONArray dataRow4Values = new JSONArray();
		JSONObject dataRow4Value1 = new JSONObject();

		dataRow4Value1.put("v", "40대");
		dataRow4Values.add(dataRow4Value1);
		JSONObject dataRow4Value2 = new JSONObject();
		dataRow4Value2.put("v", forties);
		dataRow4Values.add(dataRow4Value2);
		dataRow4.put("c", dataRow4Values);
		dataRows.add(dataRow4);

		JSONObject dataRow5 = new JSONObject();
		JSONArray dataRow5Values = new JSONArray();
		JSONObject dataRow5Value1 = new JSONObject();

		dataRow5Value1.put("v", "50대이상");
		dataRow5Values.add(dataRow5Value1);
		JSONObject dataRow5Value2 = new JSONObject();
		dataRow5Value2.put("v", fifties);
		dataRow5Values.add(dataRow5Value2);
		dataRow5.put("c", dataRow5Values);
		dataRows.add(dataRow5);
		// Add the data rows to the main JSONObject
		jsonData.put("rows", dataRows);

		return jsonData;
	}

	public JSONObject getCredatChart(HttpSession session) {
		// 날자별 가입인원수 동향 선차트
		List<UserInfoVO> items = userInfoService.getUserInfosByCredat(session);
		JSONObject jsonData = new JSONObject(); // 해당 서비스에서 jsonObject를 컨트롤러에 리턴한다.
		JSONArray columns = new JSONArray(); // 컬럼 생성

		JSONObject cols1 = new JSONObject(); // 첫번째 컬럼
		cols1.put("label", "가입날짜");
		cols1.put("type", "string");
		columns.add(cols1);
	
		// Create the "Membership" column
		JSONObject cols2 = new JSONObject();
		cols2.put("label", "가입날짜별회원수");
		cols2.put("type", "number");
		columns.add(cols2);
		
		jsonData.put("cols", columns);
		
		JSONArray dataRows = new JSONArray();
		for (UserInfoVO user : items) {
			// 날짜별 회원수 반복하며 열에 추가
			// Add the data rows as JSONObjects
			JSONObject dataRow = new JSONObject();
			JSONArray dataRowValues = new JSONArray();
			JSONObject dataRowValue1 = new JSONObject();

			dataRowValue1.put("v", user.getUiCredat());
			dataRowValues.add(dataRowValue1);
			JSONObject dataRowValue2 = new JSONObject();
			dataRowValue2.put("v", user.getUiUserCnt());
			dataRowValues.add(dataRowValue2);
			dataRow.put("c", dataRowValues);
			dataRows.add(dataRow);
		}
		jsonData.put("rows", dataRows);
		return jsonData;
	}
}