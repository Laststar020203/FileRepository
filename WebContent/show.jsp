<%@page import="com.laststar.fileRepository.entity.FILES"%>
<%@page import="com.laststar.fileRepository.entity.USERS"%>
<%@page import="com.laststar.fileRepository.dao.FileDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String version = request.getParameter("version");
	
	FILES file = new FileDao().getFile(name, version);

	if (file == null)
		return;
%>
<body>
	<%@ include file="/WEB-INF/views/common/user_header.jsp"%>
	<table>
		<tr>
			<td>파일 이름</td>
			<td><%=file.getNAME()%></td>
		</tr>
		<tr>
			<td>파일 버전</td>
			<td><%=file.getVERSION()%></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><%=file.getAUTHOR().getNICKNAME()%></td>
		</tr>
		<tr>
			<td>생성날짜</td>
			<td><%=file.getSINCE()%></td>
		</tr>
		<tr>
			<td>다운로드 횟수</td>
			<td><%=file.getDOWNLOAD_COUNT()%></td>
		</tr>
		<tr>
			<td colspan="2"><a
				href="/download?name=<%=file.getNAME()%>&version=<%=file.getVERSION()%>">
					<button>다운로드</button>
			</a></td>
		</tr>
		<tr>
			<td colspan="2"><%=file.getCONTENT()%></td>
		</tr>
		<%
			if (user != null && user.getNICKNAME().equals(file.getAUTHOR().getNICKNAME())) {
		%>
		<a
			href="modify.jsp?name=<%=file.getNAME()%>&version=<%=file.getVERSION()%>&content=<%=file.getCONTENT()%>"><button>수정</button></a>
		<%
			}
		%>
	</table>



</body>
</html>