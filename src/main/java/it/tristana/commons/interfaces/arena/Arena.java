package it.tristana.commons.interfaces.arena;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.Tickable;
import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.util.Status;

public interface Arena<P extends ArenaPlayer<?>> extends Tickable {

	boolean onPlayerJoin(Player player);
	
	void onPlayerLeave(Player player);
	
	boolean onSpectator(Player player);
	
	Collection<P> getPlayers();
	
	String getName();
	
	void setName(String name);
	
	boolean hasPlayer(Player player);
	
	Location getLobby();
	
	void setLobby(Location lobby);
	
	Status getStatus();
	
	void setStatus(Status status);
	
	P getArenaPlayer(Player player);
	
	int getMinPlayersToStart();
	
	void setMinPlayersToStart(int minPlayersToStart);
	
	void startGame();
	
	void closeArena();
	
	World getWorld();
	
	boolean testPlayerJoin(Player player);
}
