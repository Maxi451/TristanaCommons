package it.tristana.commons.interfaces.arena;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.Tickable;
import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

/**
 * <p>An Arena is the representation of a playable multiplayer map in which the<br>
 * players can join and compete with each other. An arena has a status, a lobby,<br>
 *a name, a per-arena world and a minimum required amount of players to start</p>
 * 
 * @param <P> The {@link ArenaPlayer} subclass that is used in this class
 */
public interface Arena<P extends ArenaPlayer<?>> extends Tickable {

	/**
	 * Performs the same actions of {@link #onPlayerJoin(Player)}, but if successful<br>
	 * the player will not be added to the underlying collection of players
	 * @param player The player to test
	 * @return True if the player can successfully join, false otherwise
	 */
	boolean testPlayerJoin(Player player);

	/**
	 * Called when a player tries to join this arena.<br>
	 * If successful, the player will be added to the<br>
	 * underlying collection of players of this arena
	 * @param player The player that tries to join
	 * @return True if the player successfully<br>
	 * joined and was added to the underlying<br>
	 * collection of players; false otherwise
	 */
	boolean onPlayerJoin(Player player);

	/**
	 * Called when a player leaves this<br>
	 * arena for any reason, for example<br>
	 * quitting or being eliminated
	 * @param player The player that left the arena
	 */
	void onPlayerLeave(Player player);

	/**
	 * Called when a player tries to spectate this arena<br>
	 * while its status is equal to {@link Status#PLAYING}.<br>
	 * If successful, the player will be added to the<br>
	 * underlying collection of spectators of this arena
	 * @param player The player that tries to spectate
	 * @return True if the status of the current game is<br>
	 * {@link Status#PLAYING} and the player is allowed<br>
	 * to spectate games, false otherwise
	 */
	boolean onSpectator(Player player);

	/**
	 * Retrieves a collection of the players currently playing<br>
	 * in this arena. This method will return the players'<br>
	 * list even if the game is not started yet. The returned<br>
	 * collection is a copy of the internal one, so it's safe<br>
	 * to be modified and iterated over
	 * @return A copy of the collection of {@link ArenaPlayer}s in this arena
	 */
	Collection<P> getPlayers();

	/**
	 * Retrieves a collection of the players currently<br>
	 * spectating this arena. If the arena does not<br>
	 * support spectators or its status is <u>not</u>
	 * {@link Status#PLAYING} the returned collection is empty.<br>
	 * The returned collection is a copy of the internal one,<br>
	 * so it's safe to be modified and iterated over
	 * @return A copy of the collection of spectators in this arena
	 */
	Collection<Player> getSpectators();

	/**
	 * Retrieves this arena's name. Each arena must have an unique<br>
	 * name, that may or may not be case sensitive depending on the<br>
	 * implementation of the {@link ArenasManager}
	 * @return The unique name of this arena
	 */
	String getName();

	/**
	 * Sets this arena's name. The caller must ensure that no other<br>
	 * arena has the same name, that may or may not be case sensitive<br>
	 * depending on the implementation of the {@link ArenasManager}
	 * @param name The new name for this arena
	 */
	void setName(String name);

	/**
	 * Checks if the given player is currently playing in this arena
	 * @param player The player to check
	 * @return True if the {@link ArenaPlayer} collection<br>
	 * contains this player, false otherwise
	 */
	boolean hasPlayer(Player player);

	/**
	 * Retrieves the spawning location for<br>
	 * the players when they join this arena
	 * @return This arena's lobby
	 */
	Location getLobby();

	/**
	 * Updates the arena's spawning point. Null values are not accepted
	 * @param lobby The new spawning location, complete with yaw and pitch
	 */
	void setLobby(Location lobby);

	/**
	 * Retrieves the number of times per second that this<br>
	 * arena's {@link #runTick() runTick} method is expected to be called
	 * @return The number of TPS for this arena
	 */
	int getTps();
	
	/**
	 * Retrieves the status of the current game. Players are only allowed to<br>
	 * join if the status is either {@link Status#WAITING} or {@link Status#STARTING}
	 * @return The current status
	 */
	Status getStatus();

	/**
	 * Updates the status of this arena. Should be used with caution, as it may stop<br>
	 * running games, thought developers should not depend on this behaviour
	 * @param status The new status
	 */
	void setStatus(Status status);

	/**
	 * Retrieves the {@link ArenaPlayer} object that contains the<br>
	 * given player, searching in the players collection
	 * @param player The player to search
	 * @return The {@link ArenaPlayer} instance that contains the<br>
	 * given player, or {@code null} otherwise
	 */
	P getArenaPlayer(Player player);

	/**
	 * Returns the minimum amount of players required to update the<br>
	 * status from {@link Status#WAITING} to {@link Status#STARTING}.<br>
	 * If the number of players is reduced while in the {@link Status#STARTING}<br>
	 * phase and becomes lower than the returned value, the status returns {@link Status#WAITING}
	 * @return The minimum amount of players required to start this game
	 */
	int getMinPlayersToStart();

	/**
	 * Updates the minimum amount of players required to start a game
	 * @see #getMinPlayersToStart()
	 * @param minPlayersToStart The minimum amount of players required
	 */
	void setMinPlayersToStart(int minPlayersToStart);

	/**
	 * Starts this game, updating the arena's status to {@link Status#PLAYING}.<br>
	 * If this method is called while the status is not {@link Status#WAITING}<br>
	 * or {@link Status#STARTING} then the result is undefined behaviour
	 */
	void startGame();

	/**
	 * Forcefully stops the current game and performs a reset of<br>
	 * its internal values, setting the status to {@link Status#WAITING}
	 */
	void closeArena();

	/**
	 * Retrieves the world instance associated to this arena.<br>
	 * Each arena must have its own world, so two arenas in<br>
	 * the same world are not allowed
	 * @return The world associated to this arena
	 */
	World getWorld();

	/**
	 * Checks if the game is ready to start
	 * @return True if the game can start, false otherwise
	 */
	boolean checkStartingConditions();

	/**
	 * Checks if the arena is running and players are playing
	 * @return {@code True} if the Arena is in {@link Status.PLAYING PLAYING} or<br>
	 * {@link Status.ENDING ENDING} phase, {@code False} otherwise
	 */
	default boolean isPlaying() {
		Status status = getStatus();
		return status == Status.PLAYING || status == Status.ENDING;
	}
}
