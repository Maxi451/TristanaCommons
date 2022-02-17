package it.tristana.commons.interfaces.arena;

import org.bukkit.util.Vector;

import it.tristana.commons.interfaces.arena.player.ArenaPlayer;

public interface EnclosedArena<P extends ArenaPlayer<?>> extends Arena<P> {

	Vector getLowerPos();
	
	Vector getUpperPos();
	
	void setLowerPos(Vector vector);
	
	void setUpperPos(Vector vector);
}