package it.tristana.commons.config;

import it.tristana.commons.interfaces.Reloadable;

public class SettingsDefaultCommands implements Reloadable {

	private ConfigDefaultCommands config;
	
	private String generalHelp;
	
	private String commandQueryExecuted;
	private String commandQuerySqlError;
	private String commandQueryHelp;
	
	private String commandHelpHelp;
	
	private String commandReloadReloaded;
	private String commandReloadHelp;
	
	private String commandVersionMessage;
	private String commandVersionHelp;
	
	public SettingsDefaultCommands(ConfigDefaultCommands config) {
		setConfig(config);
		reload();
	}
	
	@Override
	public void reload() {
		generalHelp = config.getString(ConfigDefaultCommands.GENERAL_HELP);
		
		commandQueryExecuted = config.getString(ConfigDefaultCommands.COMMAND_QUERY_EXECUTED);
		commandQuerySqlError = config.getString(ConfigDefaultCommands.COMMAND_QUERY_SQL_ERROR);
		commandQueryHelp = config.getString(ConfigDefaultCommands.COMMAND_QUERY_HELP);

		commandHelpHelp = config.getString(ConfigDefaultCommands.COMMAND_HELP_HELP);

		commandReloadReloaded = config.getString(ConfigDefaultCommands.COMMAND_RELOAD_RELOADED);
		commandReloadHelp = config.getString(ConfigDefaultCommands.COMMAND_RELOAD_HELP);

		commandVersionMessage = config.getString(ConfigDefaultCommands.COMMAND_VERSION_MESSAGE);
		commandVersionHelp = config.getString(ConfigDefaultCommands.COMMAND_VERSION_HELP);
	}
	
	public void setConfig(ConfigDefaultCommands config) {
		this.config = config;
	}

	public String getGeneralHelp() {
		return generalHelp;
	}
	
	public String getCommandQueryExecuted() {
		return commandQueryExecuted;
	}

	public String getCommandQuerySqlError() {
		return commandQuerySqlError;
	}

	public String getCommandQueryHelp() {
		return commandQueryHelp;
	}

	public String getCommandHelpHelp() {
		return commandHelpHelp;
	}

	public String getCommandReloadReloaded() {
		return commandReloadReloaded;
	}

	public String getCommandReloadHelp() {
		return commandReloadHelp;
	}

	public String getCommandVersionMessage() {
		return commandVersionMessage;
	}

	public String getCommandVersionHelp() {
		return commandVersionHelp;
	}
}
