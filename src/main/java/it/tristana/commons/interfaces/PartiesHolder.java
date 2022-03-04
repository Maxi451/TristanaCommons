package it.tristana.commons.interfaces;

import it.tristana.commons.interfaces.arena.player.PartiesManager;

/**
 * A {@link org.bukkit.plugin.java.JavaPlugin JavaPlugin} tagged with this interface supports a party system for its {@link it.tristana.commons.interfaces.arena.Arena Arena}s
 */
public interface PartiesHolder {

	/**
	 * Retrieves the {@link PartiesManager} singleton for this arena system
	 * @return The PartiesManager instance
	 */
	PartiesManager getPartiesManager();
}
