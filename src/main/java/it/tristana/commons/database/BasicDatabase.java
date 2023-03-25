package it.tristana.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import it.tristana.commons.interfaces.database.Database;

public abstract class BasicDatabase implements Database {

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
	public synchronized Connection openConnection() throws SQLException {
		String url = "jdbc:mysql://"+ host + ":" + port + "?tcpKeepAlive=true&autoReconnect=true&useSSL=false&useJDBCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		Connection connection = DriverManager.getConnection(url, username, password);
		connection.setCatalog(database);
		return connection;
	}

	@Override
	public void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public void executeQuery(String sql, SqlConsumer action) throws SQLException {
		Connection connection = openConnection();
		if (action != null) {
			action.accept(connection.createStatement().executeQuery(sql));
		}
		closeConnection(connection);
	}

	@Override
	public void executeUpdate(String sql) throws SQLException {
		Connection connection = openConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		closeConnection(connection);
	}

	public abstract void createTables() throws SQLException;
}
