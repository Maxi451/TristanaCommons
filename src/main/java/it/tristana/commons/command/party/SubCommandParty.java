package it.tristana.commons.command.party;

import org.bukkit.plugin.Plugin;

import it.tristana.commons.command.MainCommand;
import it.tristana.commons.command.PlayerSubCommand;
import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.interfaces.arena.player.PartiesManager;

public abstract class SubCommandParty extends PlayerSubCommand {

	protected SettingsDefaultCommands settings;
	protected PartiesManager partiesManager;
	
	public SubCommandParty(MainCommand<? extends Plugin> main, String name, String permission, SettingsDefaultCommands settings, PartiesManager partiesManager) {
		super(main, name, permission);
		this.settings = settings;
		this.partiesManager = partiesManager;
	}
}
