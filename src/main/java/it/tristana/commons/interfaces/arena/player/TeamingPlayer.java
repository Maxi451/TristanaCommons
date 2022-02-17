package it.tristana.commons.interfaces.arena.player;

import it.tristana.commons.interfaces.arena.Arena;

public interface TeamingPlayer<T extends Team<?, A>, A extends Arena<?>> extends ArenaPlayer<A> {
	
	T getTeam();
	
	void setTeam(T team);
}
