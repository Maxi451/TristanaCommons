package it.tristana.commons.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UserRetriever;
import it.tristana.commons.interfaces.database.UsersManager;

public class LoginQuitListener<U extends User> implements Listener {

	protected UsersManager<U> usersManager;
	protected UserRetriever<U> userRetriever;
	
	public LoginQuitListener(UsersManager<U> usersManager, UserRetriever<U> userRetriever) {
		this.usersManager = usersManager;
		this.userRetriever = userRetriever;
	}
	
	@EventHandler
	public void on(PlayerJoinEvent event) {
		U user = userRetriever.getUser(event.getPlayer());
		usersManager.addUser(user);
		onPlayerJoin(event, user);
	}
	
	@EventHandler
	public void on(PlayerQuitEvent event) {
		U user = usersManager.getUser(event.getPlayer());
		if (user == null) {
			return;
		}
		userRetriever.saveUser(user);
		onPlayerQuit(event, user);
	}

	protected void onPlayerJoin(PlayerJoinEvent event, U user) {}
	
	protected void onPlayerQuit(PlayerQuitEvent event, U user) {}
}
