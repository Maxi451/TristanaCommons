package it.tristana.commons.listener;

import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UserRetriever;
import it.tristana.commons.interfaces.database.UsersManager;

public final class LoginQuitListener<U extends User> implements Listener {

	private UsersManager<U> usersManager;
	private UserRetriever<U> userRetriever;
	private Plugin plugin;
	private BiConsumer<PlayerJoinEvent, U> joinConsumer;
	private BiConsumer<PlayerQuitEvent, U> quitConsumer;

	public LoginQuitListener(UsersManager<U> usersManager, UserRetriever<U> userRetriever) {
		this(usersManager, userRetriever, null, null, null);
	}

	public LoginQuitListener(UsersManager<U> usersManager, UserRetriever<U> userRetriever, Plugin plugin, BiConsumer<PlayerJoinEvent, U> joinConsumer, BiConsumer<PlayerQuitEvent, U> quitConsumer) {
		this.usersManager = usersManager;
		this.userRetriever = userRetriever;
		this.plugin = plugin;
		this.joinConsumer = joinConsumer;
		this.quitConsumer = quitConsumer;
	}

	@EventHandler
	public void on(PlayerJoinEvent event) {
		U user = userRetriever.getUser(event.getPlayer());
		usersManager.addUser(user);
		if (joinConsumer != null) {
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				joinConsumer.accept(event, user);
			}, 1);
		}
	}

	@EventHandler
	public void on(PlayerQuitEvent event) {
		U user = usersManager.getUser(event.getPlayer());
		if (user == null) {
			return;
		}

		if (quitConsumer != null) {
			quitConsumer.accept(event, user);
		}

		userRetriever.saveUser(user);
	}
}
