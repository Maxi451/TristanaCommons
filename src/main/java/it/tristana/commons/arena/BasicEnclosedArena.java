package it.tristana.commons.arena;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import it.tristana.commons.interfaces.arena.EnclosedArena;
import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Team;
import it.tristana.commons.interfaces.arena.player.TeamingPlayer;

public abstract class BasicEnclosedArena<T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> extends BasicTeamableArena<T, P> implements EnclosedArena<P> {
	
	protected Vector lowerPos;
	protected Vector upperPos;

	public BasicEnclosedArena(World world, String name) {
		this(world, name, null);
	}
	
	public BasicEnclosedArena(World world, String name, PartiesManager partiesManager) {
		super(world, name, partiesManager);
	}
	
	protected static void correctExtremities(Vector lowerPos, Vector upperPos) {
		double tmp;
		if (lowerPos.getX() > upperPos.getX()) {
			tmp = lowerPos.getX();
			lowerPos.setX(upperPos.getX());
			upperPos.setX(tmp);
		}
		if (lowerPos.getY() > upperPos.getY()) {
			tmp = lowerPos.getY();
			lowerPos.setY(upperPos.getY());
			upperPos.setY(tmp);
		}
		if (lowerPos.getZ() > upperPos.getZ()) {
			tmp = lowerPos.getZ();
			lowerPos.setZ(upperPos.getZ());
			upperPos.setZ(tmp);
		}
	}

	@Override
	public Vector getLowerPos() {
		return lowerPos;
	}

	@Override
	public Vector getUpperPos() {
		return upperPos;
	}

	@Override
	public void setLowerPos(Vector vector) {
		this.lowerPos = vector;
		if (upperPos != null) {
			correctExtremities(lowerPos, upperPos);
		}
	}

	@Override
	public void setUpperPos(Vector vector) {
		this.upperPos = vector;
		if (lowerPos != null) {
			correctExtremities(lowerPos, upperPos);
		}
	}

	protected boolean isInsideBorders(Location location) {
		boolean result = location.getWorld() == world;
		if (result) {
			double x = location.getX();
			double y = location.getY();
			double z = location.getZ();
			result = x >= lowerPos.getX() && x <= upperPos.getX() && y >= lowerPos.getY() && y <= upperPos.getY() && z >= lowerPos.getZ() && z <= upperPos.getZ();
		}
		return result;
	}
}
