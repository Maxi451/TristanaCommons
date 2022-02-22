package it.tristana.commons.interfaces.arena.player;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.Arena;

/**
 * A Team is a group of players that compete<br>
 * together against other teams to win a game
 * @param <P> The {@link TeamingPlayer} subclass used in this class
 * @param <A> The {@link Arena} subclass used in this class
 */
public interface Team<P extends TeamingPlayer<?, A>, A extends Arena<P>> {

	/**
	 * A team only exists inside a single arena
	 * @return The arena where this team exist
	 */
	A getArena();
	
	/**
	 * The returned list is a copy, so it is<br>
	 * safe for modification and iteration
	 * @return The list of {@link TeamingPlayer}s in this team
	 */
	List<P> getPlayers();

	/**
	 * Adds the given player to this team. A player is<br>
	 * expected to be part of only a single team each time.<br>
	 * Implementations should manually add<br>
	 * {@code player.setTeam(this)} inside this call
	 * @param player The player to add
	 */
	void addPlayer(P player);
	
	/**
	 * Tries to remove the given player from this team. If<br>
	 * no player inside the {@link #getPlayers()} list<br>
	 * equals the given one, then no action is performed
	 * @param player The player to remove
	 */
	void removePlayer(P player);
	
	/**
	 * Each team has an unique name inside the arena
	 * @return The team's name
	 */
	String getName();
	
	/**
	 * Each team has a chat color code (such as &4)
	 * @return The chat color code for this team
	 */
	String getColorCode();
	
	/**
	 * Each team has a spawning location where its players may spawn
	 * @return The team's spawnpoint
	 */
	Location getSpawnpoint();
	
	/**
	 * Updates the spawnpoint location for this team. Players will spawn<br>
	 * with the rotation yaw and pitch from the given location
	 * @param location The new spawnpoint location
	 */
	void setSpawnpoint(Location location);
	
	/**
	 * Checks if the given Bukkit player is contained in this team
	 * @param player The player to check
	 * @return True if at least one {@link TeamingPlayer}<br>
	 * represents this Bukkit player, false otherwise
	 */
	boolean hasPlayer(Player player);
}
