package it.tristana.commons.interfaces.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickedGuiManager {

	void registerGui(Gui gui);

	void unregisterGui(String name);
	
	void unregisterGui(Gui gui);
	
	void clearGuis();
	
	boolean processClick(InventoryClickEvent event);
	
	boolean processClick(InventoryClickEvent event, boolean cancelEvent);
}
