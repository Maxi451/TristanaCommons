package it.tristana.commons.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.DatabaseHolder;
import it.tristana.commons.interfaces.Reloadable;

public class MainCommand<P extends Plugin> implements CommandExecutor {

	protected P plugin;
	
	private String command;
	private Map<String, SubCommand> commands;
	private String help;

	public MainCommand(P plugin, SettingsDefaultCommands settings, String command) {
		this.plugin = plugin;
		this.command = command;
		commands = new HashMap<String, SubCommand>();
		help = CommonsHelper.toChatColors(String.format(settings.getGeneralHelp(), command, CommandHelp.COMMAND));
		registerSubCommand(new CommandHelp(this, settings));
		registerSubCommand(new CommandVersion<P>(this, plugin, "version", settings));
		if (plugin instanceof Reloadable) {
			registerSubCommand(new CommandReload(this, (Reloadable) plugin, "reload", getAdminPerms(), settings));
		}
		if (plugin instanceof DatabaseHolder) {
			registerSubCommand(new CommandDatabase(this, (DatabaseHolder) plugin, "sql", getAdminPerms(), settings));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			onEmptyCommand(sender);
			return true;
		}
		SubCommand subCommand = commands.get(args[0].toLowerCase());
		if (subCommand != null && canExecute(sender, subCommand)) {
			if (args.length - 1 > subCommand.getMinRequiredParameters()) {
				subCommand.execute(sender, args);
			} else {
				CommonsHelper.info(sender, subCommand.getHelpMessage());
			}
		} else {
			help(sender);
		}
		return true;
	}

	protected boolean canExecute(CommandSender sender, SubCommand command) {
		if (!hasPermission(sender, command)) {
			return false;
		}
		if (!command.requiresPlayer()) {
			return true;
		}
		return sender instanceof Player;
	}
	
	protected boolean hasPermission(CommandSender sender, SubCommand command) {
		boolean canExecute = false;
		String perms = getAdminPerms();
		if (perms != null) {
			canExecute = sender.hasPermission(perms);
		}
		if (!canExecute) {
			perms = command.getPermission();
			canExecute = perms == null || sender.hasPermission(perms);
		}
		return canExecute;
	}
	
	protected void onEmptyCommand(CommandSender sender) {
		help(sender);
	}
	
	protected String getAdminPerms() {
		return null;
	}
	
	private void help(CommandSender sender) {
		CommonsHelper.info(sender, help);
	}
	
	public void registerSubCommand(SubCommand command) {
		registerSubCommand(command.getName(), command);
	}
	
	public void registerSubCommand(String name, SubCommand command) {
		commands.put(name.toLowerCase(), command);
	}
	
	public String getCommand() {
		return command;
	}
	
	public Map<String, SubCommand> getCommands() {
		return commands;
	}
	
	public P getPlugin() {
		return plugin;
	}
}
