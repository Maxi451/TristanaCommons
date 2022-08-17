package it.tristana.commons.scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import it.tristana.commons.interfaces.database.User;

public abstract class BasicScoreboardManager<U extends User, S extends SettingsScoreboard<? extends ConfigScoreboard>> implements ScoreboardManager<U> {
	
	protected S settings;
	
	protected Map<U, Scoreboard> users;
	
	public BasicScoreboardManager(S settings) {
		this.settings = settings;
		users = new HashMap<>();
	}

	@Override
	public void runTick() {
		users.forEach(this::updateScoreboard);
	}
	
	@Override
	public void reload() {
		Collection<U> savedUsers = new HashSet<>(users.keySet());
		users.clear();
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
}
