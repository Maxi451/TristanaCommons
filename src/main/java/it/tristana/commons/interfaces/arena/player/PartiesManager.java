package it.tristana.commons.interfaces.arena.player;

import org.bukkit.entity.Player;

/**
 * A class specialized in managing the parties used by the players
 */
public interface PartiesManager {
	
	/**
	 * Creates a party where the given player is the leader. If the<br>
	 * player is already in another party this method returns {@code null}
	 * @param owner The new party's owner
	 * @return The newly created {@link Party} instance, or<br>
	 * {@code null} if the required conditions are not met
	 */
	Party createParty(Player owner);
	
	/**
	 * Removes the first party stored in this class<br>
	 * whose {@link Party#equals(Object)} call returns true
	 * @param party The party to disband
	 */
	void disbandParty(Party party);
	
	/**
	 * Retrieves the party to which the given player belongs
	 * @param player The player to look for
	 * @return The (unique) {@link Party} containing the given<br>
	 * player, or {@code null} if no party contains that player
	 */
	Party getPartyFromPlayer(Player player);
}