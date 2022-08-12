package it.tristana.commons.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExitCombatEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();

	private Player player;
	
	public ExitCombatEvent(Player player) {
		this.player = player;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}
}
