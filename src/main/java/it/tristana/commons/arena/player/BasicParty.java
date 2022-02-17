package it.tristana.commons.arena.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.player.Party;

public class BasicParty implements Party {
	
	private Player leader;
	private List<Player> players;
	private List<String> invites;

	public BasicParty(Player leader) {
		this.leader = leader;
		players = new ArrayList<>();
		invites = new ArrayList<>();
	}

	@Override
	public void addPlayer(Player player) {
		players.add(player);
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
	public List<String> getInvites() {
		return invites;
	}

	@Override
	public Player getLeader() {
		return leader;
	}

	@Override
	public List<Player> getPlayers() {
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
