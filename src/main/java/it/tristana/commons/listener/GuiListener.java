package it.tristana.commons.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import it.tristana.commons.interfaces.gui.ClickedGuiManager;

public class GuiListener implements Listener {

	private ClickedGuiManager clickedGuiManager;
	
	public GuiListener(ClickedGuiManager clickedGuiManager) {
		this.clickedGuiManager = clickedGuiManager;
	}
	
	@EventHandler
	public void on(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) {
			return;
		}
		clickedGuiManager.processClick(event);
	}
}
