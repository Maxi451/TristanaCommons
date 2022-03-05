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

public class SubCommandPartyInvite extends SubCommandParty {

	public SubCommandPartyInvite(MainCommand<? extends Plugin> main, String name, String permission, CommandParty superCommand, SettingsDefaultCommands settings, PartiesManager partiesManager) {
		super(main, name, permission, superCommand, settings, partiesManager);
	}

	@Override
	protected void run(Player player, String[] args) {
		if (args.length < 3) {
			CommonsHelper.info(player, superCommand.getCommandFor(this));
			return;
		}
		Player target = Bukkit.getPlayerExact(args[2]);
		if (target == null) {
			CommonsHelper.info(player, CommonsHelper.replaceAll(settings.getCommandPartyNotOnline(), "{player}", args[2]));
			return;
		}
		Party party = partiesManager.getPartyFromPlayer(player);
		if (party != null && party.getLeader() != player) {
			CommonsHelper.info(player, settings.getCommandPartyNotLeader());
			return;
		}
		if (partiesManager.getPartyFromPlayer(target) != null) {
			CommonsHelper.info(player, settings.getCommandPartyInviteTargetInOtherParty());
			return;
		}
		if (party == null) {
			party = partiesManager.createParty(player);
		}
		if (party.getInvites().contains(target.getName())) {
			CommonsHelper.info(player, settings.getCommandPartyInviteTargetAlreadyInvited());
			return;
		}
		party.invite(target);
		String message = CommonsHelper.replaceAll(settings.getCommandPartyInvitePlayerInvited(), new String[] {"{leader}", "{target}"}, new String[] {player.getName(), target.getName()});
		party.getPlayers().forEach(member -> CommonsHelper.info(member, message));
		CommonsHelper.info(target, CommonsHelper.replaceAll(settings.getCommandPartyInviteInvited(), "{leader}", player.getName()));
	}
	
	@Override
	protected String getAdditionalHelpParameters() {
		return "<player>";
	}

	@Override
	protected String getHelp() {
		return settings.getCommandPartyInviteHelp();
	}
}
