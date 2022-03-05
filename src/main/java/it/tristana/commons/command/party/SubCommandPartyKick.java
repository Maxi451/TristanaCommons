package it.tristana.commons.command.party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.command.CommandParty;
import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Party;

public class SubCommandPartyKick extends SubCommandParty {

	public SubCommandPartyKick(MainCommand<? extends Plugin> main, String name, String permission, CommandParty superCommand, SettingsDefaultCommands settings, PartiesManager partiesManager) {
		super(main, name, permission, superCommand, settings, partiesManager);
	}

	@Override
	protected void run(Player player, String[] args) {
		if (args.length < 3) {
			CommonsHelper.info(player, superCommand.getCommandFor(this));
			return;
		}
		Party party = partiesManager.getPartyFromPlayer(player);
		if (party == null) {
			CommonsHelper.info(player, settings.getCommandPartyNotInAParty());
			return;
		}
		if (party.getLeader() != player) {
			CommonsHelper.info(player, settings.getCommandPartyNotLeader());
			return;
		}
		Player target = Bukkit.getPlayerExact(args[2]);
		if (target == null || target == player) {
			CommonsHelper.info(player, CommonsHelper.replaceAll(settings.getCommandPartyNotOnline(), "{player}", args[2]));
			return;
		}
		Party other = partiesManager.getPartyFromPlayer(target);
		if (other != party) {
			CommonsHelper.info(player, settings.getCommandPartyInviteTargetInOtherParty());
			return;
		}
		String message = CommonsHelper.replaceAll(settings.getCommandPartyMemberKicked(), "{target}", target.getName());
		party.getPlayers().forEach(member -> CommonsHelper.info(member, message));
		party.removePlayer(target);
	}
	
	@Override
	protected String getAdditionalHelpParameters() {
		return "<player>";
	}

	@Override
	protected String getHelp() {
		return settings.getCommandPartyKickHelp();
	}

}
