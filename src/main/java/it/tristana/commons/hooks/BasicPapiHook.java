package it.tristana.commons.hooks;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.database.User;
import it.tristana.commons.interfaces.database.UsersManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public abstract class BasicPapiHook<P extends Plugin, U extends User> extends PlaceholderExpansion {

	private P plugin;
	private UsersManager<U> usersManager;

	public BasicPapiHook(P plugin, UsersManager<U> usersManager) {
		this.plugin = plugin;
		this.usersManager = usersManager;
	}

	// PlaceholderAPI complains if this isn't put here, so here it is
	@Override
	public boolean register() {
		return super.register();
	}

	@Override
	public String getAuthor() {
		return CommonsHelper.playerListToString(plugin.getDescription().getAuthors(), "and", "nobody");
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public boolean persist() {
		return true;
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
