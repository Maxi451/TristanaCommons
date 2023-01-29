package it.tristana.commons.arena.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.player.Team;
import it.tristana.commons.interfaces.arena.player.TeamingPlayer;

public class BasicTeam<P extends TeamingPlayer<? extends Team<P, A>, A>, A extends Arena<P>> implements Team<P, A> {
	
	protected A arena;
	protected List<P> players;
	protected String name;
	protected String colorCode;
	protected Location spawnPoint;
	protected Color color;

	public BasicTeam(A arena, Location spawnpoint, String name, String colorCode, Color color) {
		this.arena = arena;
		this.name = name;
		this.colorCode = colorCode;
		this.players = new ArrayList<>();
		this.spawnPoint = spawnpoint;
		this.color = color;
	}

	@Override
	public List<P> getPlayers() {
		return players;
	}
	
	@Override
	public void addPlayer(P player) {
		players.add(player);
	}

	@Override
	public void removePlayer(P player) {
		players.remove(player);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getColorCode() {
		return colorCode;
	}

	@Override
	public Location getSpawnpoint() {
		return spawnPoint;
	}

	@Override
	public void setSpawnpoint(Location location) {
		this.spawnPoint = location;
	}

	@Override
	public boolean hasPlayer(Player player) {
		boolean result = false;
		for (P test : players) {
			if (test.getPlayer() == player) {
				result = true;
				break;
			}
		}
		return result;
	}

	@Override
	public A getArena() {
		return arena;
	}

	@Override
	public Color getColor() {
		return color;
	}
}
