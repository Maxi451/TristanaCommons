package it.tristana.commons.interfaces.database;

import java.util.Collection;

import org.bukkit.entity.Player;

public interface UsersManager<U extends User> {

	U getUser(Player player);
	
	void addUser(Player player);
	
	void addUser(U user);
	
	void removeUser(Player player);
	
	void removeUser(U user);
	
	Collection<U> getUsers();
	
	void saveOnlineUsers();
}
