package it.tristana.commons.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.helper.PluginDraft;
import it.tristana.commons.interfaces.DatabaseHolder;
import it.tristana.commons.interfaces.database.Database;

public class CommandDatabase extends DefaultSubCommand {

	private DatabaseHolder databaseHolder;

	public CommandDatabase(MainCommand<? extends Plugin> main, DatabaseHolder databaseHolder, String name, String permission, SettingsDefaultCommands settings) {
		super(main, name, permission, settings);
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
			databaseHolder.getStorage().executeSomething(sql.toString(), resultSet -> {
				Database.showResults(sender, resultSet);
			});
			CommonsHelper.info(sender, settings.getCommandQueryExecuted());
		} catch (SQLException e) {
			CommonsHelper.info(sender, String.format(settings.getCommandQuerySqlError(), e.getErrorCode()));
			Plugin plugin = main.getPlugin();
			if (plugin instanceof PluginDraft) {
				((PluginDraft) plugin).writeThrowableOnErrorsFile(e);
			}
			e.printStackTrace();
		}
	}

	@Override
	protected List<String> onTab(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	protected boolean requiresPlayer() {
		return false;
	}

	@Override
	protected String getHelp() {
		return settings.getCommandQueryHelp();
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
