package it.tristana.commons.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import it.tristana.commons.interfaces.gui.ClickedGuiManager;
import it.tristana.commons.interfaces.gui.Gui;

public abstract class BasicClickedGuiManager implements ClickedGuiManager {

	protected Map<String, Gui> registeredGuis;
	
	public BasicClickedGuiManager() {
		registeredGuis = new HashMap<>();
	}
	
	@Override
	public void registerGui(Gui gui) {
		String name = gui.getName();
		if (!registeredGuis.containsKey(name)) {
			registeredGuis.put(name, gui);
		}
	}

	@Override
	public void unregisterGui(String name) {
		registeredGuis.remove(name);	
	}

	@Override
	public void unregisterGui(Gui gui) {
		unregisterGui(gui.getName());
	}

	@Override
	public void clearGuis() {
		registeredGuis.clear();
	}
	
	@Override
	public boolean processClick(InventoryClickEvent event) {
		return processClick(event, true);
	}

	@Override
	public boolean processClick(InventoryClickEvent event, boolean cancelEvent) {
		Inventory inventory = event.getClickedInventory();
		if (inventory.getType() == InventoryType.PLAYER) {
			return false;
		}
		HumanEntity entity = event.getWhoClicked();
		if (!(entity instanceof Player)) {
			return false;
		}
		String name = getInventoryName(event);
		for (Entry<String, Gui> entry : registeredGuis.entrySet()) {
			if (entry.getKey().equals(name)) {
				if (cancelEvent) {
					event.setCancelled(true);
				}
				entry.getValue().onClick((Player) entity, event.getSlot());
				return true;
			}
		}
		return false;
	}
	
	protected abstract String getInventoryName(InventoryEvent event);
}
