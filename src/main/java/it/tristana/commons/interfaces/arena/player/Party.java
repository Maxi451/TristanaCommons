package it.tristana.commons.interfaces.arena.player;

import java.util.List;

import org.bukkit.entity.Player;

/**
 * A party is a group of players with a leader<br>
 * that can join all together in an arena and,<br>
 * if supported, expect to be in the same team
 */
public interface Party {

	/**
	 * The party's leader is the only player that<br>
	 * can invite/kick other players in the party,<br>
	 * and the one who choose in which arena to play
	 * @return The party's leader
	 */
	Player getLeader();
	
	/**
	 * The returned list is a copy, so it is safe<br>
	 * for modification and iteration
	 * @return A copy of the list of the members<br>
	 * of this party, with the leader in it
	 */
	List<Player> getPlayers();
	
	/**
	 * Adds a player to this party. A player is expected<br>
	 * to be found in at most one single party at a time
	 * @param player The player to add
	 */
	void addPlayer(Player player);
	
	/**
	 * Removes the given player from the party. If the<br>
	 * player is the party's leader or it was not found<br>
	 * in the members list, then no action is executed
	 * @param player
	 */
	void removePlayer(Player player);
	
	/**
	 * Returns a copy of the list of players invited<br>
	 * in the party. If a player accepts the invite,<br>
	 * he is removed from this list and put in the<br>
	 * {@link #getPlayers()} list
	 * @return
	 */
	List<String> getInvites();
	
	/**
	 * Checks if the given player is invited to join this party
	 * @param player The player to check
	 * @return True if {@link #getInvites()} contains<br>
	 * the given player, false otherwise
	 */
	boolean hasInvited(Player player);
	
	/**
	 * Adds the given player to the {@link #getInvites()} list.<br>
	 * If the player was already invited or the {@link #getPlayers()}<br>
	 * list already contains him, then no action is executed
	 * @param player The player to add
	 */
	void invite(Player player);
	
	/**
	 * Checks if the method {@link #hasInvited(Player)} returns<br>
	 * True, and if so, then calls {@link #addPlayer(Player)} and<br>
	 * removes him from the {@link #getInvites()} list
	 * @param player The player to try to add
	 * @return True if the player was added to the party, false otherwise
	 */
	boolean tryToJoin(Player player);
}
