package it.tristana.commons.interfaces.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A ClickedGuiManager handles the user<br>
 * event when a {@link Gui} gets clicked.<br>
 * Usually a {@link org.bukkit.event.Listener Listener} that intercepts<br>
 * {@link org.bukkit.event.inventory.InventoryClickEvent InventoryClickEvent}s will have<br>
 * a reference to an instance of this interface
 */
public interface ClickedGuiManager {

	/**
	 * Registers this gui to be managed by this class
	 * @param gui The gui to register
	 */
	void registerGui(Gui gui);

	/**
	 * Removes the first gui with this name from the registered guis
	 * @param gui The gui to unregister
	 */
	void unregisterGui(String name);

	/**
	 * Removes this gui from the registered guis
	 * @param gui The gui to unregister
	 */
	void unregisterGui(Gui gui);
	
	/**
	 * Removes all the registered guis
	 */
	void clearGuis();
	
	/**
	 * Called when a player clicks on an inventory and some action has to be performed.<br>
	 * If the method returns true the event is marked as cancelled
	 * @param event The fired event
	 * @return True if this gui managed the click, false otherwise
	 */
	boolean processClick(InventoryClickEvent event);
	
	/**
	 * Called when a player clicks on an inventory<br>
	 * and some action has to be performed. If the<br>
	 * method returns true and the cancelEvent parameter<br>
	 * is set to true, then the event is marked as cancelled
	 * @param event The fired event
	 * @param cancelEvent If set to true, then if the method<br>
	 * returns true the event is flagged as cancelled
	 * @return True if this gui managed the click, false otherwise
	 */
	boolean processClick(InventoryClickEvent event, boolean cancelEvent);
}
