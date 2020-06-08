package com.laststar.fileRepository.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laststar.fileRepository.FileProxy;
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
		
		final String PATH = request.getSession().getServletContext().getRealPath("/tempFiles");				
		final int SIZE = 100 * 1024 * 1024;
		
		PrintWriter writer = response.getWriter();
		response.setContentType(CONTENT_TYPE_HTML_UTF8);
		try {
				
			MultipartRequest multi = new MultipartRequest(request, PATH, SIZE, "UTF-8", new DefaultFileRenamePolicy());
			
			
			String name = multi.getParameter("fileName");
			String version = multi.getParameter("fileVersion");
			String content = multi.getParameter("content");
			
			if(dao.getFileCount(name, version) != 0) {
				failes(writer);
				return;
			}

			
			String repository_url = "/download/"+name+"/"+version;
			
			FILES file = new FILES(name, version, (USERS) request.getSession().getAttribute("user"), content, LocalDate.now().toString(), 
					repository_url,0);
			
			dao.insertFile(file);
						
			proxy.upload(name, version, multi.getFile((String) multi.getFileNames().nextElement()));
			
			succees(writer);
		
		}catch(Exception e) {
			e.printStackTrace();
			failes(writer);
		}
	}

	@Override
	public void succees(PrintWriter writer) {
		writer.append("<script>");
		writer.append("alert('파일 업로드 성공')");
		writer.append("document.href = referrer");
		writer.append("</script>");
	}

	@Override
	public void failes(PrintWriter writer) {
		writer.append("<script>");
		writer.append("alert('파일 업로드 실패')");
		writer.append("document.href = referrer");
		writer.append("</script>");
	}

}
