package it.tristana.commons.helper;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.arena.ArenasManager;
import it.tristana.commons.interfaces.database.User;

public abstract class QuitAction<U extends User> extends Action implements BiConsumer<PlayerQuitEvent, U> {

	public QuitAction(Plugin plugin, ArenasManager<?, ?> arenasManager) {
		this(plugin, arenasManager, null);
	}
	
	public QuitAction(Plugin plugin, ArenasManager<?, ?> arenasManager, Supplier<Location> mainLobbyProvider) {
		super(plugin, arenasManager, mainLobbyProvider);
	}
	
	@Override
	public void accept(PlayerQuitEvent event, U user) {
		resetPlayer(event.getPlayer());
	}
}
