package it.tristana.commons.interfaces.arena.player;

import it.tristana.commons.interfaces.arena.Arena;

/**
 * A TeamingPlayer is an {@link ArenaPlayer} that belongs to a team
 * @param <T> The {@link Team} subclass used in this class
 * @param <A> The {@link Arena} subclass used in this class
 */
public interface TeamingPlayer<T extends Team<?, A>, A extends Arena<?>> extends ArenaPlayer<A> {
	
	/**
	 * Retrieves the team for this player. May<br>
	 * return {@code null} if the game is not started
	 * @return The team this player belongs to
	 */
	T getTeam();
	
	/**
	 * Sets the team for this player
	 * @param team The team to set
	 */
	void setTeam(T team);
}
