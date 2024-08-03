package it.tristana.commons.scoreboard;

import java.util.Collection;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public interface PersonalScoreboardManager<U> extends ScoreboardManager<U> {

	Collection<Objective> createObjectives(U user, Scoreboard scoreboard);

	void updateScoreboard(U user, Scoreboard scoreboard);

	void updateObjective(U user, Objective objective);
}
