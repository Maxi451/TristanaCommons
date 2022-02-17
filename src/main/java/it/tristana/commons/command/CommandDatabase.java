package it.tristana.commons.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.DatabaseHolder;
import it.tristana.commons.interfaces.database.Database;

public class CommandDatabase extends SubCommand {

	private DatabaseHolder databaseHolder;
	
	public CommandDatabase(MainCommand<? extends Plugin> main, DatabaseHolder databaseHolder, String name, String permission) {
		super(main, name, permission);
		this.databaseHolder = databaseHolder;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		StringBuilder sql = new StringBuilder();
		for (int i = 1; i < args.length - 1; i ++) {
			sql.append(args[i]).append(' ');
		}
		sql.append(args[args.length - 1]);
		try {
			ResultSet resultSet = databaseHolder.getStorage().executeSomething(sql.toString());
			if (resultSet == null) {
				CommonsHelper.info(sender, "Query eseguita");
			} else {
				Database.showResults(sender, resultSet);
			}
		} catch (SQLException e) {
			CommonsHelper.info(sender, "&cErrore " + e.getErrorCode() + " eseguendo la query!");
		}
	}
	
	@Override
	protected boolean requiresPlayer() {
		return false;
	}

	@Override
	protected String getHelp() {
		return "Esegue query SQL sul database (occhio a quello che fai!)";
	}
	
	@Override
	protected String getAdditionalHelpParameters() {
		return "<sql>";
	}
	
	@Override
	protected int getMinRequiredParameters() {
		return 1;
	}
}
