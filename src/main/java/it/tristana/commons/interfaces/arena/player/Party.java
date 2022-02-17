package it.tristana.commons.interfaces.arena.player;

import java.util.List;

import org.bukkit.entity.Player;

public interface Party {

	Player getLeader();
	
	List<Player> getPlayers();
	
	void addPlayer(Player player);
	
	void removePlayer(Player player);
	
	List<String> getInvites();
	
	boolean hasInvited(Player player);
	
	void invite(Player player);
	
	boolean tryToJoin(Player player);
}
