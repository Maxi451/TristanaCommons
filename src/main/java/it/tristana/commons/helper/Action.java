package it.tristana.commons.helper;

import java.util.function.Supplier;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.arena.ArenasManager;

abstract class Action {

	protected Plugin plugin;
	protected ArenasManager<?> arenasManager;
	
	protected PlayersManager playersManager;
	protected Supplier<Location> mainLobbyProvider;
	
	Action(Plugin plugin, ArenasManager<?> arenasManager, Supplier<Location> mainLobbyProvider) {
		this.plugin = plugin;
		this.arenasManager = arenasManager;
		this.mainLobbyProvider = mainLobbyProvider;
	}
	
	protected PlayersManager getManager(Plugin plugin, ArenasManager<?> arenasManager) {
		if (playersManager == null) {
			playersManager = getPlayersManager(plugin, arenasManager);
		}
		return playersManager;
	}
	
	protected void resetPlayer(Player player) {
		getManager(plugin, arenasManager).resetPlayer(player, mainLobbyProvider == null ? null : mainLobbyProvider.get());
	}
	
	protected abstract PlayersManager getPlayersManager(Plugin plugin, ArenasManager<?> arenasManager);
}
