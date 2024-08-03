package it.tristana.commons.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import it.tristana.commons.interfaces.Reloadable;
import it.tristana.commons.interfaces.Tickable;

public interface ScoreboardManager<U> extends Tickable, Reloadable {

	/**
	 * Adds a player to this scoreboard
	 * @param user The player to add
	 */
	void addUser(U user);

	/**
	 * Removes a player from this scoreboard
	 * @param user The player to remove
	 */
	void removeUser(U user);

	/**
	 * Gets the scoreboard the given player sees
	 * @param user The player to look up
	 * @return The {@link Scoreboard} this player sees
	 */
	Scoreboard getScoreboard(U user);

	/**
	 * Converts the "user" (whatever it is in this context)<br>
	 * to an actual Bukkit player
	 * @param user The user to convert
	 * @return A {@link Player} object
	 */
	Player toPlayer(U user);

	static Score registerScore(Objective objective, String name, int index) {
		Score score = objective.getScore(name);
		score.setScore(index);
		return score;
	}
}
