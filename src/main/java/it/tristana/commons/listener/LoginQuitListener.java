package it.tristana.commons.listener;

import java.util.function.BiConsumer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UserRetriever;
import it.tristana.commons.interfaces.database.UsersManager;

public final class LoginQuitListener<U extends User> implements Listener {

	private UsersManager<U> usersManager;
	private UserRetriever<U> userRetriever;
	private BiConsumer<PlayerJoinEvent, U> joinConsumer;
	private BiConsumer<PlayerQuitEvent, U> quitConsumer;
	
	public LoginQuitListener(UsersManager<U> usersManager, UserRetriever<U> userRetriever) {
		this(usersManager, userRetriever, null, null);
	}
	
	public LoginQuitListener(UsersManager<U> usersManager, UserRetriever<U> userRetriever, BiConsumer<PlayerJoinEvent, U> joinConsumer, BiConsumer<PlayerQuitEvent, U> quitConsumer) {
		this.usersManager = usersManager;
		this.userRetriever = userRetriever;
		this.joinConsumer = joinConsumer;
		this.quitConsumer = quitConsumer;
	}
	
	@EventHandler
	public void on(PlayerJoinEvent event) {
		U user = userRetriever.getUser(event.getPlayer());
		usersManager.addUser(user);
		if (joinConsumer != null) {
			joinConsumer.accept(event, user);
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
