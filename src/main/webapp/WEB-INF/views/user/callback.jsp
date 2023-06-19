<!-- 네이버 로그인시에 사용하는 callback -->
<%@page import="com.ezen.mannamatna.vo.NaverToken"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>네이버로그인</title>
  </head>
  <body>
  <%
  /*   String clientId = "BSeMnF9B1CusMX9DeEg8";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "fpEWA5y2fc";//애플리케이션 클라이언트 시크릿값";
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String redirectURI = URLEncoder.encode("http://localhost/naverPost", "UTF-8");
    String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
        + "&client_id=" + clientId
        + "&client_secret=" + clientSecret
        + "&redirect_uri=" + redirectURI
        + "&code=" + code
        + "&state=" + state;
    String accessToken = "";
    String refresh_token = "";
    System.out.println("들어간다~");
    try {
    	System.out.println("들어왔다~");
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      BufferedReader br;
      System.out.println("responseCode==================>"+ responseCode);
      if (responseCode == 200) { // 정상 호출
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuilder res = new StringBuilder();
      while ((inputLine = br.readLine()) != null) {
        res.append(inputLine);
      }
      ObjectMapper mapper = new ObjectMapper();
      NaverToken naverToken = new NaverToken();
      naverToken=mapper.readValue(inputLine, NaverToken.class);
      String access_Token = naverToken.getAccess_token();
	  String refresh_Token = naverToken.getRefresh_token();
	  log.info("access_token = {}", access_Token);
		log.info("refresh_token = {}", refresh_Token);
		
      br.close();
      if (responseCode == 200) {
    	  System.out.println("합니다~");
        out.println(res.toString());
      }
    } catch (Exception e) {
      // Exception 로깅
    }
    log.info("네이버토큰생성완료>>>{}", naverToken);
	return naverToken; */

  %>
  </body>
</html>