package it.tristana.commons.arena;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import it.tristana.commons.helper.BasicTickablesManager;
import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.ArenasManager;

public class BasicArenasManager<A extends Arena<?>> extends BasicTickablesManager implements ArenasManager<A> {

	protected Set<A> arenas;
	
	public BasicArenasManager() {
		arenas = new HashSet<>();
	}
	
	@Override
	public Collection<A> getArenas() {
		return arenas;
	}

	@Override
	public A getArena(String name) {
		for (A arena : arenas) {
			if (arena.getName().equals(name)) {
				return arena;
			}
		}
		return null;
	}

	@Override
	public boolean addArena(A arena) {
		return arenas.add(arena);
	}

	@Override
	public boolean removeArena(A arena) {
		return arenas.remove(arena);
	}

	@Override
	public A getFirstArenaAvailable(Player player) {
		for (A arena : arenas) {
			if (arena.testPlayerJoin(player)) {
				return arena;
			}
		}
		return null;
	}
}
