package it.tristana.commons.command;

import org.bukkit.plugin.Plugin;

import it.tristana.commons.config.SettingsDefaultCommands;

abstract class DefaultSubCommand extends SubCommand {

	protected final SettingsDefaultCommands settings;
	
	DefaultSubCommand(MainCommand<? extends Plugin> main, String name, String permission, SettingsDefaultCommands settings) {
		super(main, name, permission);
		this.settings = settings;
	}
}
