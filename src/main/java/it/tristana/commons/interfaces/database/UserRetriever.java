package it.tristana.commons.interfaces.database;

import org.bukkit.OfflinePlayer;

/**
 * An UserRetriever is used to load and save user data from/in the file system
 * @param <U> The {@link User} subclass used in this class
 */
public interface UserRetriever<U extends User> {

	/**
	 * Attempts to load the given player from the<br>
	 * file system. If there is an error or the<br>
	 * user does not exist, a new user is returned
	 * @param player The player to look for
	 * @return A {@link User} representation of the given player
	 */
	U getUser(OfflinePlayer player);
	
	/**
	 * Saves the given user to the file system
	 * @param user The user to save
	 */
	void saveUser(U user);
}
