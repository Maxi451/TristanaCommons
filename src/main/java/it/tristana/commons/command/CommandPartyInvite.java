package it.tristana.commons.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandPartyInvite extends SubCommand {

	public CommandPartyInvite(MainCommand<? extends Plugin> main, String name, String permission) {
		super(main, name, permission);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean requiresPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}
}
