package it.tristana.commons.interfaces.database;

import org.bukkit.OfflinePlayer;

public interface UserRetriever<U extends User> {

	U getUser(OfflinePlayer player);
	
	void saveUser(U user);
}
