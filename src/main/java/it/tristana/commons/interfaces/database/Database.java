package it.tristana.commons.interfaces.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.command.CommandSender;

import it.tristana.commons.database.DBTablePrinter;

public interface Database {

	void openConnection() throws SQLException, ClassNotFoundException;

	void closeConnection() throws SQLException;

	ResultSet executeQuery(String query) throws SQLException;

	void executeUpdate(String query) throws SQLException;

	ResultSet executeSomething(String query) throws SQLException;

	static void showResults(CommandSender sender, ResultSet resultSet) throws SQLException {
		List<String> lines = DBTablePrinter.printResultSet(resultSet);
		for (String line : lines) {
			sender.sendMessage(line);
		}
	}
}
