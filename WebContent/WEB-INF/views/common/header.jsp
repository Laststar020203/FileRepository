<%@ include file="./user_header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<div id="search-bar">
		<input type="text" id="input_search">
		<button id="search_button" onclick="search()">검색</button>
	</div>
</div>
<script>

	function search(){
		var input_search = document.getElementById('input_search');
		var newLocation = "list.jsp"
		
		if(input_search.value !== "")
			newLocation = "list.jsp?prefix="+input_search.value;
		
		location.href = newLocation;
	}

</script>