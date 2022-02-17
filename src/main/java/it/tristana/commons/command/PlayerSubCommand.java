package it.tristana.commons.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class PlayerSubCommand extends SubCommand {

	public PlayerSubCommand(MainCommand<? extends Plugin> main, String name, String permission) {
		super(main, name, permission);
	}

	@Override
	protected final boolean requiresPlayer() {
		return true;
	}

	@Override
	public final void execute(CommandSender sender, String[] args) {
		run((Player) sender, args);
	}
	
	protected abstract void run(Player player, String[] args);
}
