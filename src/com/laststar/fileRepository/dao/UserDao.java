package com.laststar.fileRepository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.laststar.fileRepository.OracleDB;
import com.laststar.fileRepository.entity.USERS;

public class UserDao extends EntityDao{

	public int getIdCount(String id) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM USERS WHERE ID = ?");
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt("COUNT(*)");
	}

	public int getNicknameCount(String nickname) throws SQLException {

		PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM USERS WHERE NICKNAME = ?");
		pstmt.setString(1, nickname);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt("COUNT(*)");
	}

	public USERS login(String id) throws SQLException {

		PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
		pstmt.setString(1, id);

		ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			return null;
		}

		USERS user = new USERS(rs.getString("ID"), rs.getString("PWD"), rs.getString("NICKNAME"), rs.getString("EMAIL"),
				rs.getString("JOINDATE"));

		return user;
	}

	public void register(String id, String pwd, String nickname, String email) throws SQLException {

		PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USERS VALUES(?,?,?,?,?)");

		pstmt.setString(1, id);
		pstmt.setString(2, pwd);
		pstmt.setString(3, nickname);
		pstmt.setString(4, email);
		pstmt.setString(5, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

		pstmt.executeUpdate();

	}
	

}
