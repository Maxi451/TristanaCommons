package it.tristana.commons.config;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import it.tristana.commons.interfaces.DatabaseHolder;
import it.tristana.commons.interfaces.MainLobbyHolder;
import it.tristana.commons.interfaces.PartiesHolder;
import it.tristana.commons.interfaces.Reloadable;

public class ConfigDefaultCommands extends Config {

	public static final String GENERAL_HELP = "general-help";
	public static final String AND_WORD = "and-word";
	public static final String NOBODY_WORD = "nobody-word";

	private static final String COMMANDS = "commands.";
	private static final String HELP = "help-message";

	private static final String COMMAND_QUERY = COMMANDS + "database.";
	public static final String COMMAND_QUERY_EXECUTED = COMMAND_QUERY + "query-executed";
	public static final String COMMAND_QUERY_SQL_ERROR = COMMAND_QUERY + "query-error";
	public static final String COMMAND_QUERY_HELP = COMMAND_QUERY + HELP;

	private static final String COMMAND_HELP = COMMANDS + "help.";
	public static final String COMMAND_HELP_HELP = COMMAND_HELP + HELP;

	private static final String COMMAND_RELOAD = COMMANDS + "reload.";
	public static final String COMMAND_RELOAD_RELOADED = COMMAND_RELOAD + "reloaded";
	public static final String COMMAND_RELOAD_HELP = COMMAND_RELOAD + HELP;

	private static final String COMMAND_VERSION = COMMANDS + "version.";
	public static final String COMMAND_VERSION_MESSAGE = COMMAND_VERSION + "message";
	public static final String COMMAND_VERSION_HELP = COMMAND_VERSION + HELP;

	private static final String COMMAND_PARTY = COMMANDS + "party.";
	public static final String COMMAND_PARTY_HELP = COMMAND_PARTY + HELP;
	public static final String COMMAND_PARTY_NOT_LEADER = COMMAND_PARTY + "not-leader";
	public static final String COMMAND_PARTY_ALREADY_IN_A_PARTY = COMMAND_PARTY + "already-in-a-party";
	public static final String COMMAND_PARTY_NOT_IN_A_PARTY = COMMAND_PARTY + "not-in-a-party";
	public static final String COMMAND_PARTY_NOT_INVITED = COMMAND_PARTY + "not-invited";
	public static final String COMMAND_PARTY_DISBANDED = COMMAND_PARTY + "party-disbanded";
	public static final String COMMAND_PARTY_PLAYER_JOINED = COMMAND_PARTY + "player-joined";
	public static final String COMMAND_PARTY_LEAVE_PLAYER_LEFT = COMMAND_PARTY + "player-left";
	public static final String COMMAND_PARTY_LEAVE_HELP = COMMAND_PARTY + "leave-help";
	public static final String COMMAND_PARTY_NOT_ONLINE = COMMAND_PARTY + "cant-invite-offline";
	public static final String COMMAND_PARTY_INVITE_TARGET_IN_OTHER_PARTY = COMMAND_PARTY + "target-in-other-party";
	public static final String COMMAND_PARTY_INVITE_ALREADY_INVITED = COMMAND_PARTY + "target-already-invited";
	public static final String COMMAND_PARTY_INVITE_PLAYER_INVITED = COMMAND_PARTY + "player-invited";
	public static final String COMMAND_PARTY_INVITE_INVITED = COMMAND_PARTY + "you-invited";
	public static final String COMMAND_PARTY_INVITE_HELP = COMMAND_PARTY + "invite-help";
	public static final String COMMAND_PARTY_MEMBER_KICKED = COMMAND_PARTY + "member-kicked";
	public static final String COMMAND_PARTY_KICK_HELP = COMMAND_PARTY + "kick-help";
	public static final String COMMAND_PARTY_JOIN_HELP = COMMAND_PARTY + "join-help";
	public static final String COMMAND_PARTY_LIST_HELP = COMMAND_PARTY + "list-help";

	private static final String COMMAND_MAIN_LOBBY = COMMANDS + "main-lobby.";
	public static final String COMMAND_MAIN_LOBBY_SET = COMMAND_MAIN_LOBBY + "set";
	public static final String COMMAND_MAIN_LOBBY_HELP = COMMAND_MAIN_LOBBY + HELP;

	public ConfigDefaultCommands(File file, JavaPlugin plugin) {
		super(file, plugin);
	}

	@Override
	protected void createDefault() {
		set(GENERAL_HELP, "Type '&b/%s %s&f' to display a list of commands");
		set(AND_WORD, "and");
		set(NOBODY_WORD, "nobody");

		set(COMMAND_VERSION_MESSAGE, "Using version: &e%s");
		set(COMMAND_VERSION_HELP, "Displays the current version of this plugin");

		set(COMMAND_HELP_HELP, "Displays this guide");

		if (plugin instanceof DatabaseHolder) {
			set(COMMAND_QUERY_EXECUTED, "Query executed");
			set(COMMAND_QUERY_SQL_ERROR, "&cMySQL error %d, check the console and/or the errors file");
			set(COMMAND_QUERY_HELP, "Executes SQL queries on the database (be careful!)");
		}

		if (plugin instanceof Reloadable) {
			set(COMMAND_RELOAD_RELOADED, "&aPlugin reloaded!");
			set(COMMAND_RELOAD_HELP, "Reloads this plugin");
		}

		if (plugin instanceof PartiesHolder) {
			set(COMMAND_PARTY_HELP, "Displays the available options");
			set(COMMAND_PARTY_NOT_LEADER, "&cYou're not the party's leader");
			set(COMMAND_PARTY_ALREADY_IN_A_PARTY, "&cYou're already in a party!");
			set(COMMAND_PARTY_NOT_IN_A_PARTY, "&cYou're not in a party");
			set(COMMAND_PARTY_NOT_INVITED, "&cYou're not invited");
			set(COMMAND_PARTY_DISBANDED, "&cYour party was disbanded");
			set(COMMAND_PARTY_PLAYER_JOINED, "&a{player} joined the party");
			set(COMMAND_PARTY_LEAVE_PLAYER_LEFT, "&c{player} left the party");
			set(COMMAND_PARTY_NOT_ONLINE, "&cThe player {player} is not online!");
			set(COMMAND_PARTY_INVITE_TARGET_IN_OTHER_PARTY, "&cThat player is already in another party!");
			set(COMMAND_PARTY_INVITE_ALREADY_INVITED, "&cThat player is already invited");
			set(COMMAND_PARTY_INVITE_PLAYER_INVITED, "&a{leader} invited {target} to join the party");
			set(COMMAND_PARTY_INVITE_INVITED, "You are invited to join {leader}'s party! Use \"&b/party join {leader}&f\" to accept");
			set(COMMAND_PARTY_MEMBER_KICKED, "&c{target} has been kicked from the party!");
			set(COMMAND_PARTY_LEAVE_HELP, "Leave your current party");
			set(COMMAND_PARTY_INVITE_HELP, "Invite a player in the party");
			set(COMMAND_PARTY_KICK_HELP, "Kick a player from the party");
			set(COMMAND_PARTY_JOIN_HELP, "Join a party");
			set(COMMAND_PARTY_LIST_HELP, "List the players in the party");
		}

		if (plugin instanceof MainLobbyHolder) {
			set(COMMAND_MAIN_LOBBY_SET, "Main lobby set at your coordinates");
			set(COMMAND_MAIN_LOBBY_HELP, "Sets the location where players will be teleported after exiting an arena");
		}
	}
}
