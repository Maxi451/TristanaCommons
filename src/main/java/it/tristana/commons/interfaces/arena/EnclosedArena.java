package it.tristana.commons.interfaces.arena;

import org.bukkit.util.Vector;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

/**
 * A specialization over a simple arena when borders are set
 * @param <P> The {@link ArenaPlayer} subclass that is used in this class
 */
public interface EnclosedArena<P extends ArenaPlayer<?>> extends Arena<P> {

	/**
	 * Returns a vector pointing to the lowest location for this arena
	 * @return The lowest point of this arena
	 */
	Vector getLowerPos();

	/**
	 * Returns a vector pointing to the highest location for this arena
	 * @return The highest point of this arena
	 */
	Vector getUpperPos();
	
	/**
	 * Sets the lowest point of this arena. If {@link #getUpperPos()} does not return<br>
	 * null, the two vectors are adjusted accordingly to what they represent
	 * @param vector The lowest point of this arena
	 */
	void setLowerPos(Vector vector);

	
	/**
	 * Sets the highest point of this arena. If {@link #getLowerPos()} does not return<br>
	 * null, the two vectors are adjusted accordingly to what they represent
	 * @param vector The highest point of this arena
	 */
	void setUpperPos(Vector vector);
}