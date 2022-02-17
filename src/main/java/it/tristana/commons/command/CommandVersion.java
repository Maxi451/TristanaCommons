package it.tristana.commons.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.helper.CommonsHelper;

public class CommandVersion<P extends Plugin> extends SubCommand {

	private P plugin;
	
	public CommandVersion(MainCommand<P> main, P plugin, String name) {
		super(main, name, null);
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		CommonsHelper.info(sender, "Versione in uso: &e" + plugin.getDescription().getFullName());
	}

	@Override
	protected boolean requiresPlayer() {
		return false;
	}

	@Override
	protected String getHelp() {
		return "visualizza la versione del plugin";
	}
}
