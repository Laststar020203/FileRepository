<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id="login_form" action="login" method="post">
		<table>
			<tr>
				<td><span>아이디</span></td>
				<td><input type="text" id="input_id" name="id" />
				</td>
			</tr>
			<tr>
				<td><span>비밀번호</span></td>
				<td><input type="text" id="input_pwd" name="pwd" /></td>
			</tr>
			<tr>
				<td colspan="2">
				<span>아직 계정이 없으신가요? <a href="regsiter.jsp">회원가입</a></span>
				<td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="login_button" value="login" onclick="login_submit();"/>
				</td>
			</tr>
		</table>
	</form>
	
	<script>
		function vaild(){
			var input_id = document.getElementById('input_id');
			var input_pwd = document.getElementById('input_pwd');
			
			if(input_id.value == ""){
				alert('아이디를 입력해주세요')
				input_id.focus();
				return false;
			}else if(!'^[A-Za-z0-9]{4,10}$'.match(input_id.value)){
				alert('영문과 숫자만 입력이 가능합니다.')
				input_id.focus();
				return false;
			}else if(input_pwd.value == ""){
				alert('비밀번호를 입력해주세요')
				input_pwd.focus();
				return false;
			}else if(!'^[A-Za-z0-9]{4,10}$'.match(input_pwd.value)){
				alert('영문과 숫자만 입력이 가능합니다.')
				input_pwd.focus();
				return false;
			}
			
			return true;
			
		}
		
		function login_submit(){
			var login_form = document.getElementById('login_form');
			if(valid()){
				login_form.submit();
			}
		}
	</script>
</body>
</html>