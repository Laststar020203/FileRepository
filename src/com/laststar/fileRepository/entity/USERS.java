package com.laststar.fileRepository.entity;

public class USERS {

	private String ID;
	private String PWD;
	private String NICKNAME;
	private String EMAIL;
	private String JOINDATE;
	
	public USERS(String ID, String PWD, String NICKNAME, String EMAIL, String JOINDATE) {
		this.ID = ID;
		this.PWD = PWD;
		this.NICKNAME = NICKNAME;
		this.EMAIL = EMAIL;
		this.JOINDATE = JOINDATE;
	}

	public String getID() {
		return ID;
	}

	public String getPWD() {
		return PWD;
	}

	public String getNICKNAME() {
		return NICKNAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public String getJOINDATE() {
		return JOINDATE;
	}
	
	
}
