<%@page import="com.laststar.fileRepository.entity.USERS"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	USERS user = (USERS) session.getAttribute("user");
%>
<header>
	<%
		if(user == null){
	%>
	<a href="/login.jsp">
		<button>로그인</button>
	</a>
	<%
		}else{
	%>
	<span><%=user.getNAME() %>님 안녕하세요</span>
	<%
		}
	%>
</header>