package com.laststar.fileRepository.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laststar.fileRepository.OracleDB;
import com.laststar.fileRepository.dao.UserDao;

/**
 * Servlet implementation class CheckNicknameServlet
 */
@WebServlet("/checkNickname")
public class CheckNicknameServlet extends HttpServlet implements ResultReturnable{
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	private UserDao dao;
	
	public CheckNicknameServlet() {
		dao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
        response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
                
        PrintWriter writer = response.getWriter();
        
		try {
			
	        if(dao.getNicknameCount(request.getParameter("nickname")) == 0) {
	        	sendMessage(writer, "사용 가능한 닉네임 입니다!");
	        }else {
	        	sendMessage(writer, "이미 존재하는 닉네임 입니다!");
	        }
	        
	        
		} catch (SQLException e) {
        	sendMessage(writer, "서버 에러");
		}        
		
	}
	
	private String checkNicknameResponseToJSON(boolean isSuccees, String msg) {
		return "{"
				+ "\"succees\":" + isSuccees + ","
				+ "\"msg\":" + "\"" +  msg + "\"" + "}";
	}

	@Override
	public void sendMessage(PrintWriter writer, String msg) {
		writer.append(checkNicknameResponseToJSON(true, msg));
		
	}
	
	

}
