package it.tristana.commons.command.party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Party;

public class SubCommandPartyJoin extends SubCommandParty {

	public SubCommandPartyJoin(MainCommand<? extends Plugin> main, String name, String permission, SettingsDefaultCommands settings, PartiesManager partiesManager) {
		super(main, name, permission, settings, partiesManager);
	}

	@Override
	protected void run(Player player, String[] args) {
		if (args.length < 3) {
			CommonsHelper.info(player, getHelp());
			return;
		}
		Player target = Bukkit.getPlayerExact(args[2]);
		if (target == null) {
			CommonsHelper.info(player, CommonsHelper.replaceAll(settings.getCommandPartyCantInviteOffline(), "{player}", args[2]));
		}
		Party party = partiesManager.getPartyFromPlayer(player);
	}

	@Override
	protected String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}
}
