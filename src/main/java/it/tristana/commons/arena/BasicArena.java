package it.tristana.commons.arena;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import it.tristana.commons.helper.TeamsBuilder;
import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.Status;
import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Team;
import it.tristana.commons.interfaces.arena.player.Teamable;
import it.tristana.commons.interfaces.arena.player.TeamingPlayer;

public abstract class BasicArena<T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> implements Arena<P>, Teamable<T, P> {
	
	protected String name;
	protected World world;
	protected Location lobby;
	
	protected PartiesManager partiesManager;
	protected List<T> teams;
	protected List<Location> spawnpoints;
	protected List<P> players;
	protected List<Player> spectators;

	protected Status status;
	protected int maxPerTeam;
	protected int minPlayersToStart;
	protected int ticksToStart;
	protected int ticksToEnd;
	
	public BasicArena(World world, String name) {
		this(world, name, null);
	}
	
	public BasicArena(World world, String name, PartiesManager partiesManager) {
		this.world = world;
		this.name = name;
		this.partiesManager = partiesManager;
		spawnpoints = new ArrayList<Location>();
		players = new ArrayList<P>();
		spectators = new ArrayList<Player>();
		status = Status.WAITING;
		minPlayersToStart = 2;
		maxPerTeam = 4;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public Collection<T> getTeams() {
		return new ArrayList<>(teams);
	}

	@Override
	public T getTeam(Player player) {
		P arenaPlayer = getArenaPlayer(player);
		T team = null;
		if (arenaPlayer != null) {
			team = arenaPlayer.getTeam();
		}
		return team;
	}

	@Override
	public P getArenaPlayer(Player player) {
		int index = getArenaPlayerIndex(player);
		return index == -1 ? null : players.get(index);
	}

	@Override
	public int getMaxPerTeam() {
		return maxPerTeam;
	}

	@Override
	public void setMaxPerTeam(int maxPerTeam) {
		this.maxPerTeam = maxPerTeam;
	}

	@Override
	public int getMaxPlayers() {
		return spawnpoints.size() * maxPerTeam;
	}

	@Override
	public void addTeam(T team) {
		teams.add(team);
	}

	@Override
	public int getMinPlayersToStart() {
		return minPlayersToStart;
	}

	@Override
	public void setMinPlayersToStart(int minPlayersToStart) {
		this.minPlayersToStart = minPlayersToStart;
	}

	@Override
	public void startGame() {
		createTeams();
		teleportTeamsToSpawnpoints();
	}

	@Override
	public void closeArena() {
		teams = new ArrayList<T>();
		players = new ArrayList<P>();
		spectators = new ArrayList<Player>();
	}

	@Override
	public boolean setSpawnpoint(Location location) {
		return spawnpoints.add(location);
	}

	@Override
	public boolean areInSameTeam(Player p1, Player p2) {
		return getTeam(p1) == getTeam(p2);
	}
	
	@Override
	public void onPlayerLeave(Player player) {
		players.remove(getArenaPlayer(player));
	}

	@Override
	public boolean onSpectator(Player player) {
		boolean result = status == Status.PLAYING;
		if (result) {
			spectators.add(player);
		}
		return result;
	}

	@Override
	public Collection<P> getPlayers() {
		return new ArrayList<>(players);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasPlayer(Player player) {
		return getArenaPlayer(player) != null;
	}

	@Override
	public Location getLobby() {
		return lobby;
	}

	@Override
	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	@Override
	public void runTick() {
		switch (status) {
		case WAITING:
			waitingPhase();
			break;
		case STARTING:
			startingPhase();
			break;
		case PLAYING:
			playingPhase();
			break;
		case ENDING:
			endingPhase();
			break;
		case DISABLED:
			break;
		}
	}

	@Override
	public boolean onPlayerJoin(Player player) {
		boolean result = testPlayerJoin(player);
		if (result) {
			players.add(createArenaPlayer(player));
		}
		return result;
	}
	
	@Override
	public boolean testPlayerJoin(Player player) {
		return (status == Status.WAITING || status == Status.STARTING) && spawnpoints.size() >= 2 && players.size() < getMaxPlayers();
	}
	
	@Override
	public Collection<Player> getSpectators() {
		return new ArrayList<>(spectators);
	}
	
	@Override
	public List<Location> getSpawnpoints() {
		return new ArrayList<>(spawnpoints);
	}
	
	protected int getTeamsForNumPlayers(int players) {
		return spawnpoints.size();
	}
	
	protected void createTeams() {
		teams = new ArrayList<T>();
		int size = getTeamsForNumPlayers(players.size());
		for (int i = 0; i < size; i ++) {
			T team = createTeam(i);
			if (team != null) {
				teams.add(team);
			}
			else {
				break;
			}
		}
		TeamsBuilder.buildTeams(partiesManager, this, teams, players);
	}
	
	protected void teleportTeamsToSpawnpoints() {
		for (T team : teams) {
			for (P player : team.getPlayers()) {
				player.getPlayer().teleport(team.getSpawnpoint());
			}
		}
	}
	
	protected int getArenaPlayerIndex(Player player) {
		int index = -1;
		int size = players.size();
		for (int i = 0; i < size; i ++) {
			if (players.get(i).getPlayer() == player) {
				index = i;
				break;
			}
		}
		return index;
	}

	protected void waitingPhase() {
		if (players.size() >= getMinPlayersToStart()) {
			setStatus(Status.STARTING);
		}
	}

	protected void startingPhase() {
		if (players.size() < getMinPlayersToStart()) {
			setStatus(Status.WAITING);
		}
		for (P player : players) {
			player.getPlayer().setLevel(ticksToStart);
		}
		if (ticksToStart -- <= 0) {
			launchGame();
		}
	}

	protected void launchGame() {
		setStatus(Status.PLAYING);
		startGame();
	}
	
	protected void playingPhase() {}

	protected void endingPhase() {
		if (ticksToEnd -- <= 0) {
			closeArena();
		}
	}

	protected abstract P createArenaPlayer(Player player);
	
	protected abstract T createTeam(int index);
}