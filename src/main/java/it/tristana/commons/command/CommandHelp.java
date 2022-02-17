package it.tristana.commons.command;

import java.util.Collection;

import org.bukkit.command.CommandSender;

import it.tristana.commons.helper.CommonsHelper;

public class CommandHelp extends SubCommand {

	public static final String COMMAND = "help";

	private final Collection<SubCommand> commands;
	
	public CommandHelp(MainCommand<?> main) {
		super(main, COMMAND, null);
		commands = main.getCommands().values();
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		for (SubCommand command : commands) {
			if (main.canExecute(sender, command)) {
				CommonsHelper.info(sender, command.getHelpMessage());
			}
		}
	}
	
	@Override
	public String getHelp() {
		return "Visualizza questa guida";
	}

	@Override
	protected boolean requiresPlayer() {
		return false;
	}
}
