package it.tristana.commons.helper;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.ArenasManager;

public abstract class PlayersManager {
	
	private ArenasManager<?> arenasManager;

	public PlayersManager(ArenasManager<?> arenasManager) {
		this.arenasManager = arenasManager;
	}

	public void resetPlayer(Player player) {
		resetPlayer(player, null);
	}

	public void resetPlayer(Player player, Location mainLobby) {
		if (mainLobby != null) {
			player.teleport(mainLobby);
		}
		fixHiddenPlayers(player);
		heal(player);
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.setExp(0);
		player.setLevel(0);
		player.setGameMode(GameMode.ADVENTURE);
	}

	public void fixHiddenPlayers(Player player) {
		Arena<?> arena = arenasManager.getArenaWithPlayer(player);
		if (arena == null) {
			Bukkit.getOnlinePlayers().forEach(other -> showPlayer(player, other));
			arenasManager.getArenas().forEach(currentArena -> currentArena.getPlayers().forEach(other -> showPlayer(player, other.getPlayer())));
		} else {
			Bukkit.getOnlinePlayers().forEach(other -> hidePlayer(player, other));
			arena.getPlayers().forEach(other -> showPlayer(player, other.getPlayer()));
		}
	}
	
	protected abstract void heal(Player player);

	protected abstract void hidePlayer(Player p1, Player p2);
	
	protected abstract void showPlayer(Player p1, Player p2);
}
