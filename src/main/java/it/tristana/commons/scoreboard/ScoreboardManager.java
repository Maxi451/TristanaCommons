package it.tristana.commons.scoreboard;

import java.util.Collection;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import it.tristana.commons.interfaces.Reloadable;
import it.tristana.commons.interfaces.Tickable;
import it.tristana.commons.interfaces.database.User;

public interface ScoreboardManager<U extends User> extends Tickable, Reloadable {
	
	void addUser(U user);
	
	void removeUser(U user);
	
	Collection<Objective> createObjectives(U user, Scoreboard scoreboard);
	
	void updateScoreboard(U user, Scoreboard scoreboard);
	
	void updateObjective(U user, Objective objective);
}
