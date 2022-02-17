package it.tristana.commons.interfaces.arena;

import java.util.Collection;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.util.TickablesManager;

public interface ArenasManager<A extends Arena<?>> extends TickablesManager {

	Collection<A> getArenas();
	
	A getArena(String name);
	
	boolean addArena(A arena);
	
	boolean removeArena(A arena);
	
	A getFirstArenaAvailable(Player player);
}
