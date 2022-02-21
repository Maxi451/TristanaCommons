package it.tristana.commons.interfaces.arena.player;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.Arena;

/**
 * An ArenaPlayer represent a player that can join and play in an arena
 * @param <A> The {@link Arena} subclass that is used in this class
 */
public interface ArenaPlayer<A extends Arena<?>> {
	
	/**
	 * Retrieves the arena where this player is<br>
	 * currently playing. Will not be null
	 * @return The arena where this player is playing
	 */
	A getArena();
	
	/**
	 * Retrieves the Bukkit player represented by this class. Only a single<br>
	 * instance of ArenaPlayer may exist for a given Bukkit player each time
	 * @return The Bukkit player
	 */
	Player getPlayer();
}
