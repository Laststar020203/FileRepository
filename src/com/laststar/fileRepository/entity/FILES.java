package com.laststar.fileRepository.entity;

import java.sql.ResultSet;

public class FILES {

	private String NAME;
	private String VERSION;
	private USERS AUTHOR;
	private String CONTENT;
	private String SINCE;
	private String REPOSITORY_PATH;
	private int DOWNLOAD_COUNT;
		

	public FILES(String nAME, String vERSION, USERS aUTHOR, String cONTENT, String sINCE, 
			String rEPOSITORY_PATH, int dOWNLOAD_COUNT) {
		super();
		NAME = nAME;
		VERSION = vERSION;
		AUTHOR = aUTHOR;
		CONTENT = cONTENT;
		SINCE = sINCE;
		REPOSITORY_PATH = rEPOSITORY_PATH;
		DOWNLOAD_COUNT = dOWNLOAD_COUNT;
	}
	
	
	public String getNAME() {
		return NAME;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getVERSION() {
		return VERSION;
	}
	public USERS getAUTHOR() {
		return AUTHOR;
	}
	public String getSINCE() {
		return SINCE;
	}
	public String getREPOSITORY_PATH() {
		return REPOSITORY_PATH;
	}
	public int getDOWNLOAD_COUNT() {
		return DOWNLOAD_COUNT;
	}
	
	
}
