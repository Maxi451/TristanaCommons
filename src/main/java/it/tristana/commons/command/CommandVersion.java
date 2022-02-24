package it.tristana.commons.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;

public class CommandVersion<P extends Plugin> extends DefaultSubCommand {

	private P plugin;
	
	public CommandVersion(MainCommand<P> main, P plugin, String name, SettingsDefaultCommands settings) {
		super(main, name, null, settings);
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		CommonsHelper.info(sender, String.format(settings.getCommandVersionMessage(), plugin.getDescription().getFullName()));
	}

	@Override
	protected boolean requiresPlayer() {
		return false;
	}

	@Override
	protected String getHelp() {
		return settings.getCommandVersionHelp();
	}
}
