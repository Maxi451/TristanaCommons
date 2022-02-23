package it.tristana.commons.interfaces.util;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

/**
 * A Powerup is an item that will give some sort of bonus<br>
 * or malus to the player that picked it up. Each powerup<br>
 * has a name and a percentage of chance to be selected
 * @param <P> The {@link ArenaPlayer} subclass that is used by this class
 */
public interface Powerup<P extends ArenaPlayer<?>> {

	/**
	 * Retrieves this powerup's name
	 * @return The powerup's name
	 */
	String getName();
	
	/**
	 * Each powerup has a percentage to be selected when<br>
	 * a {@link PowerupsManager} draws a random powerup
	 * @return The chances that this powerup will be selected. It is not required to be lower than 100
	 */
	int getSpawnChance();
	
	/**
	 * Performs this powerup's action on the given ArenaPlayer
	 * @param arenaPlayer The player that will suffer this action
	 * @return True if this action may be performed on the given player, false otherwise
	 */
	boolean doAction(P arenaPlayer);
}