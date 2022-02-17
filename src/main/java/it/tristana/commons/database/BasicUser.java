package it.tristana.commons.database;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.database.User;

public class BasicUser implements User {

	protected Player player;
	
	public BasicUser(Player player) {
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}
}
