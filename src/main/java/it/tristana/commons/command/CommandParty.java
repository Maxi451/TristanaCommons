package it.tristana.commons.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.command.party.SubCommandParty;
import it.tristana.commons.command.party.SubCommandPartyJoin;
import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.PartiesHolder;
import it.tristana.commons.interfaces.arena.player.PartiesManager;

public class CommandParty extends DefaultSubCommand {

	private PartiesManager partiesManager;
	private Map<String, SubCommandParty> commands;
	
	public CommandParty(MainCommand<? extends Plugin> main, PartiesHolder partiesHolder, String name, String permission, SettingsDefaultCommands settings) {
		super(main, name, permission, settings);
		partiesManager = partiesHolder.getPartiesManager();
		commands = new HashMap<>();
		registerSubCommand(new SubCommandPartyJoin(main, "join", permission, settings, partiesManager));
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length < 2) {
			showHelp(sender);
			return;
		}
		SubCommandParty subCommand = commands.get(args[1].toLowerCase());
		if (subCommand == null) {
			showHelp(sender);
			return;
		}
		subCommand.execute(sender, args);
	}

	@Override
	protected String getHelp() {
		return settings.getCommandPartyHelp();
	}

	@Override
	protected boolean requiresPlayer() {
		return true;
	}
	
	private void registerSubCommand(SubCommandParty command) {
		commands.put(command.getName().toLowerCase(), command);
	}
	
	private void showHelp(CommandSender sender) {
		commands.entrySet().forEach(entry -> CommonsHelper.info(sender, "&f\"&b/" + main.getCommand() + " " + name + " " + entry.getKey() + "&f\": " + entry.getValue().getHelp()));
	}
}
