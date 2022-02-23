package it.tristana.commons.arena.powerup;

import java.util.Arrays;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.util.Powerup;
import it.tristana.commons.interfaces.util.PowerupsManager;

public class BasicPowerupsManager<P extends ArenaPlayer<?>> implements PowerupsManager<P> {

	protected Powerup<P>[] powerups;
	protected int chances;

	public BasicPowerupsManager(Powerup<P>[] powerups) {
		this.powerups = Arrays.copyOf(powerups, powerups.length);
		this.chances = PowerupsManager.getTotalChances(powerups);
	}

	@Override
	public Powerup<P> getRandomPowerup() {
		return PowerupsManager.getRandomPowerup(powerups, chances);
	}
}