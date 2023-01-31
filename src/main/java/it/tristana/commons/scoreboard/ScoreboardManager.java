package it.tristana.commons.scoreboard;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import it.tristana.commons.interfaces.Reloadable;
import it.tristana.commons.interfaces.Tickable;
import it.tristana.commons.interfaces.database.User;

public interface ScoreboardManager<U extends User> extends Tickable, Reloadable {

	void addUser(U user);

	void removeUser(U user);

	Scoreboard getScoreboard(U user);
	
	static Score registerScore(Objective objective, String name, int index) {
		Score score = objective.getScore(name);
		score.setScore(index);
		return score;
	}
}
