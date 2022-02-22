package it.tristana.commons.interfaces.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	void openConnection() throws SQLException, ClassNotFoundException;

	/**
	 * Closes the connection towards this database
	 * @throws SQLException If an error occurred while closing the connection
	 */
	void closeConnection() throws SQLException;

	/**
	 * Executes a select query (such as with SELECT or SHOW) on the database and retrieves the results
	 * @param query The query to execute on this database
	 * @return The {@link ResultSet} result
	 * @throws SQLException If a SQL error occurred
	 */
	ResultSet executeQuery(String query) throws SQLException;

	/**
	 * Executes an update query (such as with INSERT, UPDATE or DELETE) on the database
	 * @param query The query to execute on this database
	 * @throws SQLException If a SQL error occurred
	 */
	void executeUpdate(String query) throws SQLException;

	/**
	 * Determines if the given query is a select or update and calls the appropriate method
	 * @param query The query to execute on this database
	 * @return A {@link ResultSet} if the query was a select, {@code null} otherwise
	 * @throws SQLException If a SQL error occurred
	 */
	ResultSet executeSomething(String query) throws SQLException;

	/**
	 * Prints the given {@link ResultSet} to the given {@link CommandSender}
	 * @param sender The entity that will receive the output
	 * @param resultSet The result of a select query
	 */
	static void showResults(CommandSender sender, ResultSet resultSet) {
		List<String> lines = DBTablePrinter.printResultSet(resultSet);
		for (String line : lines) {
			sender.sendMessage(line);
		}
	}
}
