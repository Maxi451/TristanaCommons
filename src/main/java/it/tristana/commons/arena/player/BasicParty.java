package it.tristana.commons.arena.player;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.player.Party;

public class BasicParty implements Party {
	
	protected Player leader;
	protected Collection<Player> players;
	protected Collection<String> invites;

	public BasicParty(Player leader) {
		this.leader = leader;
		players = new HashSet<>();
		invites = new HashSet<>();
	}

	@Override
	public boolean addPlayer(Player player) {
		return players.add(player);
	}

	@Override
	public void removePlayer(Player player) {
		players.remove(player);
	}

	@Override
	public void invite(Player player) {
		invites.add(player.getName());
	}

	@Override
	public boolean hasInvited(Player player) {
		return invites.contains(player.getName());
	}

	@Override
	public Collection<String> getInvites() {
		return invites;
	}

	@Override
	public Player getLeader() {
		return leader;
	}

	@Override
	public Collection<Player> getPlayers() {
		return players;
	}

	@Override
	public boolean tryToJoin(Player player) {
		boolean flag = invites.remove(player.getName());
		if (flag) {
			players.add(player);
		}
		return flag;
	}
}
