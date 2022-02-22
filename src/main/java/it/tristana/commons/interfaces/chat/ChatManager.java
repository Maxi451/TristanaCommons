package it.tristana.commons.interfaces.chat;

import org.bukkit.entity.Player;

/**
 * A ChatManager, as the name implies, manages the server's chat
 */
public interface ChatManager {

	/**
	 * Called each time a player writes a message in chat
	 * @param player The message sender
	 * @param message What the player sent
	 */
	void onChat(Player player, String message);
}
