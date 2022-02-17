package it.tristana.commons.arena.powerup;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.util.Powerup;

public class PowerupsManager<P extends ArenaPlayer<?>> {

	protected Powerup<P>[] powerups;
	protected int chances;

	public PowerupsManager(Powerup<P>[] powerups) {
		this.powerups = powerups;
		this.chances = getTotalChances(powerups);
	}

	public Powerup<P> getRandomPowerup() {
		return getRandomPowerup(powerups, chances);
	}

	protected static <P extends ArenaPlayer<?>> Powerup<P> getRandomPowerup(Powerup<P>[] powerups, int chances) {
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

	protected static <P extends ArenaPlayer<?>> int getTotalChances(Powerup<P>[] powerups) {
		int chances = 0;
		for (Powerup<P> powerup : powerups) {
			chances += powerup.getSpawnChance();
		}
		return chances;
	}
}