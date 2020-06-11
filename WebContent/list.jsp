<%@page import="com.laststar.fileRepository.Paging"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	Paging paging = (Paging) pageContext.getAttribute("paging");
	
	if(paging == null){
%>
	<script>
		alert('페이징이 생성됩니다.');
	</script>
<%
	
	}

%>

<body>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<div>
	<table>
		<thead>
			<tr>
				<td>
					name
				</td>
				<td>
					name
				</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
</body>
</html>