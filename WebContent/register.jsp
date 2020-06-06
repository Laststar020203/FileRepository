<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="WEB-INF/src/js/AsyncRequest.js"></script>
<body>
	<form action="register" method="post" id="register_form">
		<table>
			<tr>
				<td><span>아이디</span></td>
				<td><input type="text" id="input_id" name="id" /></td>
				<td><input type="button" id="check_id_button" value="중복 체크"
					onclick="check_id();" /></td>
			</tr>
			<tr>
				<td><span>비밀번호</span></td>
				<td><input type="text" id="input_pwd" name="pwd" /></td>
			</tr>
			<tr>
				<td><span>닉네임</span></td>
				<td><input type="text" id="input_nickname" name="nickname" /></td>
				<td><input type="button" id="check_nickname_button"
					value="중복 체크" onclick="check_nickname();" /></td>
			<tr>
				<td><span>이메일</span></td>
				<td><input type="text" id="input_email" name="email" /></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="button"
					id="register_button" value="회원가입" onclick="register_submit();"></td>
			</tr>
		</table>
	</form>
	<script>
		
		var isIdCheck = false;
		var isNicknameCheck = false;
		
		
		function check_id(){
			
			var input_id = document.getElementById('input_id');
			
			if(input_id.value == ""){
				alert('아이디를 입력해주세요')
				input_id.focus();
				return;
			}else if(!'^[A-Za-z0-9]{4,10}$'.match(input_id.value)){
				alert('영문과 숫자만 입력이 가능합니다.')
				input_id.focus();
				return;
			}
			
			
			AsyncRequest.ready('GET', 'checkId?id='+input_id.value, 
				(data) => {
					if(data.succees)
						isIdCheck = true;
					alert(data.msg);
				}		
			)
			
			AsyncRequest.submit();
			
		}
		function check_nickname(){
			
			var input_nickname = document.getElementById('input_nickname');

			
			AsyncRequest.ready('GET', 'checknickname?nickname='+input_nickname.value, 
				(data) => {
					if(data.succees)
						isNicknameCheck = true;
					alert(data.msg);
				}		
			)
			
			AsyncRequest.submit();
		}
		
		function valid(){
			
			var input_pwd = document.getElementById('input_pwd');
			var input_email = document.getElementById('input_email');
			
			
			
			if(!isIdCheck){
				alert('아이디 중복체크가 필요합니다.');
				return false;
			}else if(!isNicknameCheck){
				alert('닉네임 중복체크가 필요합니다.');
				return false;
			}else if(input_pwd.value == ""){
				alert('비밀번호를 입력해주세요');
				input_pwd.focus();
				return false;
			}else if(!'/^[A-Za-z0-9]{4,10}$/g'.match(input_pwd.value)){
				alert('영문과 숫자만 입력이 가능합니다.');
				input_pwd.focus();
				return false;
			}else if(input_emil.value == ""){
				alert('이메일을 입력해주세요');
				return false;
			}else if(!'//g'.match(input_email.value)){
				return false;

			}
			
			return true;
			
		}
		function register_submit(){
			var register_form = document.getElementById('register_form');
			if(valid()){
				register_form.submit();
			}
		}
			
	</script>
</body>
</html>