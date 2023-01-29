package it.tristana.commons.scoreboard;

import java.util.Collection;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import it.tristana.commons.interfaces.database.User;

public interface PersonalScoreboardManager<U extends User> extends ScoreboardManager<U> {

	Collection<Objective> createObjectives(U user, Scoreboard scoreboard);

	void updateScoreboard(U user, Scoreboard scoreboard);

	void updateObjective(U user, Objective objective);
}
