package it.tristana.commons.interfaces.arena;

/**
 * Each {@link Arena} has a status that represents the current game phase
 */
public enum Status {

	/**
	 * The arena does not have enaugh players to start
	 */
	WAITING,
	
	/**
	 * The arena has enaugh players to start and is counting down to start
	 */
	STARTING,
	
	/**
	 * The arena is currently running
	 */
	PLAYING,
	
	/**
	 * The game is finished and it is about to close and reset
	 */
	ENDING,
	
	/**
	 * The arena is disabled and the player can not join it
	 */
	DISABLED
}
