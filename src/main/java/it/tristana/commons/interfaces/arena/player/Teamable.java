package it.tristana.commons.interfaces.arena.player;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Teamable<T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> {

	Collection<T> getTeams();
	
	T getTeam(Player player);
	
	int getMaxPerTeam();
	
	void setMaxPerTeam(int maxPerTeam);
	
	int getMaxPlayers();
	
	void addTeam(T team);
	
	boolean setSpawnpoint(Location spawnpoint);
	
	P getArenaPlayer(Player player);
	
	boolean areInSameTeam(Player p1, Player p2);
}
