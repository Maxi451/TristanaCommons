package it.tristana.commons.interfaces.database;

import org.bukkit.entity.Player;

/**
 * An User is the representation of a Bukkit player stored in a {@link it.tristana.commons.interfaces.database.UserRetriever UserRetriever}
 */
public interface User {

	/**
	 * Retrieves the Bukkit player instance represented by this object
	 * @return The Bukkit player
	 */
	Player getPlayer();
}
