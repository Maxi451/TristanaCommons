package it.tristana.commons.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.DatabaseHolder;
import it.tristana.commons.interfaces.PartiesHolder;
import it.tristana.commons.interfaces.Reloadable;

public class MainCommand<P extends Plugin> implements TabExecutor {

	protected P plugin;

	private String command;
	private Map<String, SubCommand> commands;
	private String help;

	public MainCommand(P plugin, SettingsDefaultCommands settings, String command) {
		this.plugin = plugin;
		this.command = command;
		commands = new TreeMap<String, SubCommand>();
		help = CommonsHelper.toChatColors(String.format(settings.getGeneralHelp(), command, CommandHelp.COMMAND));
		registerSubCommand(new CommandHelp(this, settings));
		registerSubCommand(new CommandVersion<P>(this, plugin, "version", settings));
		if (plugin instanceof Reloadable) {
			registerSubCommand(new CommandReload(this, (Reloadable) plugin, "reload", getAdminPerms(), settings));
		}
		if (plugin instanceof DatabaseHolder) {
			registerSubCommand(new CommandDatabase(this, (DatabaseHolder) plugin, "sql", getAdminPerms(), settings));
		}
		if (plugin instanceof PartiesHolder) {
			registerSubCommand(new CommandParty(this, (PartiesHolder) plugin, "party", null, settings));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			onEmptyCommand(sender);
			return true;
		}
		SubCommand subCommand = getSubCommand(args[0]);
		if (subCommand == null || !canExecute(sender, subCommand)) {
			help(sender);
			return true;
		}
		if (args.length - 1 < subCommand.getMinRequiredParameters()) {
			CommonsHelper.info(sender, subCommand.getHelpMessage());
			return true;
		}
		subCommand.execute(sender, args);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 0) {
			List<String> results = new ArrayList<>();
			commands.forEach((name, command) -> {
				if (canExecute(sender, command)) {
					results.add(name);
				}
			});
			return results;
		}
		SubCommand subCommand = getSubCommand(args[0]);
		if (subCommand != null) {
			return subCommand.onTab(sender, args);
		}
		String partial = args[0].toLowerCase();
		List<String> results = new ArrayList<>();
		commands.forEach((name, command) -> {
			if (name.startsWith(partial) && canExecute(sender, command)) {
				results.add(name);
			}
		});
		return results;
	}
	
	public void registerSubCommands() {}

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

	private SubCommand getSubCommand(String name) {
		return commands.get(name.toLowerCase());
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
