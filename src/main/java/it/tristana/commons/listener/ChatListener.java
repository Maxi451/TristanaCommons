package it.tristana.commons.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import it.tristana.commons.interfaces.chat.ChatManager;

public class ChatListener implements Listener {

	private ChatManager chat;
	
	public ChatListener(ChatManager chat) {
		this.chat = chat;
	}
	
	@EventHandler
	public void on(AsyncPlayerChatEvent event) {
		chat.onChat(event.getPlayer(), event.getMessage());
		event.setCancelled(true);
	}
}