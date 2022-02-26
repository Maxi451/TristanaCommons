package it.tristana.commons.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public abstract class SubCommand {

	protected MainCommand<? extends Plugin> main;
	
	private final String name;
	private final String permission;
	private String helpMessage;
	
	public SubCommand(MainCommand<? extends Plugin> main, String name, String permission) {
		this.main = main;
		this.name = name.toLowerCase();
		this.permission = permission;
	}
	
	public String getName() {
		return name;
	}
	
	protected String getAdditionalHelpParameters() {
		return "";
	}
	
	protected String getHelpMessage() {
		if (helpMessage == null) {
			String params = getAdditionalHelpParameters();
			helpMessage = "&f\"&b/" + main.getCommand() + " " + this.name + (params.length() > 0 ? " " + params : "") + "&f\": " + getHelp();
		}
		return helpMessage;
	}
	
	protected String getPermission() {
		return permission;
	}
	
	protected int getMinRequiredParameters() {
		return 0;
	}
	
	public abstract void execute(CommandSender sender, String[] args);
	
	protected abstract boolean requiresPlayer();
	
	protected abstract String getHelp();
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SubCommand)) {
			return false;
		}
		return name.equals(((SubCommand) other).name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
