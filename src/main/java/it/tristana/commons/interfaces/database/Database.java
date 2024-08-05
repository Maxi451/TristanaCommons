package it.tristana.commons.interfaces.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

import org.bukkit.command.CommandSender;

import it.tristana.commons.database.DBTablePrinter;
import it.tristana.commons.database.SqlAction;
import it.tristana.commons.database.SqlConsumer;
import it.tristana.commons.database.SqlRetriever;

/**
 * A Database is an organized collection of data<br>
 * where it is possible to store and query values
 */
public interface Database {

	/**
	 * Opens the connection towards this database
	 * @throws SQLException If an error occurred while opening the connection
	 * @throws ClassNotFoundException If the given JDBC adapter was not found
	 */
	Connection openConnection() throws SQLException, ClassNotFoundException;

	/**
	 * Closes the connection towards this database
	 * @throws SQLException If an error occurred while closing the connection
	 */
	void closeConnection(Connection connection) throws SQLException;

	void executeQuery(String sql, SqlConsumer action) throws SQLException;

	<T> T executeQuery(String sql, SqlRetriever<T> action) throws SQLException;

	default void executeQueryAsync(String sql, SqlConsumer action, Consumer<? super SQLException> onError) {
		new Thread(() -> {
			try {
				executeQuery(sql, action);
			} catch (SQLException e) {
				if (onError != null) {
					onError.accept(e);
				}
			}
		}).start();
	}

	void executeUpdate(String sql) throws SQLException;

	default void executeUpdateAsync(String sql, Consumer<? super SQLException> onError) {
		executeUpdateAsync(sql, null, onError);
	}

	default void executeUpdateAsync(String sql, SqlAction action, Consumer<? super SQLException> onError) {
		new Thread(() -> {
			try {
				executeUpdate(sql);
				if (action != null) {
					action.run();
				}
			} catch (SQLException e) {
				if (onError != null) {
					onError.accept(e);
				}
			}
		}).start();
	}

	default void executeSomething(String sql, SqlConsumer action) throws SQLException {
		String[] words = sql.split(" ");
		words[0] = words[0].toLowerCase();
		if (words[0].equals("select") || words[0].equals("show")) {
			executeQuery(sql, action);
			return;
		}

		executeUpdate(sql);
		if (action != null) {
			action.accept(null);
		}
	}

	default void executeSomethingAsync(String sql, SqlConsumer action, Consumer<? super SQLException> onError) {
		new Thread(() -> {
			try {
				executeSomething(sql, action);
			} catch (SQLException e) {
				if (onError != null) {
					onError.accept(e);
				}
			}
		}).start();
	}

	/**
	 * Prints the given {@link ResultSet} to the given {@link CommandSender}
	 * @param sender The entity that will receive the output
	 * @param resultSet The result of a select query
	 */
	static void showResults(CommandSender sender, ResultSet resultSet) {
		DBTablePrinter.printResultSet(resultSet).forEach(sender::sendMessage);
	}
}
