package it.tristana.commons.arena.player;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.player.Team;
import it.tristana.commons.interfaces.arena.player.TeamingPlayer;

public class BasicArenaPlayer<T extends Team<?, A>, A extends Arena<?>> implements TeamingPlayer<T, A> {
	
	protected final A arena;
	protected final Player player;
	
	protected T team;

	public BasicArenaPlayer(A arena, Player player) {
		this.arena = arena;
		this.player = player;
	}

	@Override
	public A getArena() {
		return arena;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public T getTeam() {
		return team;
	}

	@Override
	public void setTeam(T team) {
		this.team = team;
	}
}
