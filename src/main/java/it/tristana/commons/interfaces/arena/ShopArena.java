package it.tristana.commons.interfaces.arena;

import java.util.Collection;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.util.VillagerShop;

public interface ShopArena<P extends ArenaPlayer<?>, S extends VillagerShop> extends Arena<P> {

	Collection<S> getShops();

	void addShop(S shop);
}
