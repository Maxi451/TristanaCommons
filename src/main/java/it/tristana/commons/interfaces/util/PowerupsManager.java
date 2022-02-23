package it.tristana.commons.interfaces.util;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

/**
 * A PowerupsManager is in charge of selecting a random {@link Powerup} when queried to do so
 * @param <P> The {@link ArenaPlayer} subclass used in this class
 */
public interface PowerupsManager<P extends ArenaPlayer<?>> {

	/**
	 * Randomly chooses a powerup. The chances of all<br>
	 * the powerups will be added together and a random<br>
	 * integer between 0 and this sum will be chosen
	 * @return The powerup that has been chosen
	 */
	Powerup<P> getRandomPowerup();

	/**
	 * A simple implementation of the algorithm specified by {@link #getRandomPowerup()}
	 * @param <P> The {@link ArenaPlayer} subclass used in this method
	 * @param powerups An array of powerups from which to choose one
	 * @param chances The pre-computed total amount of chances for these powerups
	 * @return A randomly chosen powerup, or {@code null} if the specified number<br>
	 * of chances was greater than the sum of all the powerups chances
	 */
	static <P extends ArenaPlayer<?>> Powerup<P> getRandomPowerup(Powerup<P>[] powerups, int chances) {
		int extracted = (int) (Math.random() * chances);
		Powerup<P> chosed = null;
		for (Powerup<P> powerup : powerups) {
			extracted -= powerup.getSpawnChance();
			if (extracted <= 0) {
				chosed = powerup;
				break;
			}
		}
		return chosed;
	}

	/**
	 * Retrieves the sum of all the chances for the given powerups array
	 * @param powerups The powerups array
	 * @return The sum of the chances
	 */
	static int getTotalChances(Powerup<?>[] powerups) {
		int chances = 0;
		for (Powerup<?> powerup : powerups) {
			chances += powerup.getSpawnChance();
		}
		return chances;
	}
}
