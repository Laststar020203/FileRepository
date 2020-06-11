<%@page import="com.laststar.fileRepository.entity.USERS"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
		
	//Cookie originFileName = new Cookie("originFileName", request.getParameter("name"));
	//Cookie originFileVersion = new Cookie("originFileVersion", request.getParameter("version"));
	
	//response.addCookie(originFileName);
	//response.addCookie(originFileVersion);
	
	String name = request.getParameter("name");
	String version = request.getParameter("version");
	
	USERS user = (USERS) request.getSession(false).getAttribute("user");
	
	if(user == null){
%>
	<script>
		alert('로그인후 이용해 주세요')
	</script>
<%
		Cookie cookie = new Cookie("reffer", "/show.jsp?name="+name+"&version="+version);
		response.addCookie(cookie);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}
	
%>

	<form action="fileModify" method="post" enctype="multipart/form-data"
		id="modifyForm">
		<table>
			<tr>
				<th><span>파일 이름</span></th>
				<th><input type="text" name="modifyFileName" id="input_modifyFileName"
					value=<%=name%>></th>
			</tr>
			<tr>
				<th><span>버전</span></th>
				<th><input type="text" name="modifyFileVersion"
					id="input_modifyVersion" value=<%=version%>>
				</th>
			</tr>
			<tr>
				<th><span>파일 첨부</span></th>
				<th><input type="file" name="file" id="input_file"></th>
			</tr>
			<tr>
				<th colspan="2"><textarea name="modifyContent" id="input_modifyContent"
						rows="50" cols="50"><%=request.getParameter("content")%></textarea>
				</th>
			</tr>
			<tr>
				<th colspan="2"><input type="button" id="cancle_button"
					value="취소"> <input type="button" id="submit_button"
					value="수정" onclick="write_submit();"></th>
			</tr>
			<input type="hidden" name="originFileName" value=<%=name%>>
			<input type="hidden" name="originFileVersion" value=<%=version%>>
		
		</table>
	</form>
	<script>
	
		function valid(){
			var input_fileName = document.getElementById('input_modifyFileName');
			var input_fileVersion = document.getElementById('input_modifyVersion');
			var input_content = document.getElementById('input_modifyContent');
			
			if(input_fileName.value == ""){
				alert('파일 이름을 입력해주세요')
				input_fileName.focus();
				return false;
			}else if(input_fileVersion.value == ""){
				alert('파일 버전을 입력해주세요')
				input_fileVersion.focus();
			}else if(false && !(/^([0-9.])*$/g).test(input_fileVersion.value)){
				alert('버전은 숫자나 . 조합으로만 만들 수 있습니다.')
				input_fileVersion.focus();
				return false;
			}else if(input_content.value == ""){
				alert('내용을 작성해주세요')
				return false;
			}
				
			return true;			
		}
		
		function write_submit(){
			var writeForm = document.getElementById('modifyForm');
			if(valid()){
				writeForm.submit();
			}
		}
	
	</script>
</body>
</html>