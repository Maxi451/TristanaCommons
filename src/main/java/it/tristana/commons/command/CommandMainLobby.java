package it.tristana.commons.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.MainLobbyHolder;

public class CommandMainLobby extends DefaultSubCommand {

	private MainLobbyHolder mainLobbyHolder;
	
	public CommandMainLobby(MainCommand<? extends Plugin> main, MainLobbyHolder mainLobbyHolder, String name, String permission, SettingsDefaultCommands settings) {
		super(main, name, permission, settings);
		this.mainLobbyHolder = mainLobbyHolder;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			mainLobbyHolder.setMainLobby(((Player) sender).getLocation());
		}
		CommonsHelper.info(sender, settings.getCommandMainLobbySet());
	}

	@Override
	protected String getHelp() {
		return settings.getCommandMainLobbyHelp();
	}

	@Override
	protected boolean requiresPlayer() {
		return true;
	}
}
