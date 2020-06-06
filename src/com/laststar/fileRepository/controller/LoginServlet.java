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
import com.laststar.fileRepository.entity.USERS;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet implements ResultReturnable {
	private static final long serialVersionUID = 1L;

	private UserDao dao;

	public LoginServlet() {
		dao = new UserDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		response.setContentType(CONTENT_TYPE_HTML_UTF8);

		PrintWriter writer = response.getWriter();

		try {

			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");

			USERS user = dao.login(id);

			if (user == null || !user.getPWD().equals(pwd)) {
				failes(writer);
			} else {

				request.getSession().setAttribute("user", user);
				succees(writer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			failes(writer);
		}

	}

	@Override
	public void succees(PrintWriter writer) {
		writer.append("<script>");
		writer.append("alert('로그인 성공')");
		writer.append("document.href = referrer");
		writer.append("</script>");

	}

	@Override
	public void failes(PrintWriter writer) {
		writer.append("<script>");
		writer.append("alert('로그인에 실패하였습니다.'");
		writer.append("document.href = referrer");
		writer.append("</script>");

	}

}
