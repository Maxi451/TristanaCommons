package it.tristana.commons.database;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UserRetriever;

public abstract class DatabaseManager<U extends User> extends BasicDatabase implements UserRetriever<U> {
	
	public DatabaseManager(String host, String database, String username, String password, int port) {
		super(host, database, username, password, port);
	}

	protected static String getUuid(OfflinePlayer player) {
		return player.getUniqueId().toString().replaceAll("-", "");
	}
	
	@SuppressWarnings("deprecation")
	protected static String getUuid(String name) {
		return getUuid(Bukkit.getOfflinePlayer(name));
	}
}
