package com.laststar.fileRepository.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laststar.fileRepository.dao.FileDao;
import com.laststar.fileRepository.entity.FILES;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet implements ResultReturnable {
	private static final long serialVersionUID = 1L;
	private FileDao dao;

	public DownloadServlet() {
		super();
		dao = new FileDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		InputStream in = null;
		OutputStream out = null;
		try {
			String name = request.getParameter("name");
			String version = request.getParameter("version");

			FILES db = dao.getFile(name, version);

			File file = new File(db.getREPOSITORY_PATH());
			in = new FileInputStream(file);
			
			System.out.println(file.getName());

			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Description", "Send " + name + "-" + version);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + getIncodingFileName(request, file.getName() + "\""));
			
			response.setHeader("Content-Length", ""+file.length());
			
			out = response.getOutputStream();
			byte[] b = new byte[(int)file.length()];
			int leng = 0;
			
			while((leng = in.read(b)) > 0) {
				out.write(b ,0 ,leng);
			}
			
			
		} catch (Exception e) {
			failesHtmlMessage(response, "서버 에러");
		}
		
		in.close();
		out.close();
	}

	@Override
	public void sendMessage(PrintWriter writer, String msg) {
		writer.append(msg);

	}

	private void failesHtmlMessage(HttpServletResponse response, String msg) throws IOException {
		response.setContentType(CONTENT_TYPE_HTML_UTF8);
		sendMessage(response.getWriter(),
				"<script> alert('" + msg + "'); location.href = document.referrer; </script>");
	}

	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		} else if (header.indexOf("Firefox") > -1) {
			return "Firefox";
		} else if (header.indexOf("Mozilla") > -1) {
			if (header.indexOf("Firefox") > -1) {
				return "Firefox";
			} else {
				return "MSIE";
			}
		}
		return "MSIE";
	}

	private String getIncodingFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
		String browser = getBrowser(request);

		if (browser.equals("MSIE")) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
		}
		return fileName;

	}

}
