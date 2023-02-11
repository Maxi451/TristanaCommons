package it.tristana.commons.interfaces.arena;

import java.util.Collection;

import org.bukkit.Location;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.util.VillagerShop;

public interface ShopArena<P extends ArenaPlayer<?>, S extends VillagerShop> extends Arena<P> {

	Collection<S> getShops();

	void addShop(Location location);
	
	default void spawnShops() {
		getShops().forEach(VillagerShop::spawnEntity);
	}
	
	default void despawnShops() {
		getShops().forEach(VillagerShop::despawnEntity);
	}
}
