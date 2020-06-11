package com.laststar.fileRepository.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laststar.fileRepository.OracleDB;
import com.laststar.fileRepository.dao.UserDao;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet implements ResultReturnable{
	
	private static final long serialVersionUID = 1L;
       
	private UserDao dao;
	
	public RegisterServlet() {
		dao = new UserDao();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType(CONTENT_TYPE_HTML_UTF8);
		
		PrintWriter writer = response.getWriter();
		
		try {
			
			OracleDB.setAutoCommit(false);
			
			dao.register(request.getParameter("id"),request.getParameter("pwd"),request.getParameter("nickname"),request.getParameter("email"));
				
			sendMessage(writer, "로그인 성공");
			
			OracleDB.commit();
					
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage(writer, "서버 에러");
			try {
				OracleDB.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void sendMessage(PrintWriter writer, String msg) {
		writer.append("<script>");
		writer.append("alert('"+msg+"')");
		writer.append("</script>");				
	}
	
	
	

}
