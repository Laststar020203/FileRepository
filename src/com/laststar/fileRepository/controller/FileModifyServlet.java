package com.laststar.fileRepository.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
 * Servlet implementation class FileModifyServlet
 */
@WebServlet("/fileModify")
public class FileModifyServlet extends HttpServlet implements ResultReturnable {
	private static final long serialVersionUID = 1L;
       
	private FileDao dao;
	private FileProxy proxy;

	private Object modifyRepositoryPath;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileModifyServlet() {
        super();
        dao = new FileDao();
        proxy = new FileProxy();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String PATH = request.getSession().getServletContext().getRealPath("/");				
		final int SIZE = 100 * 1024 * 1024;

		request.setCharacterEncoding("UTF-8");
		response.setContentType(CONTENT_TYPE_HTML_UTF8);
		
		PrintWriter writer = response.getWriter();
		
		
		try {
			OracleDB.setAutoCommit(false);
						
			MultipartRequest multi = new MultipartRequest(request, PATH, SIZE, "UTF-8", new DefaultFileRenamePolicy());
			
			String originFileName = multi.getParameter("originFileName");
			String originFileVersion = multi.getParameter("originFileVersion");
			
			
			String modifyName = multi.getParameter("modifyFileName");
			String modifyVersion = multi.getParameter("modifyFileVersion");
			String modifyContent = multi.getParameter("modifyContent");
			
			File modifyFile = multi.getFile((String) multi.getFileNames().nextElement());			
			
			FILES files = dao.getFile(originFileName, originFileVersion);
			
			
			if(files == null) {
				failesHtmlMessage(writer, "������ ������ �������� �ʽ��ϴ�.");
				return;
			}else if(request.getSession().getAttribute("user") == null 
					|| !files.getAUTHOR().getID().equals(((USERS)request.getSession().getAttribute("user")).getID())) {
				failesHtmlMessage(writer, "�ش� ������ ������ ������ �����ϴ�.");
				return;
			}
			
			
			if(!(modifyName.equals(originFileName) && modifyVersion.equals(originFileVersion)) && dao.getFileCount(modifyName, modifyVersion) != 0) {
				failesHtmlMessage(writer, "������ ���� �̸��� ������ �̹� �����մϴ�");
				return;
			}
			
			String modifyRepositoryPath;
			
			if(modifyFile != null) {
				modifyRepositoryPath = proxy.change(originFileName, originFileVersion, modifyFile);
			}else {
				modifyRepositoryPath = proxy.rename(modifyName, modifyVersion, files.getREPOSITORY_PATH());
			}
			
			
			
			dao.modifyFile(originFileName, originFileVersion, modifyName, modifyVersion, modifyContent, modifyRepositoryPath);
			
			response.sendRedirect("/show.jsp?name="+modifyName+"&version="+modifyVersion);
								
			OracleDB.commit();
			

			
		}catch(Exception e) {
			failesHtmlMessage(writer, "���� ����");
			e.printStackTrace();
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
