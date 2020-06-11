package com.laststar.fileRepository.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laststar.fileRepository.FileProxy;
import com.laststar.fileRepository.OracleDB;
import com.laststar.fileRepository.dao.FileDao;
import com.laststar.fileRepository.entity.FILES;
import com.laststar.fileRepository.entity.USERS;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/fileUpload")
public class FileUploadServlet extends HttpServlet implements ResultReturnable {
	private static final long serialVersionUID = 1L;

	private FileDao dao;
	private FileProxy proxy;

	public FileUploadServlet() {
		super();
		dao = new FileDao();
		proxy = new FileProxy();
	}

	// getRealPath("/"); 는 webapp 폴더까지를 의미한다.

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String PATH = request.getSession().getServletContext().getRealPath("/");				
		final int SIZE = 100 * 1024 * 1024;

		request.setCharacterEncoding("UTF-8");
		response.setContentType(CONTENT_TYPE_HTML_UTF8);
		
		PrintWriter writer = response.getWriter();

		
		try {
			
			OracleDB.setAutoCommit(false);
				
			MultipartRequest multi = new MultipartRequest(request, PATH, SIZE, "UTF-8", new DefaultFileRenamePolicy());
			
			
			String name = multi.getParameter("fileName");
			String version = multi.getParameter("fileVersion");
			String content = multi.getParameter("content");
			
			if(dao.getFileCount(name, version) != 0) {
				failesHtmlMessage(writer, "동일한 파일이 존재합니다!");
				System.out.println("iam here!");
				return;
			}
			
			File file =  multi.getFile((String) multi.getFileNames().nextElement());			
			String repository_path = proxy.upload(name, version, file);
			
			
			dao.insertFile(new FILES(name, version, (USERS) request.getSession().getAttribute("user"), content, LocalDate.now().toString(), 
					 repository_path,0));
			
			sendMessage(writer, "<script>alert('파일 업로드 성공');</script>");
			
			response.sendRedirect("/show.jsp?name="+name+"&version="+version);
		
			
			OracleDB.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			failesHtmlMessage(writer, "서버 오류");
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
		writer.append(msg);
	}

	private void failesHtmlMessage(PrintWriter writer, String msg) {
		sendMessage(writer, "<script> alert('" + msg + "'); location.href = document.referrer; </script>");
	}

}
