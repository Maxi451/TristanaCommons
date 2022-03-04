package it.tristana.commons.config;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import it.tristana.commons.Main;

public class ConfigDefaultCommands extends Config {

	public static final String GENERAL_HELP = "general-help";
	
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
	public static final String COMMAND_PARTY_CANT_INVITE_OFFLINE = COMMAND_PARTY + "cant-invite-offline";
	
	public ConfigDefaultCommands() {
		super(new File(JavaPlugin.getPlugin(Main.class).getFolder(), "commands-language.yml"));
	}

	@Override
	protected void createDefault() {
		set(GENERAL_HELP, "Type '&b/%s %s&f' to display a list of commands");
		
		set(COMMAND_QUERY_EXECUTED, "Query executed");
		set(COMMAND_QUERY_SQL_ERROR, "&cMySQL error %d, check the console and/or the errors file");
		set(COMMAND_QUERY_HELP, "Executes SQL queries on the database (be careful!)");
		
		set(COMMAND_HELP_HELP, "Displays this guide");
		
		set(COMMAND_RELOAD_RELOADED, "&aPlugin reloaded!");
		set(COMMAND_RELOAD_HELP, "Reloads this plugin");
		
		set(COMMAND_VERSION_MESSAGE, "Using version: &e%s");
		set(COMMAND_VERSION_HELP, "Displays the current version of this plugin");
		
		set(COMMAND_PARTY_HELP, "Displays the available options");
		set(COMMAND_PARTY_CANT_INVITE_OFFLINE, "&cThe player {player} is not online!");
	}
}
