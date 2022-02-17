package it.tristana.commons.interfaces.arena.player;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.Arena;

public interface ArenaPlayer<A extends Arena<?>> {
	
	A getArena();
	
	Player getPlayer();
}
