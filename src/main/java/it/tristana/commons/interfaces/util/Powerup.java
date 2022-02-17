package it.tristana.commons.interfaces.util;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

public interface Powerup<P extends ArenaPlayer<?>> {

	String getName();
	
	int getSpawnChance();
	
	boolean doAction(Player player, P arenaPlayer);
}