package it.tristana.commons.helper;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.arena.ArenasManager;
import it.tristana.commons.interfaces.database.User;

public abstract class LoginAction<U extends User> extends Action implements BiConsumer<PlayerJoinEvent, U> {
	
	public LoginAction(Plugin plugin, ArenasManager<?, ?> arenasManager) {
		this(plugin, arenasManager, null);
	}
	
	public LoginAction(Plugin plugin, ArenasManager<?, ?> arenasManager, Supplier<Location> mainLobbyProvider) {
		super(plugin, arenasManager, mainLobbyProvider);
	}
	
	@Override
	public void accept(PlayerJoinEvent event, U user) {
		resetPlayer(event.getPlayer());
	}
}
