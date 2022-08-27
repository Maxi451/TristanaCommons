package it.tristana.commons.interfaces.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * An Element is the root hierarchy of a {@link Gui}'s<br>
 * clickable items. It is represented by an {@link ItemStack}<br>
 * and can perform an action when it is clicked
 */
public interface Element {

	/**
	 * Retrieves a copy of the ItemStack that represents this Element
	 * @param player The player that will see this Element
	 * @return A copy of the ItemStack
	 */
	ItemStack getDisplayItem(Player player);
	
	/**
	 * Called when the specified player clicks this Element
	 * @param player The player who clicked
	 */
	void onClick(Player player);
	
	/**
	 * If this returns true, then the parent gui is closed when this element is clicked
	 * @param player The player who clicked
	 * @return True if this element should close the gui, false otherwise
	 */
	boolean closesInventory(Player player);
	
	/**
	 * Get the next menu that this element will open once clicked. May return<br>
	 * null, which means this element does not open another gui. This element<br>
	 * itself must take care to handle the next gui opening
	 * @param player The player who will see the next gui opened
	 * @return The class of the gui that will be displayed, or {@code null} if<br>
	 * this element does not open another gui for the specified player when clicked
	 */
	Class<? extends Gui> getNextMenu(Player player);
}
