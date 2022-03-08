package it.tristana.commons.helper;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.arena.ArenasManager;
import it.tristana.commons.interfaces.database.User;

public abstract class LoginAction<U extends User> implements BiConsumer<PlayerJoinEvent, U> {

	private Plugin plugin;
	private ArenasManager<?> arenasManager;
	
	protected PlayersManager playersManager;
	protected Supplier<Location> mainLobbyProvider;
	
	public LoginAction(Plugin plugin, ArenasManager<?> arenasManager) {
		this(plugin, arenasManager, null);
	}
	
	public LoginAction(Plugin plugin, ArenasManager<?> arenasManager, Supplier<Location> mainLobbyProvider) {
		this.plugin = plugin;
		this.arenasManager = arenasManager;
		this.mainLobbyProvider = mainLobbyProvider;
	}
	
	@Override
	public void accept(PlayerJoinEvent event, U user) {
		getManager(plugin, arenasManager).resetPlayer(event.getPlayer(), mainLobbyProvider == null ? null : mainLobbyProvider.get());
	}
	
	private PlayersManager getManager(Plugin plugin, ArenasManager<?> arenasManager) {
		if (playersManager == null) {
			playersManager = getPlayersManager(plugin, arenasManager);
		}
		return playersManager;
	}
	
	protected abstract PlayersManager getPlayersManager(Plugin plugin, ArenasManager<?> arenasManager);
}
