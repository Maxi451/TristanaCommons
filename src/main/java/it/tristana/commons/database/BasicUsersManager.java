package it.tristana.commons.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UserRetriever;
import it.tristana.commons.interfaces.database.UsersManager;

public class BasicUsersManager<U extends User> implements UsersManager<U> {

	protected UserRetriever<U> userRetriever;
	protected Set<U> users;
	
	public BasicUsersManager(UserRetriever<U> userRetriever) {
		this.userRetriever = userRetriever;
		users = new HashSet<>();
	}
	
	@Override
	public U getUser(Player player) {
		for (U user : users) {
			if (user.getPlayer() == player) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void addUser(Player player) {
		if (getUser(player) == null) {
			addUser(userRetriever.getUser(player));
		}
	}

	@Override
	public void addUser(U user) {
		users.add(user);
	}

	@Override
	public void removeUser(Player player) {
		removeUser(getUser(player));
	}

	@Override
	public void removeUser(U user) {
		if (user == null) {
			return;
		}
		users.remove(user);
	}

	@Override
	public Collection<U> getUsers() {
		return users;
	}

	@Override
	public void saveOnlineUsers() {
		users.forEach(user -> userRetriever.saveUser(user));
	}
}
