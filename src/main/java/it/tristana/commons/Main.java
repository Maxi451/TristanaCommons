package it.tristana.commons;

import org.bukkit.plugin.java.JavaPlugin;

import it.tristana.commons.helper.CommonsHelper;

public class Main extends JavaPlugin {

	private final String version = "&6" + getDescription().getFullName();
	
	@Override
	public void onEnable() {
		CommonsHelper.consoleInfo(version + " &renabled! :)");
	}
	
	@Override
	public void onDisable() {
		CommonsHelper.consoleInfo(version + " &rdisabled! :(");
	}
}
