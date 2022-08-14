package it.tristana.commons.database;

import org.bukkit.OfflinePlayer;

import it.tristana.commons.interfaces.database.User;

public class BasicUser implements User {

	protected OfflinePlayer player;
	
	public BasicUser(OfflinePlayer player) {
		this.player = player;
	}

	@Override
	public OfflinePlayer getPlayer() {
		return player;
	}
}
