package it.tristana.commons.arena;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

import it.tristana.commons.helper.BasicTickablesManager;
import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.ArenasManager;

public class BasicArenasManager<A extends Arena<?>> extends BasicTickablesManager implements ArenasManager<A> {

	protected List<A> arenas;

	public BasicArenasManager() {
		arenas = new LinkedList<>();
	}

	@Override
	public Collection<A> getArenas() {
		return new LinkedList<>(arenas);
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
		if (getArenaInWorld(arena.getWorld()) != null) {
			return false;
		}
		registerTickable(arena);
		return arenas.add(arena);
	}

	@Override
	public boolean removeArena(A arena) {
		removeTickable(arena);
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

	@Override
	public A getArenaWithPlayer(Player player) {
		A arena = getArenaInWorld(player.getWorld());
		return arena == null || arena.getArenaPlayer(player) == null ? null : arena;
	}

	@Override
	public A getArenaInWorld(World world) {
		return arenas.stream().findAny().orElse(null);
	}

	@Override
	public void cycleArena(A arena) {
		if (arenas.remove(arena)) {
			arenas.add(arena);
		}
	}
}
