package it.tristana.commons.command;

import java.util.Collection;

import org.bukkit.command.CommandSender;

import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;

public class CommandHelp extends DefaultSubCommand {

	public static final String COMMAND = "help";

	private final Collection<SubCommand> commands;

	public CommandHelp(MainCommand<?> main, SettingsDefaultCommands settings) {
		super(main, COMMAND, null, settings);
		commands = main.getCommands().values();
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		commands.forEach(command -> {
			if (main.canExecute(sender, command)) {
				CommonsHelper.info(sender, command.getHelpMessage());
			}
		});
	}

	@Override
	public String getHelp() {
		return settings.getCommandHelpHelp();
	}

	@Override
	protected boolean requiresPlayer() {
		return false;
	}
}
