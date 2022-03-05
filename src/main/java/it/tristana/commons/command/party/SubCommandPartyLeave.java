package it.tristana.commons.command.party;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.command.CommandParty;
import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Party;

public class SubCommandPartyLeave extends SubCommandParty {

	public SubCommandPartyLeave(MainCommand<? extends Plugin> main, String name, String permission, CommandParty superCommand, SettingsDefaultCommands settings, PartiesManager partiesManager) {
		super(main, name, permission, superCommand, settings, partiesManager);
	}

	@Override
	protected void run(Player player, String[] args) {
		Party party = partiesManager.getPartyFromPlayer(player);
		if (party == null) {
			CommonsHelper.info(player, settings.getCommandPartyNotInAParty());
			return;
		}
		if (party.getLeader() == player) {
			party.getPlayers().forEach(member -> CommonsHelper.info(member, settings.getCommandPartyDisbanded()));
			partiesManager.disbandParty(party);
		} else {
			party.getPlayers().forEach(member -> CommonsHelper.info(member, CommonsHelper.replaceAll(settings.getCommandPartyLeavePlayerLeft(), "{player}", player.getName())));
			party.removePlayer(player);
		}
	}

	@Override
	protected String getHelp() {
		return settings.getCommandPartyLeaveHelp();
	}
}
