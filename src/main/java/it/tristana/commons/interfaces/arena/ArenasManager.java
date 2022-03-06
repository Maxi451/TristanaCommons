package it.tristana.commons.interfaces.arena;

import java.util.Collection;

import org.bukkit.World;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.util.TickablesManager;

/**
 * An ArenasManager is used to manage a collection of arenas<br>
 * ensuring no arena has the same name or world as another<br>
 * and to look for the first available arena for a player
 * @param <A> The {@link Arena} subclass that is used in this class
 */
public interface ArenasManager<A extends Arena<?>> extends TickablesManager {

	/**
	 * Retrieves a copy of the arenas stored by<br>
	 * this ArenasManager instance. The collection<br>
	 * is copied, so it is safe to edit or iterate over it
	 * @return
	 */
	Collection<A> getArenas();
	
	/**
	 * Looks for an arena with the given name<br>
	 * and if found returns the (unique) result.<br>
	 * Depending on the implementation, the<br>
	 * lookup may or may not be case sensitive
	 * @param name The arena's name
	 * @return The arena instance with this name,<br>
	 * or {@code null} if no arena had this name
	 */
	A getArena(String name);
	
	/**
	 * Tries to add an arena to this ArenasManager, that will be<br>
	 * added if and only if it is not already contained, there is<br>
	 * not another arena with the same name or the same world and<br>
	 * the underlying collection returned true on the {@link Collection#add(Object)} call
	 * @param arena The arena to add
	 * @return True if the arena was successfully added, false otherwise
	 */
	boolean addArena(A arena);
	
	/**
	 * Removes the first arena that is equal to the given one,<br>
	 * returning a value according to {@link Collection#remove(Object)} call
	 * @param arena The arena to be removed
	 * @return True if the arena was successfully removed, false otherwise
	 */
	boolean removeArena(A arena);
	
	/**
	 * Iterates over the arena's collection performing on each one<br>
	 * a {@link Arena#testPlayerJoin(Player)} call, returning the<br>
	 * first one whose method returned true
	 * @param player The player to add
	 * @return An arena instance if at least one arena<br>
	 * returned true on its call, {@code null} otherwise
	 */
	A getFirstArenaAvailable(Player player);
	
	/**
	 * Retrieves the (unique) arena that contains the given player
	 * @param player The player to look for
	 * @return The first (and unique) arena that contains him,<br>
	 * or {@code null} if no registered arena contains him
	 */
	A getArenaWithPlayer(Player player);
	
	/**
	 * Retrieves the (unique) arena that is located in the given world
	 * @param world The world that may contain an arena
	 * @return The arena located in this world, or {@code null} if no arena is located this world
	 */
	A getArenaInWorld(World world);
	
	/**
	 * Moves this arena to the end of the underlying
	 * <br>collection, so that {@link #getFirstArenaAvailable(Player)}
	 * <br>will check all the other arenas first
	 * @param arena The arena to move to the end of the underlying collection
	 */
	void cycleArena(A arena);
}
