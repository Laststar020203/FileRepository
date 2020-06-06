package com.laststar.fileRepository.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.laststar.fileRepository.Paging;
import com.laststar.fileRepository.entity.FILES;
import com.laststar.fileRepository.entity.USERS;

public class FileDao extends EntityDao {

	public int getTotalFileCount() throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("SELET COUNT(*) FROM FILES");
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt("COUNT(*)");
	}

	public FILES[] getFileList(Paging paging) throws SQLException {
		FILES[] files = new FILES[paging.getEndRow() - paging.getStartRow() + 1];
		PreparedStatement pstmt = connection
				.prepareStatement("SELECT  F.NAME, F.VERSION, F.SINCE, U.NICKNAME FROM FILES F, USERS U"
						+ " WHERE F.USER_ID = U.ID" + " AND ROWNUM >= ?" + " AND ROWNUM <= ?");
		pstmt.setInt(1, paging.getStartRow());
		pstmt.setInt(2, paging.getEndRow());

		ResultSet rs = pstmt.executeQuery();
		for (int i = 0; rs.next(); i++) {
			String name = rs.getString("F.NAME");
			String version = rs.getString("F.VERSION");
			String since = rs.getString("F.SINCE");
			String user_name = rs.getString("U.NICKNAME");

			USERS author = new USERS(null, null, user_name, null, null);
			files[i] = new FILES(name, version, author, null, since, null, 0);
		}

		return files;
	}

	public FILES getFile(String name, String version) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT F.NAME, F.VERSION, F.CONTENT. F.SINCE, F.REPOSITORY_URL, F.DOWNLOAD_COUNT, U.ID, U.NICKNAME, U.EMAIL"
						+ " FROM FILES F, USERS U" + " WHERE F.USER_ID = U.ID" + " AND F.NAME = ?"
						+ " AND F.VERSION = ?");
		pstmt.setString(1, name);
		pstmt.setString(2, version);

		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			USERS author = new USERS(rs.getString("U.ID"), null, rs.getString("U.NICKNAME"), rs.getString("U.EMAIL"),
					null);
			return new FILES(rs.getString("F.NAME"), rs.getString("F.VERSION"), author, rs.getString("F.CONTENT"),
					rs.getString("F.SINCE"), rs.getString("F.REPOSITORY_URL"), rs.getInt("F.DOWNLOAD_COUNT"));
		} else {
			return null;
		}
		
	}
	
	public void insertFile(FILES file) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("INSERT INTO FILES VALUES(?,?,?,?,?,?,?)");
		pstmt.setString(1, file.getNAME());
		pstmt.setString(2, file.getVERSION());
		pstmt.setString(3, file.getAUTHOR().getID());
		pstmt.setString(4, file.getCONTENT());
		pstmt.setString(5, file.getSINCE());
		pstmt.setString(6, file.getREPOSITORY_URL());
		pstmt.setInt(7, 0);
		
		pstmt.executeUpdate();
	}

}
