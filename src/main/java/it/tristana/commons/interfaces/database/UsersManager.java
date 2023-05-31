package it.tristana.commons.interfaces.database;

import java.util.Collection;

import org.bukkit.entity.Player;

/**
 * An UsersManager is in charge of managing<br>
 * the users, allowing to add, remove or save them
 * @param <U> The {@link User} subclass used in this class
 */
public interface UsersManager<U extends User> {

	/**
	 * Retrieves the user that represents the given player
	 * @param player The player to look for
	 * @return An {@link User} instance, or {@code null} if no user represents the given player
	 */
	U getUser(Player player);
	
	/**
	 * Loads the user represented by the given player and adds it to the manager
	 * @param player The player to add
	 */
	void addUser(Player player);
	
	/**
	 * Add the given user to the manager
	 * @param user The user to add
	 */
	void addUser(U user);
	
	/**
	 * Removes the user represented by the given player from the manager
	 * @param player The player to remove
	 */
	U removeUser(Player player);
	
	/**
	 * Removes the given user from the manager
	 * @param user The user to remove
	 */
	void removeUser(U user);
	
	/**
	 * Retrieves a copy of the underlying collection of<br>
	 * users that is safe for modification or iteration
	 * @return A copy of the users collection
	 */
	Collection<U> getUsers();
	
	/**
	 * Saves all the users 
	 */
	void saveOnlineUsers();
}
