package it.tristana.commons;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

public class BungeeMain extends Plugin {

	private static final CommandSender console = ProxyServer.getInstance().getConsole();

	private final String version;

	public BungeeMain() {
		PluginDescription description = getDescription();
		version = description.getName() + "-" + description.getVersion();
	}

	@Override
	public void onEnable() {
		console.sendMessage(new TextComponent(version + " enabled! :)"));
	}

	@Override
	public void onDisable() {
		console.sendMessage(new TextComponent(version + " disabled! :("));
	}
}
