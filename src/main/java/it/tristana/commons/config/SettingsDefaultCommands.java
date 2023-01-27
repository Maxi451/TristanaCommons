package it.tristana.commons.config;

import java.io.File;

public class SettingsDefaultCommands extends Settings<ConfigDefaultCommands> {

	private String generalHelp;
	private String andWord;
	private String nobodyWord;

	private String commandQueryExecuted;
	private String commandQuerySqlError;
	private String commandQueryHelp;

	private String commandHelpHelp;

	private String commandReloadReloaded;
	private String commandReloadHelp;

	private String commandVersionMessage;
	private String commandVersionHelp;

	private String commandPartyHelp;
	private String commandPartyNotLeader;
	private String commandPartyAlreadyInAParty;
	private String commandPartyNotInvited;
	private String commandPartyNotInAParty;
	private String commandPartyDisbanded;
	private String commandPartyPlayerJoined;
	private String commandPartyLeavePlayerLeft;
	private String commandPartyLeaveHelp;
	private String commandPartyNotOnline;
	private String commandPartyInviteTargetInOtherParty;
	private String commandPartyInviteTargetAlreadyInvited;
	private String commandPartyInvitePlayerInvited;
	private String commandPartyInviteInvited;
	private String commandPartyInviteHelp;
	private String commandPartyMemberKicked;
	private String commandPartyKickHelp;
	private String commandPartyJoinHelp;
	private String commandPartyListHelp;

	private String commandMainLobbySet;
	private String commandMainLobbyHelp;

	public SettingsDefaultCommands(File folder) {
		super(folder, ConfigDefaultCommands.class);
	}

	@Override
	protected void reload(ConfigDefaultCommands config) {
		generalHelp = config.getString(ConfigDefaultCommands.GENERAL_HELP);
		andWord = config.getString(ConfigDefaultCommands.AND_WORD);
		nobodyWord = config.getString(ConfigDefaultCommands.NOBODY_WORD);

		commandQueryExecuted = config.getString(ConfigDefaultCommands.COMMAND_QUERY_EXECUTED);
		commandQuerySqlError = config.getString(ConfigDefaultCommands.COMMAND_QUERY_SQL_ERROR);
		commandQueryHelp = config.getString(ConfigDefaultCommands.COMMAND_QUERY_HELP);

		commandHelpHelp = config.getString(ConfigDefaultCommands.COMMAND_HELP_HELP);

		commandReloadReloaded = config.getString(ConfigDefaultCommands.COMMAND_RELOAD_RELOADED);
		commandReloadHelp = config.getString(ConfigDefaultCommands.COMMAND_RELOAD_HELP);

		commandVersionMessage = config.getString(ConfigDefaultCommands.COMMAND_VERSION_MESSAGE);
		commandVersionHelp = config.getString(ConfigDefaultCommands.COMMAND_VERSION_HELP);

		commandPartyHelp = config.getString(ConfigDefaultCommands.COMMAND_PARTY_HELP);
		commandPartyNotLeader = config.getString(ConfigDefaultCommands.COMMAND_PARTY_NOT_LEADER);
		commandPartyAlreadyInAParty = config.getString(ConfigDefaultCommands.COMMAND_PARTY_ALREADY_IN_A_PARTY);
		commandPartyNotInAParty = config.getString(ConfigDefaultCommands.COMMAND_PARTY_NOT_IN_A_PARTY);
		commandPartyNotInvited = config.getString(ConfigDefaultCommands.COMMAND_PARTY_NOT_INVITED);
		commandPartyDisbanded = config.getString(ConfigDefaultCommands.COMMAND_PARTY_DISBANDED);
		commandPartyPlayerJoined = config.getString(ConfigDefaultCommands.COMMAND_PARTY_PLAYER_JOINED);
		commandPartyLeavePlayerLeft = config.getString(ConfigDefaultCommands.COMMAND_PARTY_LEAVE_PLAYER_LEFT);
		commandPartyLeaveHelp = config.getString(ConfigDefaultCommands.COMMAND_PARTY_LEAVE_HELP);
		commandPartyNotOnline = config.getString(ConfigDefaultCommands.COMMAND_PARTY_NOT_ONLINE);
		commandPartyInviteTargetInOtherParty = config.getString(ConfigDefaultCommands.COMMAND_PARTY_INVITE_TARGET_IN_OTHER_PARTY);
		commandPartyInviteTargetAlreadyInvited = config.getString(ConfigDefaultCommands.COMMAND_PARTY_INVITE_ALREADY_INVITED);
		commandPartyInvitePlayerInvited = config.getString(ConfigDefaultCommands.COMMAND_PARTY_INVITE_PLAYER_INVITED);
		commandPartyInviteInvited = config.getString(ConfigDefaultCommands.COMMAND_PARTY_INVITE_INVITED);
		commandPartyInviteHelp = config.getString(ConfigDefaultCommands.COMMAND_PARTY_INVITE_HELP);
		commandPartyMemberKicked = config.getString(ConfigDefaultCommands.COMMAND_PARTY_MEMBER_KICKED);
		commandPartyKickHelp = config.getString(ConfigDefaultCommands.COMMAND_PARTY_KICK_HELP);
		commandPartyJoinHelp = config.getString(ConfigDefaultCommands.COMMAND_PARTY_JOIN_HELP);
		commandPartyListHelp = config.getString(ConfigDefaultCommands.COMMAND_PARTY_LIST_HELP);

		commandMainLobbySet = config.getString(ConfigDefaultCommands.COMMAND_MAIN_LOBBY_SET);
		commandMainLobbyHelp = config.getString(ConfigDefaultCommands.COMMAND_MAIN_LOBBY_HELP);
	}

	public String getCommandMainLobbySet() {
		return commandMainLobbySet;
	}

	public String getCommandMainLobbyHelp() {
		return commandMainLobbyHelp;
	}

	public String getCommandPartyListHelp() {
		return commandPartyListHelp;
	}

	public String getAndWord() {
		return andWord;
	}

	public String getNobodyWord() {
		return nobodyWord;
	}

	public String getCommandPartyAlreadyInAParty() {
		return commandPartyAlreadyInAParty;
	}

	public String getCommandPartyNotInvited() {
		return commandPartyNotInvited;
	}

	public String getCommandPartyPlayerJoined() {
		return commandPartyPlayerJoined;
	}

	public String getCommandPartyJoinHelp() {
		return commandPartyJoinHelp;
	}

	public String getCommandPartyKickHelp() {
		return commandPartyKickHelp;
	}

	public String getCommandPartyMemberKicked() {
		return commandPartyMemberKicked;
	}

	public String getCommandPartyDisbanded() {
		return commandPartyDisbanded;
	}

	public String getCommandPartyLeavePlayerLeft() {
		return commandPartyLeavePlayerLeft;
	}

	public String getCommandPartyLeaveHelp() {
		return commandPartyLeaveHelp;
	}

	public String getCommandPartyNotInAParty() {
		return commandPartyNotInAParty;
	}

	public String getCommandPartyInvitePlayerInvited() {
		return commandPartyInvitePlayerInvited;
	}

	public String getCommandPartyInviteInvited() {
		return commandPartyInviteInvited;
	}

	public String getCommandPartyInviteTargetAlreadyInvited() {
		return commandPartyInviteTargetAlreadyInvited;
	}

	public String getCommandPartyInviteTargetInOtherParty() {
		return commandPartyInviteTargetInOtherParty;
	}

	public String getCommandPartyNotLeader() {
		return commandPartyNotLeader;
	}

	public String getCommandPartyInviteHelp() {
		return commandPartyInviteHelp;
	}

	public String getCommandPartyNotOnline() {
		return commandPartyNotOnline;
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
