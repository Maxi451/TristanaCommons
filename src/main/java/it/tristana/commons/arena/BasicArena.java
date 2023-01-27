package it.tristana.commons.arena;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.MainLobbyHolder;
import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.Status;
import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

public abstract class BasicArena<A extends ArenaPlayer<?>> implements Arena<A> {

	protected MainLobbyHolder mainLobbyHolder;
	protected World world;
	protected String name;
	protected Status status;
	protected Location lobby;
	
	protected Collection<A> players;
	protected Collection<Player> spectators;
	protected int minPlayersToStart;
	
	public BasicArena(MainLobbyHolder mainLobbyHolder, World world, String name, int minPlayersToStart) {
		this.mainLobbyHolder = mainLobbyHolder;
		this.world = world;
		this.name = name;
		this.minPlayersToStart = minPlayersToStart;
		players = new HashSet<>();
		spectators = new HashSet<>();
		status = Status.WAITING;
	}

	@Override
	public Collection<A> getPlayers() {
		return new HashSet<>(players);
	}

	@Override
	public Collection<Player> getSpectators() {
		return new HashSet<>(spectators);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean hasPlayer(Player player) {
		return getArenaPlayer(player) != null;
	}

	@Override
	public Location getLobby() {
		return lobby;
	}

	@Override
	public void setLobby(Location lobby) {
		World lobbyWorld = lobby.getWorld();
		if (world != lobbyWorld) {
			throw new IllegalArgumentException("Arena world '" + world.getName() + "' and lobby world '" + lobbyWorld + "' are different");
		}
		this.lobby = lobby;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public A getArenaPlayer(Player player) {
		for (A test : players) {
			if (test.getPlayer() == player) {
				return test;
			}
		}
		return null;
	}

	@Override
	public int getMinPlayersToStart() {
		return minPlayersToStart;
	}

	@Override
	public void setMinPlayersToStart(int minPlayersToStart) {
		this.minPlayersToStart = minPlayersToStart;
	}

	@Override
	public World getWorld() {
		return world;
	}
}
