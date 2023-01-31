package it.tristana.commons.scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import it.tristana.commons.interfaces.arena.player.Team;
import it.tristana.commons.interfaces.arena.player.Teamable;
import it.tristana.commons.interfaces.database.User;

public abstract class TeamableScoreboard<U extends User, A extends Teamable<T, ?>, T extends Team<?, ?>> implements ScoreboardManager<U> {

	protected static final org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
	private static final int NO_TEAMS_INDEX = -1;

	protected final Collection<U> users;
	protected final Scoreboard scoreboard;
	protected final Objective objective;
	protected final A teamable;

	protected Score[] scores;
	protected int teamsIndex;

	public TeamableScoreboard(A teamable) {
		this.users = new HashSet<>();
		this.scoreboard = scoreboardManager.getNewScoreboard();
		this.teamable = teamable;
		this.objective = createObjective();
		this.scores = new Score[0];
		reload();
	}

	@Override
	public void runTick() {
		List<String> lines = new ArrayList<>(getScoreboardLines());
		int size = lines.size();

		for (int i = 0; i < size; i ++) {
			lines.set(i, parseLine(lines.get(i)));
		}

		if (teamsIndex != NO_TEAMS_INDEX) {
			List<String> teamsLines = new ArrayList<>();
			teamable.getTeams().forEach(team -> teamsLines.addAll(parseTeam(team)));
			lines.remove(teamsIndex);
			lines.addAll(teamsIndex, teamsLines);
		}

		recalcScores(lines);
	}

	@Override
	public void reload() {
		objective.setDisplayName(getScoreboardName());
		teamsIndex = getTeamsIndex();

		Collection<U> savedUsers = new HashSet<>(users);
		users.clear();
		for (Score score : scores) {
			scoreboard.resetScores(score.getEntry());
		}

		runTick();
		savedUsers.forEach(this::addUser);
	}

	@Override
	public void addUser(U user) {
		users.add(user);
		Player player = user.getOnlinePlayer();
		T team = teamable.getTeam(player);
		if (team != null) {
			getVanillaTeam(team).addEntry(player.getName());
		}
		player.setScoreboard(scoreboard);
	}

	@Override
	public void removeUser(U user) {
		users.remove(user);
		Player player = user.getOnlinePlayer();
		T team = teamable.getTeam(player);
		if (team != null) {
			getVanillaTeam(team).removeEntry(player.getName());
		}
		user.getOnlinePlayer().setScoreboard(scoreboardManager.getMainScoreboard());
	}

	@Override
	public Scoreboard getScoreboard(U user) {
		return scoreboard;
	}

	protected void recalcScores(List<String> lines) {
		if (lines.size() == scores.length) {
			int idx = 0;
			for (String line : lines) {
				String entry = scores[idx].getEntry();
				if (!entry.equals(line)) {
					scoreboard.resetScores(entry);
					scores[idx] = ScoreboardManager.registerScore(objective, line, idx);
				}
				idx ++;
			}
			return;
		}

		for (Score score : scores) {
			scoreboard.resetScores(score.getEntry());
		}

		scores = new Score[lines.size()];
		int idx = 0;
		for (String line : lines) {
			scores[idx] = ScoreboardManager.registerScore(objective, line, idx);
			idx ++;
		}
	}

	protected org.bukkit.scoreboard.Team getVanillaTeam(T team) {
		String teamName = team.getName();
		org.bukkit.scoreboard.Team vanillaTeam = scoreboard.getTeam(teamName);
		if (vanillaTeam == null) {
			vanillaTeam = scoreboard.registerNewTeam(teamName);
			setTeamColor(vanillaTeam, team.getColor());
		}
		return vanillaTeam;
	}

	private int getTeamsIndex() {
		int idx = 0;
		for (String line : getScoreboardLines()) {
			if (line.equals(getTeamsPlaceholder())) {
				return idx;
			}
			idx ++;
		}
		return NO_TEAMS_INDEX;
	}

	protected abstract String getTeamsPlaceholder();

	protected abstract String getScoreboardName();

	protected abstract List<String> getScoreboardLines();

	protected abstract void setTeamColor(org.bukkit.scoreboard.Team team, Color color);

	protected abstract String parseLine(String line);

	protected abstract Objective createObjective();

	protected abstract List<String> parseTeam(T team);
}
