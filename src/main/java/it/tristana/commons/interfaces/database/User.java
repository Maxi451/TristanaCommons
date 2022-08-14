package it.tristana.commons.interfaces.database;

import org.bukkit.OfflinePlayer;

/**
 * An User is the representation of a Bukkit player stored in a {@link it.tristana.commons.interfaces.database.UserRetriever UserRetriever}
 */
public interface User {

	/**
	 * Retrieves the Bukkit offline player instance represented by this object
	 * @return The Bukkit offline player. To get the online player the {@link OfflinePlayer#getPlayer()}<br>
	 * method must be used. Beware that it will return {@code null}<br>
	 * if there is not an online counterpart for the offline player
	 */
	OfflinePlayer getPlayer();
}
