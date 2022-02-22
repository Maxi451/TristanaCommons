package it.tristana.commons.interfaces.arena.player;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Something (for example an {@link it.tristana.commons.interfaces.arena.Arena Arena}) is defined Teamable<br>
 * if it supports several teams to compete against each other
 * @param <T> The {@link Team} subclass used in this class
 * @param <P> The {@link TeamingPlayer} subclass used in this class
 */
public interface Teamable<T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> {

	/**
	 * The returned collection is a copy of the internal one,<br>
	 * so it is safe for modification and iteration
	 * @return A copy of the underlying collection of teams
	 */
	Collection<T> getTeams();
	
	/**
	 * Retrieves the team that contains the given player.<br>
	 * At most one team is expected to contain him
	 * @param player The player to search
	 * @return An instance of {@link Team} if a match<br>
	 * was found, {@code null} otherwise
	 */
	T getTeam(Player player);
	
	/**
	 * Retrieves the maximum amount of players that a team is allowed to have
	 * @return The maximum amount of players
	 */
	int getMaxPerTeam();
	
	/**
	 * Sets the maximum amount of players that a team is allowed to have
	 * @param maxPerTeam The maximum amount of players
	 */
	void setMaxPerTeam(int maxPerTeam);
	
	/**
	 * Retrieves the maximum amount of players<br>
	 * supported by this Teamable object, for example<br>
	 * computating {@link #getTeams()}.size() * {@link #getMaxPerTeam()}
	 * @return The maximum amount of players that this Teamable object may have
	 */
	int getMaxPlayers();
	
	/**
	 * Adds a new team to this Teamable object. No duplicates are allowed
	 * @param team The team to add
	 */
	void addTeam(T team);
	
	/**
	 * Adds a spawnpoint that can be later assigned to a team as its spawnpoint
	 * @param spawnpoint The spawnpoint to add
	 * @return True if the spawnpoint was added, false otherwise
	 */
	boolean setSpawnpoint(Location spawnpoint);
	
	/**
	 * Returns a copy of the spawnpoints set. Since it is<br>
	 * a copy, it is safe for modification and iteration
	 * @return A copy of the spawnpoints list
	 */
	List<Location> getSpawnpoints();
	
	/**
	 * Retrieves the {@link TeamingPlayer} that represents the given Bukkit player
	 * @param player The player to look for
	 * @return The (unique) TeamingPlayer that represents this<br>
	 * Bukkit player, or {@code null} if nothing was found
	 */
	P getArenaPlayer(Player player);
	
	/**
	 * Checks if the given players are in the same team
	 * @param p1 The first player
	 * @param p2 The second player
	 * @return True if these players belong to the same team, false otherwise
	 */
	boolean areInSameTeam(Player p1, Player p2);
}
