package it.tristana.commons.config;

public class SettingsDefaultCommands extends Settings<ConfigDefaultCommands> {
	
	private String generalHelp;
	
	private String commandQueryExecuted;
	private String commandQuerySqlError;
	private String commandQueryHelp;
	
	private String commandHelpHelp;
	
	private String commandReloadReloaded;
	private String commandReloadHelp;
	
	private String commandVersionMessage;
	private String commandVersionHelp;
	
	private String commandPartyHelp;
	private String commandPartyCantInviteOffline;
	
	public SettingsDefaultCommands(ConfigDefaultCommands config) {
		super(config);
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
		
		commandPartyHelp = config.getString(ConfigDefaultCommands.COMMAND_PARTY_HELP);
		commandPartyCantInviteOffline = config.getString(ConfigDefaultCommands.COMMAND_PARTY_CANT_INVITE_OFFLINE);
	}

	public String getCommandPartyCantInviteOffline() {
		return commandPartyCantInviteOffline;
	}

	public String getCommandPartyHelp() {
		return commandPartyHelp;
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
