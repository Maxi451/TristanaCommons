package it.tristana.commons.arena.powerup;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;
import it.tristana.commons.interfaces.util.Powerup;

public abstract class BasicPowerup<P extends ArenaPlayer<?>> implements Powerup<P> {
	
	protected String name;
	protected int spawnChance;

	public BasicPowerup(String name, int spawnChance) {
		this.name = name;
		this.spawnChance = spawnChance;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getSpawnChance() {
		return spawnChance;
	}
}