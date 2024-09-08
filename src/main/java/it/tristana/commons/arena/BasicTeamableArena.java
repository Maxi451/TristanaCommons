package it.tristana.commons.arena;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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

public abstract class BasicTeamableArena<T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> implements Arena<P>, Teamable<T, P> {

	protected final Supplier<? extends Location> mainLobbySupplier;
	protected final World world;
	protected String name;
	protected Location lobby;
	protected Map<Status, Runnable> actions;

	protected final PartiesManager partiesManager;
	protected final List<Location> spawnpoints;
	protected List<T> teams;
	protected List<P> players;
	protected List<Player> spectators;

	protected Status status;
	protected int maxPerTeam;
	protected int minPlayersToStart;
	protected int ticksToStart;
	protected int ticksToEnd;
	protected int currentTick;

	public BasicTeamableArena(Supplier<? extends Location> mainLobbySupplier, World world, String name, int minPlayersToStart, int maxPerTeam) {
		this(mainLobbySupplier, world, name, null, minPlayersToStart, maxPerTeam);
	}

	public BasicTeamableArena(Supplier<? extends Location> mainLobbySupplier, World world, String name, PartiesManager partiesManager, int minPlayersToStart, int maxPerTeam) {
		this.mainLobbySupplier = mainLobbySupplier;
		this.world = world;
		this.name = name;
		this.partiesManager = partiesManager;
		this.minPlayersToStart = minPlayersToStart;
		this.maxPerTeam = maxPerTeam;
		this.actions = new HashMap<>();
		this.actions.put(Status.WAITING, this::onWaiting);
		this.actions.put(Status.STARTING, this::onStarting);
		this.actions.put(Status.PLAYING, this::onPlaying);
		this.actions.put(Status.ENDING, this::onEnding);
		this.spawnpoints = new ArrayList<>();
		reset();
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
		return teams == null ? null : new ArrayList<>(teams);
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
		reset();
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
	public boolean onSpectator(Player player) {
		boolean result = status == Status.PLAYING;
		if (result) {
			spectators.add(player);
		}
		return result;
	}

	@Override
	public Collection<P> getPlayers() {
		return players;
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
		if (status == Status.DISABLED) {
			return;
		}

		actions.get(status).run();
	}

	@Override
	public boolean onPlayerJoin(Player player) {
		boolean result = testPlayerJoin(player);
		if (result) {
			player.teleport(lobby);
			players.add(createArenaPlayer(player));
		}
		return result;
	}

	@Override
	public boolean testPlayerJoin(Player player) {
		return lobby != null && mainLobbySupplier.get() != null && (status == Status.WAITING || status == Status.STARTING) && spawnpoints.size() >= 2 && players.size() < getMaxPlayers();
	}

	@Override
	public Collection<Player> getSpectators() {
		return spectators;
	}

	@Override
	public List<Location> getSpawnpoints() {
		return spawnpoints;
	}

	@Override
	public boolean checkStartingConditions() {
		return players.size() >= getMinPlayersToStart();
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

	protected void onWaiting() {
		if (checkStartingConditions()) {
			setStatus(Status.STARTING);
		}
	}

	protected void onStarting() {
		if (players.size() < getMinPlayersToStart()) {
			setStatus(Status.WAITING);
			return;
		}

		for (P arenaPlayer : players) {
			Player player = arenaPlayer.getPlayer();
			player.setLevel(ticksToStart);
		}

		if (ticksToStart -- <= 0) {
			setStatus(Status.PLAYING);
		}
	}
	
	protected void onPlaying() {
		currentTick ++;
	}

	protected void onEnding() {
		if (ticksToEnd -- <= 0) {
			closeArena();
		}
	}

	protected void reset() {
		teams = new ArrayList<>();
		players = new ArrayList<>();
		spectators = new ArrayList<>();
		setStatus(Status.WAITING);
		ticksToStart = getConfigTicksToStart();
		ticksToEnd = getConfigTicksToEnd();
		currentTick = 0;
	}

	protected abstract int getConfigTicksToStart();
	
	protected abstract int getConfigTicksToEnd();

	protected abstract P createArenaPlayer(Player player);

	protected abstract T createTeam(int index);
}