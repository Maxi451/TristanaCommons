package it.tristana.commons.hooks;

import org.bukkit.plugin.Plugin;

import it.tristana.commons.helper.CommonsHelper;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public abstract class BasicPapiHook extends PlaceholderExpansion {

	private Plugin plugin;
	
	public BasicPapiHook(Plugin plugin) {
		this.plugin = plugin;
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
}
