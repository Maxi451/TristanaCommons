package it.tristana.commons.scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import it.tristana.commons.interfaces.database.User;

public abstract class BasicScoreboardManager<U extends User, S extends SettingsScoreboard<? extends ConfigScoreboard>> implements PersonalScoreboardManager<U> {

	protected S settings;

	protected Map<U, Scoreboard> users;
	protected Map<Objective, Score[]> objectivesScores;

	public BasicScoreboardManager(S settings) {
		this.settings = settings;
		users = new HashMap<>();
		objectivesScores = new HashMap<>();
	}

	@Override
	public void runTick() {
		users.forEach(this::updateScoreboard);
	}

	@Override
	public void reload() {
		Collection<U> savedUsers = new HashSet<>(users.keySet());
		users.clear();
		objectivesScores.clear();
		savedUsers.forEach(this::addUser);
	}

	@Override
	public void addUser(U user) {
		Player player = user.getPlayer().getPlayer();
		if (player == null) {
			throw new IllegalArgumentException("The given user does not have an online player associated");
		}
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		createObjectives(user, scoreboard);
		users.put(user, scoreboard);
		player.setScoreboard(scoreboard);
	}

	@Override
	public void removeUser(U user) {
		users.remove(user);
		Player player = user.getPlayer().getPlayer();
		if (player != null) {
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}

	@Override
	public void updateScoreboard(U user, Scoreboard scoreboard) {
		scoreboard.getObjectives().forEach(objective -> updateObjective(user, objective));
	}

	@Override
	public void updateObjective(U user, Objective objective) {
		List<String> lines = settings.getLines();
		int size = lines.size();
		String[] newScoresEntries = new String[size];
		for (int i = 0; i < size; i ++) {
			newScoresEntries[i] = parseLine(user, lines.get(i));
		}

		Score[] scores = objectivesScores.get(objective);
		if (scores == null) {
			scores = new Score[size];
			for (int i = 0; i < size; i ++) {
				scores[i] = ScoreboardManager.registerScore(objective, newScoresEntries[i], size - i - 1);
			}
			objectivesScores.put(objective, scores);
			return;
		}

		Scoreboard scoreboard = objective.getScoreboard();
		for (int i = 0; i < scores.length; i ++) {
			String entry = scores[i].getEntry();
			if (!entry.equals(newScoresEntries[i])) {
				scoreboard.resetScores(entry);
				scores[i] = ScoreboardManager.registerScore(objective, newScoresEntries[i], size - i - 1);
			}
		}
	}

	protected abstract String parseLine(U user, String line);
}
