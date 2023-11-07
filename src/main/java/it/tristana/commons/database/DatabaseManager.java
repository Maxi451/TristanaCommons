package it.tristana.commons.database;

import org.bukkit.OfflinePlayer;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UserRetriever;

public abstract class DatabaseManager<U extends User> extends BasicDatabase implements UserRetriever<U> {

	public DatabaseManager(String host, String database, String username, String password, int port) {
		super(host, database, username, password, port);
	}

	protected static String getUuid(OfflinePlayer player) {
		return CommonsHelper.getUuid(player);
	}

	protected static String getUuid(String name) {
		return CommonsHelper.getUuid(name);
	}
}
