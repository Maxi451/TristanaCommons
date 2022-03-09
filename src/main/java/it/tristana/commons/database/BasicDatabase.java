package it.tristana.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.tristana.commons.interfaces.database.Database;

public abstract class BasicDatabase implements Database {

	protected Connection connection;

	protected String host;
	protected String database;
	protected String username;
	protected String password;
	protected int port;

	public BasicDatabase(String host, String database, String username, String password, int port) {
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	@Override
	public synchronized void openConnection() throws SQLException, ClassNotFoundException {
		if (connection != null && !connection.isClosed()) {
			return;
		}
		String url = "jdbc:mysql://"+ host + ":" + port + "?tcpKeepAlive=true&autoReconnect=true&useSSL=false&useJDBCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, username, password);
		connection.setCatalog(database);
		createTables();
	}

	@Override
	public void closeConnection() throws SQLException {
		connection.close();
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return connection.createStatement().executeQuery(sql);
	}

	@Override
	public void executeUpdate(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
	}

	@Override
	public ResultSet executeSomething(String sql) throws SQLException {
		ResultSet resultSet = null;
		String[] words = sql.split(" ");
		if (words.length > 0) {
			words[0] = words[0].toLowerCase();
			if (words[0].equals("select") || words[0].equals("show")) {
				resultSet = executeQuery(sql);
			} else {
				executeUpdate(sql);
			}
		}
		return resultSet;
	}
	
	protected abstract void createTables() throws SQLException;
}
