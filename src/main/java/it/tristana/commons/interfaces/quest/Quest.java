package it.tristana.commons.interfaces.quest;

import org.bukkit.inventory.ItemStack;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

public interface Quest<P extends ArenaPlayer<?>> {

	P getPlayer();
	
	String getName();
	
	String getDescription();
	
	ItemStack getDisplayItem();
}
