package it.tristana.commons.interfaces.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Element {

	ItemStack getDisplayItem(Player player);
	
	void onClick(Player player);
}
