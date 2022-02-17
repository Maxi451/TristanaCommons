package it.tristana.commons.command;

import org.bukkit.command.CommandSender;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.Reloadable;

public class CommandReload extends SubCommand {

	private Reloadable reloadable;
	
	public CommandReload(MainCommand<?> main, Reloadable plugin, String name, String permission) {
		super(main, name, permission);
		reloadable = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		reloadable.reload();
		CommonsHelper.info(sender, "&aPlugin ricaricato!");
	}

	@Override
	protected boolean requiresPlayer() {
		return false;
	}

	@Override
	protected String getHelp() {
		return "Ricarica il plugin";
	}

}
