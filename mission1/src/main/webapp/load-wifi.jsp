<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="api.WifiService"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
h1 {
	text-align: center;
}
div {
	text-align: center;
}
</style>
</head>
<body>
	<%
	request.setCharacterEncoding("UTF-8");

	WifiService wifiService = WifiService.getWifiService();
	int total_cnt = wifiService.insertWifiAPI();
	%>
	<h1><%=total_cnt%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
	<div>
		<a href="home.jsp">홈 으로 가기</a>
	</div>
	
</body>
</html>

<!-- 

<!DOCTYPE html>
<html lang="kr">
<head>
	<meta charset="UTF-8">
	<title>서울시 열린데이터 광장 OpenAPI 샘플(Javascript)</title>
</head>
<body>
<script>
	var xhr = new XMLHttpRequest();
	var url = 'http://openapi.seoul.go.kr:8088/6a7949777068323337396f654d6143/json/TbPublicWifiInfo/1/10/'; /* URL */
	xhr.open('GET', url);
	xhr.onreadystatechange = function () {
		if (this.readyState == xhr.DONE) {  // <== 정상적으로 준비되었을때
		if(xhr.status == 200||xhr.status == 201){ // <== 호출 상태가 정상적일때
			alert('Status: '+this.status+
				'\nHeaders: '+JSON.stringify(this.getAllResponseHeaders())+
				'\nBody: '+this.responseText);
			}
		}
	};
	xhr.send('');
</script>
</body>
</html> -->