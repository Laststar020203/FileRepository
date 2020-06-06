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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType(CONTENT_TYPE_HTML_UTF8);
		
		PrintWriter writer = response.getWriter();
		
		try {
			
			dao.register(request.getParameter("id"),request.getParameter("pwd"),request.getParameter("nickname"),request.getParameter("email"));
				
			succees(writer);
					
		} catch (Exception e) {
			e.printStackTrace();
			failes(writer);
		}
		
		
	}

	@Override
	public void succees(PrintWriter writer) {
		writer.append("<script>");
		writer.append("alert('회원가입 성공')");
		writer.append("document.href = /login.jsp");
		writer.append("</script>");
	}


	@Override
	public void failes(PrintWriter writer) {
		writer.append("<script>");
		writer.append("alert('회원가입 실패')");
		writer.append("document.href = referrer");
		writer.append("</script>");		
	}
	
	
	

}
