package it.tristana.commons.interfaces.gui;

import org.bukkit.entity.Player;

/**
 * A menu is a clickable {@link Element} that, if clicked,<br>
 * will open another gui for the player who clicked
 */
public interface Menu extends Element {

	/**
	 * Creates a new gui that will be displayed to the given player
	 * @param player The player that will see the gui
	 * @return A newly created gui for the given player
	 */
	Gui getGui(Player player);
}
