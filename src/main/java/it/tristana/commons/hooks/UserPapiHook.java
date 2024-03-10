package it.tristana.commons.hooks;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UsersManager;

public abstract class UserPapiHook<U extends User> extends BasicPapiHook {

	private UsersManager<U> usersManager;

	public UserPapiHook(Plugin plugin, UsersManager<U> usersManager) {
		super(plugin);
		this.usersManager = usersManager;
	}

	@Override
	public String onPlaceholderRequest(final Player player, String identifier) {
		if (player == null) {
			return "*** PLAYER OFFLINE ***";
		}

		return parsePlaceholder(usersManager.getUser(player), identifier.toLowerCase());
	}

	public abstract String getIdentifier();
	
	protected abstract String parsePlaceholder(U user, String identifier);
}
