<%@page import="api.HistoryService"%>
<%@page import="api.WifiService"%>
<%@page import="api.Wifi"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style>
#customers {
	font-family: Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

#customers td, #customers th {
	border: 1px solid #ddd;
	padding: 8px;
}

#customers tr:nth-child(even) {
	background-color: #f2f2f2;
}

#customers tr:hover {
	background-color: #ddd;
}

#customers th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	background-color: #04AA6D;
	color: white;
}
</style>
</head>
<script type="text/javascript">
	
</script>
<body>
	<%
	request.setCharacterEncoding("UTF-8");
	
	String LAT = request.getParameter("LAT");
	String LNT = request.getParameter("LNT");
	
	HistoryService historyService = HistoryService.getHistoryService();
	WifiService wifiService = WifiService.getWifiService();
	
	

	List<Wifi> wifiList = new ArrayList<Wifi>();

 	if (null != LAT && null != LNT) {

		historyService.insertHistory(LAT, LNT);
		wifiList = wifiService.selectWifiList(LAT, LNT);
		
	
 	}
 	
 	if (null == LAT && null == LNT) {
 		LAT = "0.0";
 		LNT = "0.0";
 	}
	%>
	<h1>와이파이 정보 구하기</h1>
	<p style=""></p>
	<a href="home.jsp">홈</a>
	<a>|</a>
	<a href="history.jsp">위치 히스토리 목록</a>
	<a>|</a>
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>

	<p style=""></p>

	<form action="home.jsp" method="get">
		LAT: <input name="LAT" value="<%=LAT%>" style="padding-left: 10px;" /> 
		LNT: <input name="LNT" value="<%=LNT%>" style="padding-left: 10px;" />
		<button>근처 WIFI 정보 보기</button>

	</form>
	
	<p style=""></p>

	<table id="customers">
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
	
			</tr>
		</thead>

		<tbody>
		<%
		if (LAT == "0.0" && LNT == "0.0") {
			%>
		<tr>
			<td style="padding:25px 25px; text-align: center;" colspan='17' >
				<h4>위치 정보를 입력한 후에 조회해 주세요</h4>
			</td>
		</tr>
			<%
		}
		%>
		
		<%
		for (int i = 0; i < wifiList.size(); i++) {
		//wifiList
		%>
		<tr>
			<td><%=wifiList.get(i).getDISTANCE()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_MGR_NO()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_WRDOFC()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_MAIN_NM()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_ADRES1()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_ADRES2()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_INSTL_FLOOR()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_INSTL_TY()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_INSTL_MBY()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_SVC_SE()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_CMCWR()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_CNSTC_YEAR()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_INOUT_DOOR()%></td>
			<td><%=wifiList.get(i).getX_SWIFI_REMARS3()%></td>
			<td><%=wifiList.get(i).getLAT()%></td>
			<td><%=wifiList.get(i).getLNT()%></td>
			<td><%=wifiList.get(i).getWORK_DTTM()%></td>
		</tr>
			
		<%
			}
		%>
		  
		
		</tbody>
	</table>

</body>
</html>


