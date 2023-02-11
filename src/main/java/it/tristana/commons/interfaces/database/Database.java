package it.tristana.commons.interfaces.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.command.CommandSender;

import it.tristana.commons.database.DBTablePrinter;

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

	/**
	 * Executes a select query (such as with SELECT or SHOW) on the database
	 * @param <T> The result type of the given action
	 * @param query The query to execute on this database
	 * @param action The action that will be executed on the ResultSet
	 * @return The action's result, or {@code null} if the action was null
	 * @throws SQLException If a SQL error occurred
	 */
	<T> T executeQuery(String query, Function<? super ResultSet, T> action) throws SQLException;

	/**
	 * Executes a select query (such as with SELECT or SHOW) on the database
	 * @param query The query to execute on this database
	 * @param action The action that will be executed on the ResultSet
	 * @throws SQLException If a SQL error occurred
	 */
	default void executeQuery(String sql, Consumer<? super ResultSet> action) throws SQLException {
		executeQuery(sql, resultSet -> {
			if (action != null) {
				action.accept(resultSet);
			}
			return null;
		});
	}

	/**
	 * Executes an update query (such as with INSERT, UPDATE or DELETE) on the database
	 * @param query The query to execute on this database
	 * @throws SQLException If a SQL error occurred
	 */
	void executeUpdate(String query) throws SQLException;

	/**
	 * Determines if the given query is a select or update and calls the appropriate method
	 * @param <T> The result type of the given action
	 * @param query The query to execute on this database
	 * @param action The action that will be performed if the SQL is a selection action
	 * @return The action's result, or {@code null} if the action was null
	 * @throws SQLException If a SQL error occurred
	 */
	public <T> T executeSomething(String sql, Function<? super ResultSet, T> action) throws SQLException;

	/**
	 * Determines if the given query is a select or update and calls the appropriate method
	 * @param sql The query to execute on this database
	 * @param action The action that will be performed if the SQL is a selection action
	 * @throws SQLException If a SQL error occurred
	 */
	default void executeSomething(String sql, Consumer<? super ResultSet> action) throws SQLException {
		executeQuery(sql, resultSet -> {
			if (action != null) {
				action.accept(resultSet);
			}
			return null;
		});
	}

	/**
	 * Determines if the given query is a select or update and calls the appropriate method
	 * @param sql The query to execute on this database
	 * @throws SQLException If a SQL error occurred
	 */
	default void executeSomething(String sql) throws SQLException {
		executeSomething(sql, (Consumer<? super ResultSet>) null);
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
