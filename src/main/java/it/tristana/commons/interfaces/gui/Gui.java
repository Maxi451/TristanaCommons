package it.tristana.commons.interfaces.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * A Gui is an abstract representation of an<br>
 * {@link Inventory} in which a player can see the<br>
 * available options and interact clicking its elements
 */
public interface Gui {

	/**
	 * Called when the given player clicks the specified slot.<br>
	 * The slot number goes from {@code 0} to {@link #getElements()}{@code .length - 1}
	 * @param player The player who clicked
	 * @param slot The slot's id that has been clicked
	 */
	void onClick(Player player, int slot);
	
	/**
	 * Displays this gui to the given player, opening an Inventory
	 * @param player The player that will see this gui
	 */
	void open(Player player);
	
	/**
	 * Closes this gui for the given player
	 * @param player The player whose gui will be closed
	 */
	void close(Player player);
	
	/**
	 * Updates the gui for the given player
	 * @param player THe player that will see this gui
	 */
	void update(Player player);
	
	/**
	 * Retrieves the {@link Element} in the specified gui's slot
	 * @param player The player associated to the gui
	 * @param id The gui's slot
	 * @return The Element in the specified id, or {@code null}<br>
	 * if this id id lower than {@code 0} or greater than<br>
	 * {@link #getElements()}{@code .length - 1}
	 */
	Element getById(Player player, int id);
	
	/**
	 * Retrieves a copy of the elements that this gui displays
	 * @return The elements array
	 */
	Element[] getElements(Player player);
	
	/**
	 * Creates an {@link Inventory} for the given<br>
	 * player that may be displayed to him
	 * @param player The player used to generate this inventory
	 * @return The newly created inventory
	 */
	Inventory getInventory(Player player);
	
	/**
	 * Each gui should have an unique name.<br>
	 * This name will also be used as inventory title
	 * @return The gui's name
	 */
	String getName();
}
