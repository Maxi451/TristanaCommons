package it.tristana.commons.interfaces.arena;

import java.util.Collection;

/**
 * An ArenaLoader is used to save and load<br>
 * the arena configuration from its saving files
 * @param <A> The {@link Arena} subclass that is used in this class
 */
public interface ArenaLoader<A extends Arena<?>> {

	/**
	 * The root tag for the collection of arenas to load
	 */
	static final String ARENAS = "arenas";
	
	/**
	 * The tag for the current arena's lobby
	 * @see Arena#getLobby()
	 */
	static final String LOBBY = "lobby";
	
	/**
	 * The tag for the current arena's min players to start
	 * @see {@link Arena#getMinPlayersToStart()}
	 */
	static final String MIN_TO_START = "min-to-start";
	
	/**
	 * The world tag used when saving a location
	 */
	static final String WORLD = "world";
	
	/**
	 * The x coordinate tag used when saving a location
	 */
	static final String X = "x";
	
	/**
	 * The y coordinate tag used when saving a location
	 */
	static final String Y = "y";
	
	/**
	 * The z coordinate tag used when saving a location
	 */
	static final String Z = "z";
	
	/**
	 * The yaw tag used when saving a location
	 */
	static final String YAW = "yaw";
	
	/**
	 * The pitch coordinate tag used when saving a location
	 */
	static final String PITCH = "pitch";

	/**
	 * Saves the given arena's configuration to file
	 * @param arena The arena to save
	 */
	void saveArena(A arena);
	
	/**
	 * Loads an arena from disk
	 * @param name The name of the arena to load
	 * @return The arena that holds that name,<br>
	 * or {@code null} if it was not found
	 */
	A loadArena(String name);

	/**
	 * Saves a collection of arenas to file
	 * @see #saveArena(Arena)
	 * @param arenas The collection of the arenas
	 */
	void saveArenas(Collection<A> arenas);
	
	/**
	 * Loads all the arenas that can be loaded by this ArenaLoader
	 * @return The collection of loaded arenas
	 */
	Collection<A> loadArenas();
	
	/**
	 * Utility method to retrieve the root path of the given arena's name
	 * @param name The arena's name
	 * @return A path to the root of this arena's configuration,<br>
	 * build as follows: {@link #ARENAS} + "." + name
	 */
	String getRoot(String name);
}
