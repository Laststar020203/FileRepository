package com.laststar.fileRepository.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laststar.fileRepository.OracleDB;
import com.laststar.fileRepository.dao.FileDao;
import com.laststar.fileRepository.entity.FILES;
import com.laststar.fileRepository.entity.USERS;

import sun.reflect.generics.tree.ReturnType;

/**
 * Servlet implementation class FileRemoveServlet
 */
@WebServlet("/fileRemove")
public class FileRemoveServlet extends HttpServlet implements ResultReturnable {
	private static final long serialVersionUID = 1L;
	private FileDao dao;
	
	public FileRemoveServlet() {
		dao = new FileDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = null;
		
		try {
			
			OracleDB.setAutoCommit(false);
			
			writer = response.getWriter();
			
			String name = request.getParameter("name");
			String version = request.getParameter("version");
			
			FILES file = dao.getFile(name, version);
			
			if(file == null)
				sendMessage(writer, "존재하지 않는 파일입니다.");
			
			if(request.getSession().getAttribute("user") == null || !file.getAUTHOR().getID().equals(((USERS)request.getSession().getAttribute("user")).getID()))
				sendMessage(writer, "당신은 삭제할 권한이 없습니다.");
			
			dao.deleteFile(name, version);
			
			sendMessage(writer, "삭제 완료");
			
			OracleDB.commit();
			
		}catch(Exception e) {
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
		writer.append("location.href = document.referrer");
		writer.append("</script>");			
	}
	
	
	
	

}
