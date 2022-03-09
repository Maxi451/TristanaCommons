package it.tristana.commons.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class PlayerSubCommand extends SubCommand {

	public PlayerSubCommand(MainCommand<? extends Plugin> main, String name, String permission) {
		super(main, name, permission);
	}

	@Override
	public final void execute(CommandSender sender, String[] args) {
		run((Player) sender, args);
	}

	@Override
	protected final boolean requiresPlayer() {
		return true;
	}
	
	@Override
	protected final List<String> onTab(CommandSender sender, String[] args) {
		return tab((Player) sender, args);
	}
	
	protected List<String> tab(Player player, String[] args) {
		return null;
	}
	
	protected abstract void run(Player player, String[] args);
}
