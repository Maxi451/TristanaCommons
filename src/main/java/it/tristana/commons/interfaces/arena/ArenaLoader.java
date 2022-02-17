package it.tristana.commons.interfaces.arena;

import java.util.Collection;

public interface ArenaLoader<A extends Arena<?>> {

	static final String ARENAS = "arenas";
	static final String LOBBY = "lobby";
	static final String MIN_TO_START = "min-to-start";
	
	static final String WORLD = "world";
	static final String X = "x";
	static final String Y = "y";
	static final String Z = "z";
	static final String YAW = "yaw";
	static final String PITCH = "pitch";

	void saveArena(A arena);
	
	A loadArena(String name);

	void saveArenas(Collection<A> arenas);
	
	Collection<A> loadArenas();
	
	String getRoot(String name);
}
