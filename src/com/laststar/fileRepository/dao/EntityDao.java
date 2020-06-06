package com.laststar.fileRepository.dao;

import java.sql.Connection;

import com.laststar.fileRepository.OracleDB;

public abstract class EntityDao {

	protected Connection connection;

	protected EntityDao() {
		connection = OracleDB.getConnection();
	}
}
