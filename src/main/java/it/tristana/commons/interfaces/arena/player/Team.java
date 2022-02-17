package it.tristana.commons.interfaces.arena.player;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.Arena;

public interface Team<P extends TeamingPlayer<?, A>, A extends Arena<P>> {

	A getArena();
	
	List<P> getPlayers();

	/**
	 * Must manually add<br>
	 * player.setTeam(this);<br>
	 * after this call
	 */
	
	void addPlayer(P player);
	
	void removePlayer(P player);
	
	String getName();
	
	String getColorCode();
	
	Location getSpawnpoint();
	
	void setSpawnpoint(Location location);
	
	boolean hasPlayer(Player player);
}
