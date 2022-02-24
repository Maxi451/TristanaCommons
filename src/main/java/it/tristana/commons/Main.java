package it.tristana.commons;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.helper.PluginDraft;

/**
 * The main class of this library. It only logs that the plugins is enabled/disabled, nothing more
 * @author Massimiliano Micol
 * @since 21/02/2022
 */

public final class Main extends PluginDraft {

	/**
	 * The current version of this plugin
	 */
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
