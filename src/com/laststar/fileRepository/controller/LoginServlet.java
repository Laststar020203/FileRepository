package com.laststar.fileRepository.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
				failesHtmlMessage(writer, "로그인 실패");
			} else {

				sendMessage(writer, "<script>alert('로그인 성공')</script>");

				request.getSession().setAttribute("user", user);

				for (Cookie c : request.getCookies()) {
					if (c.getName().equals("reffer")) {
						response.sendRedirect(c.getValue());
						c.setMaxAge(0);
						response.addCookie(c);
						return;
					}
				}

				response.sendRedirect("/");

			}

		} catch (SQLException e) {
			e.printStackTrace();
			failesHtmlMessage(writer, "서버 에러");
		}

	}

	@Override
	public void sendMessage(PrintWriter writer, String msg) {
		writer.append(msg);
	}

	private void failesHtmlMessage(PrintWriter writer, String msg) {
		sendMessage(writer, "<script> alert('" + msg + "'); location.href = document.referrer; </script>");
	}

}
