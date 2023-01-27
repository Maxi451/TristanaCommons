package it.tristana.commons.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.player.PartiesManager;
import it.tristana.commons.interfaces.arena.player.Party;
import it.tristana.commons.interfaces.arena.player.Team;
import it.tristana.commons.interfaces.arena.player.Teamable;
import it.tristana.commons.interfaces.arena.player.TeamingPlayer;

public class TeamsBuilder {

	private TeamsBuilder() {}

	public static <T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> void buildTeams(PartiesManager partiesManager, Teamable<T, P> arena, List<T> teams, List<P> originalPlayers) {
		List<P> players = new ArrayList<>(originalPlayers);
		CommonsHelper.shuffle(players);
		if (partiesManager != null) {
			addPartiesToTeams(arena, teams, getPartiesInArena(partiesManager, players), players);
		}
		int maxPerTeam = arena.getMaxPerTeam();
		addPlayersLeft(players, teams, maxPerTeam);
		removeExceedingPlayers(teams, players, originalPlayers.size());
		addPlayersLeft(players, teams, maxPerTeam);
	}

	private static <T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> void addPlayersLeft(Collection<P> players, List<T> teams, int maxPerTeam) {
		Iterator<P> iterator = players.iterator();
		while (iterator.hasNext()) {
			T team = getTeamWithMinimumPlayers(teams, maxPerTeam);
			if (team == null) {
				team = teams.get(0);
			}
			P next = iterator.next();
			team.addPlayer(next);
			next.setTeam(team);
			iterator.remove();
		}
	}

	private static <T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> void removeExceedingPlayers(List<T> teams, List<P> players, int totalPlayers) {
		int teamsSize = teams.size();
		int minPerTeam = totalPlayers / teamsSize;
		int teamsWithAnotherPlayer = totalPlayers % teamsSize;
		for (int i = 0; i < teamsSize; i ++) {
			T team = teams.get(i);
			List<P> teamPlayers = team.getPlayers();
			while (teamPlayers.size() > minPerTeam + (i < teamsWithAnotherPlayer ? 1 : 0)) {
				players.add(teamPlayers.remove(teamPlayers.size() - 1));
			}
		}
	}

	private static <T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> T getTeamWithMinimumPlayers(Collection<T> teams, int maxNumberOfPlayers) {
		T result = null;
		int minPlayers = maxNumberOfPlayers + 1;
		for (T team : teams) {
			int playersInTeam = team.getPlayers().size();
			if (playersInTeam < minPlayers) {
				minPlayers = playersInTeam;
				result = team;
			}
		}
		return result;
	}

	private static <T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> void addPartiesToTeams(Teamable<T, P> arena, Collection<T> teams, Collection<Party> parties, Collection<P> players) {
		parties.forEach(party -> {
			List<Player> partyPlayers = new ArrayList<>(party.getPlayers());
			partyPlayers.add(party.getLeader());
			T team = getTeamWithMinimumPlayers(teams, arena.getMaxPerTeam() - partyPlayers.size());

			if (team == null) {
				return;
			}

			CommonsHelper.shuffle(partyPlayers);
			partyPlayers.forEach(partyPlayer -> {
				P arenaPlayer = arena.getArenaPlayer(partyPlayer);

				if (arenaPlayer == null) {
					return;
				}

				team.addPlayer(arenaPlayer);
				players.remove(arenaPlayer);
				arenaPlayer.setTeam(team);
			});
		});
	}

	private static <T extends Team<P, ?>, P extends TeamingPlayer<T, ?>> List<Party> getPartiesInArena(PartiesManager partiesManager, Collection<P> players) {
		List<Party> partiesInArena = new ArrayList<>();
		players.forEach(player -> {
			Party party = partiesManager.getPartyFromPlayer(player.getPlayer());
			if (party != null && !partiesInArena.contains(party)) {
				partiesInArena.add(party);
			}
		});
		return partiesInArena;
	}
}
