package it.tristana.commons.arena;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

import it.tristana.commons.helper.BasicTickablesManager;
import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.ArenasManager;
import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

public class BasicArenasManager<A extends Arena<P>, P extends ArenaPlayer<A>> extends BasicTickablesManager implements ArenasManager<A, P> {

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
		P arenaPlayer = getArenaPlayer(player);
		return arenaPlayer == null ? null : arenaPlayer.getArena();
	}

	@Override
	public A getArenaInWorld(World world) {
		return arenas.stream().filter(arena -> arena.getWorld() == world).findAny().orElse(null);
	}

	@Override
	public void cycleArena(A arena) {
		if (arenas.remove(arena)) {
			arenas.add(arena);
		}
	}

	@Override
	public P getArenaPlayer(Player player) {
		A arena = getArenaInWorld(player.getWorld());
		return arena == null ? null : arena.getArenaPlayer(player);
	}
}
