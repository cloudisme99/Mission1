<%@page import="java.util.ArrayList"%>
<%@page import="api.History"%>
<%@page import="java.util.List"%>
<%@page import="api.WifiService"%>
<%@page import="api.HistoryService"%>

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

<body>
	<%
	request.setCharacterEncoding("UTF-8");

	String HISTORY_ID = request.getParameter("HISTORY_ID");
	
	HistoryService historyService = HistoryService.getHistoryService();
	List<History> hisList = new ArrayList<History>();

	hisList = historyService.selectHistoryList();
	
	if (null != HISTORY_ID) {

		historyService.deleteHistory(HISTORY_ID);
		
	} 
	
	
	%>
	<h1>위치 히스토리 목록</h1>
	<p style=""></p>
	<a href="home.jsp">홈</a>
	<a>|</a>
	<a href="history.jsp">위치 히스토리 목록</a>
	<a>|</a>
	<a href="load-wifi.jsp">Open API와이파이 정보 가져오기</a>
	<p style=""></p>
	
		


	<table id="customers">
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<!--여기서부턴 for문 돌면서 db내용 출력하면 될 -->
		<tbody>
			<%
			for (int i = 0; i < hisList.size(); i++) {
			%>
			<tr>
				<td><%=hisList.get(i).getHISTORY_ID()%></td>
				<td><%=hisList.get(i).getLAT()%></td>
				<td><%=hisList.get(i).getLNT()%></td>
				<td><%=hisList.get(i).getTime()%></td>
				<td>
					<div style="margin:0 auto; text-align: center;">
						<form action="history.jsp" method="get">
								<input type="hidden" name="HISTORY_ID" value=<%=hisList.get(i).getHISTORY_ID()%>>
								<button type="submit" onclick="alert('삭제가 완료되었습니다. 새로고침을 해주십시오.');">삭제</button>
						</form>
					</div>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

</body>
</html>


