package it.tristana.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

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
	public synchronized void openConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			return;
		}
		String url = "jdbc:mysql://"+ host + ":" + port + "?tcpKeepAlive=true&autoReconnect=true&useSSL=false&useJDBCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		connection = DriverManager.getConnection(url, username, password);
		connection.setCatalog(database);
		createTables();
	}

	@Override
	public void closeConnection() throws SQLException {
		connection.close();
	}

	@Override
	public void executeQuery(String sql, Consumer<? super ResultSet> action) throws SQLException {
		openConnection();
		action.accept(connection.createStatement().executeQuery(sql));
		closeConnection();
	}

	@Override
	public void executeUpdate(String sql) throws SQLException {
		openConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		closeConnection();
	}

	@Override
	public void executeSomething(String sql, Consumer<? super ResultSet> action) throws SQLException {
		if (sql.isEmpty()) {
			return;
		}
		String[] words = sql.split(" ");
		words[0] = words[0].toLowerCase();
		if (words[0].equals("select") || words[0].equals("show")) {
			executeQuery(sql, action);
		} else {
			executeUpdate(sql);
		}
	}

	protected abstract void createTables() throws SQLException;
}
