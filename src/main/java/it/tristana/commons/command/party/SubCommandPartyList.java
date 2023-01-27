package it.tristana.commons.command.party;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.command.CommandParty;
import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Party;

public class SubCommandPartyList extends SubCommandParty {

	public SubCommandPartyList(MainCommand<? extends Plugin> main, String name, String permission, CommandParty superCommand, SettingsDefaultCommands settings, PartiesManager partiesManager) {
		super(main, name, permission, superCommand, settings, partiesManager);
	}

	@Override
	protected void run(Player player, String[] args) {
		Party party = partiesManager.getPartyFromPlayer(player);
		if (party == null) {
			CommonsHelper.info(player, settings.getCommandPartyNotInAParty());
			return;
		}
		CommonsHelper.info(player, CommonsHelper.playerListToString(CommonsHelper.playerListToPlayerNames(new ArrayList<>(party.getPlayers())), settings.getNobodyWord(), settings.getAndWord()));
	}

	@Override
	protected String getHelp() {
		return settings.getCommandPartyListHelp();
	}

	@Override
	protected List<String> tab(Player player, String[] args) {
		return new ArrayList<>();
	}
}
