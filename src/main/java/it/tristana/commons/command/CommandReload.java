package it.tristana.commons.command;

import org.bukkit.command.CommandSender;

import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.Reloadable;

public class CommandReload extends DefaultSubCommand {

	private Reloadable reloadable;
	
	public CommandReload(MainCommand<?> main, Reloadable plugin, String name, String permission, SettingsDefaultCommands settings) {
		super(main, name, permission, settings);
		reloadable = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		reloadable.reload();
		CommonsHelper.info(sender, settings.getCommandReloadReloaded());
	}

	@Override
	protected boolean requiresPlayer() {
		return false;
	}

	@Override
	protected String getHelp() {
		return settings.getCommandReloadHelp();
	}

}
