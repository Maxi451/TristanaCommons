package it.tristana.commons.interfaces.arena.player;

import org.bukkit.entity.Player;

public interface PartiesManager {
	
	Party createParty(Player owner);
	
	void disbandParty(Party party);
	
	Party getPartyFromPlayer(Player player);
}