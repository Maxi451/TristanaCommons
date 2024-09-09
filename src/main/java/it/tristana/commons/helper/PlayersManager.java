package it.tristana.commons.helper;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import it.tristana.commons.interfaces.arena.Arena;
import it.tristana.commons.interfaces.arena.ArenasManager;

public abstract class PlayersManager {

	private Plugin plugin;
	private ArenasManager<?, ?> arenasManager;

	public PlayersManager(Plugin plugin, ArenasManager<?, ?> arenasManager) {
		this.plugin = plugin;
		this.arenasManager = arenasManager;
	}

	public void resetPlayer(Player player, boolean isQuitting) {
		resetPlayer(player, null, isQuitting);
	}

	public void resetPlayer(Player player, Location mainLobby, boolean isQuitting) {
		if (mainLobby != null) {
			player.teleport(mainLobby);
		}
		Bukkit.getScheduler().runTaskLater(plugin, () -> fixHiddenPlayers(player), 2);
		heal(player);
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.setExp(0);
		player.setLevel(0);
		player.setGameMode(GameMode.ADVENTURE);
	}

	public void fixHiddenPlayers(Player player) {
		if (!player.isOnline()) {
			return;
		}

		Arena<?> arena = arenasManager.getArenaWithPlayer(player);
		if (arena == null) {
			Bukkit.getOnlinePlayers().forEach(other -> {
				showPlayer(player, other);
				showPlayer(other, player);
			});
			arenasManager.getArenas().forEach(currentArena -> currentArena.getPlayers().forEach(other -> {
				Player otherPlayer = other.getPlayer();
				hidePlayer(player, otherPlayer);
				hidePlayer(otherPlayer, player);
			}));
		} else {
			Bukkit.getOnlinePlayers().forEach(other -> {
				hidePlayer(player, other);
				hidePlayer(other, player);
			});
			arenasManager.getArenas().forEach(currentArena -> currentArena.getPlayers().forEach(other -> {
				Player otherPlayer = other.getPlayer();
				showPlayer(player, otherPlayer);
				showPlayer(otherPlayer, player);
			}));
		}
	}

	protected abstract void heal(Player player);

	protected abstract void hidePlayer(Player p1, Player p2);

	protected abstract void showPlayer(Player p1, Player p2);
}
