package com.laststar.fileRepository.controller;

import java.io.PrintWriter;

public interface ResultReturnable {

	static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	static final String CONTENT_TYPE_HTML_UTF8 = "text/html; charset=utf-8";
	
	void succees(PrintWriter writer);
	void failes(PrintWriter writer);
}
