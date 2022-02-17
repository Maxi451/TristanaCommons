package it.tristana.commons.interfaces.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Gui {

	void onClick(Player player, int slot);
	
	void open(Player player);
	
	void update(Player player);
	
	Element getById(int id);
	
	Element[] getElements();
	
	Inventory getInventory(Player player);
	
	String getName();
}
