package it.tristana.commons.combat;

import java.util.Collection;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.Tickable;

/**
 * A system to monitor the combat between<br>
 * players to track killers and assistants
 */
public interface CombatManager extends Tickable {
	
	/**
	 * Called when the attacker hits the target
	 * @param attacker The player that hit the target
	 * @param target The player that got hit
	 */
	void onCt(Player attacker, Player target);
	
	/**
	 * Executes the given action on each player
	 * @param action The action that will be executed
	 */
	void forEach(Consumer<Player> action);
	
	/**
	 * Gets the remaining time for this player to exit the combat
	 * @param player The player to monitor
	 * @return The amount of time remaining, or 0 if there is not such player
	 */
	long getMillisToExitCt(Player player);
	
	/**
	 * Returns true if {@link #getMillisToExitCt(Player)}
	 * @param player
	 * @return
	 */
	boolean isInCt(Player player);
	
	/**
	 * Retrieves the last player that damaged the given player
	 * @param player The player that was damaged
	 * @return The last damaging player
	 */
	Player getLastAttacker(Player player);
	
	/**
	 * Retrieves a collection of players that contribuited to<br>
	 * damage the given player, <u>without</u> the last attacker
	 * @param player The player that was damaged
	 * @return The list of assisting players, or an empty<br>
	 * list if the given player is not present
	 */
	Collection<Player> getAssistPlayers(Player player);
	
	/**
	 * Removes the given player from the combat track manager
	 * @param player The player that will be removed
	 */
	void remove(Player player);
}
