<%@page import="com.laststar.fileRepository.entity.FILES"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.laststar.fileRepository.dao.FileDao"%>
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
	FileDao dao = new FileDao();
	String prefix = request.getParameter("prefix") != null ? request.getParameter("prefix") : "/";
	int index = request.getParameter("index") == null ? 1 : Integer.parseInt(request.getParameter("index"));

	//	Cookie cookie = Arrays.asList(request.getCookies()).stream().filter(c -> c.getName().equals("prefix"))
	//			.findAny().get();
	Cookie prefixCookie = null;
	Cookie totalCountCookie = null;

	for (Cookie c : request.getCookies()) {
		if (c.getName().equals("prefix")) {
			prefixCookie = c;
		}

		if (c.getName().equals("totalCount")) {
			totalCountCookie = c;
		}
	}

	boolean isChange = false;
	
	if (prefixCookie == null || totalCountCookie == null || !prefixCookie.getValue().equals(prefix)) {
		prefixCookie = new Cookie("prefix", prefix);
		prefixCookie.setMaxAge(60 * 5);

		totalCountCookie = new Cookie("totalCount", String
				.valueOf(prefix.equals("/") ? dao.getTotalFileCount() : dao.getPrefixMatchFileCount(prefix)));
		totalCountCookie.setMaxAge(60 * 5);
		
		isChange = true;
		
	}
	
	response.addCookie(prefixCookie);
	response.addCookie(totalCountCookie);

	Paging paging = new Paging(Integer.parseInt(totalCountCookie.getValue()));
	
	if(!isChange && paging.isUpdateWarnning()){
		totalCountCookie = new Cookie("totalCount", String
				.valueOf(prefix.equals("/") ? dao.getTotalFileCount() : dao.getPrefixMatchFileCount(prefix)));
		totalCountCookie.setMaxAge(60 * 5);
	}
	
	paging.setTotalRowCount(Integer.parseInt(totalCountCookie.getValue()));
	
	
	paging.setPage(index);
%>

<body>
	<%@include file="/WEB-INF/views/common/header.jsp"%>
	<div>
		<table border="1px">
			<thead>
				<tr>
					<td>name</td>
					<td>version</td>
					<td>author</td>
					<td>since</td>
					<td>이동하기</td>
				</tr>
			</thead>
			<tbody>
				<%
					FILES[] files = dao.getFileList(paging);
					System.out.println(files.length);
					for (FILES file : files) {
				%>
				<tr>
					<td><%=file.getNAME()%></td>
					<td><%=file.getVERSION()%></td>
					<td><%=file.getAUTHOR().getNICKNAME()%>
					<td><%=file.getSINCE()%></td>
					<td><a
						href="show.jsp?name=<%=file.getNAME()%>&version=<%=file.getVERSION()%>"><button>이동</button></a></td>
				</tr>

				<%
					}
				%>
			</tbody>
		</table>
		<a href="/write.jsp"><button>파일 올리기</button></a>
		<div>
			<%
				if (paging.isCanPrevious()) {
			%>
			<a
				href="/list.jsp?index=<%=paging.getPrevious()%><%=prefix.equals("/") ? "" : "&prefix=" + prefix%>"><button><%=paging.getPrevious()%></button></a>
			<%
				}
				for (int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
			%>
			<a
				href="/list.jsp?index=<%=i%><%=prefix.equals("/") ? "" : "&prefix=" + prefix%>"><button><%=i%></button></a>
			<%
				}
				if (paging.isCanNext()) {
			%>
			<a
				href="/list.jsp?index=<%=paging.getNext()%><%=prefix.equals("/") ? "" : "&prefix=" + prefix%>"><button><%=paging.getNext()%></button></a>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>