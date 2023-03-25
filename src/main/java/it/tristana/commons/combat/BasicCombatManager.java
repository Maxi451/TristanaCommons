package it.tristana.commons.combat;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BasicCombatManager implements CombatManager {

	private Map<CtUser, LinkedList<CtUser>> combatTag;
	private SettingsCombat<?> settings;

	public BasicCombatManager(SettingsCombat<?> settings) {
		combatTag = new HashMap<>();
		this.settings = settings;
	}

	@Override
	public void runTick() {
		combatTag.keySet().removeIf(ctUser -> {
			boolean flag = !isInCt(ctUser);
			if (flag) {
				Bukkit.getPluginManager().callEvent(new ExitCombatEvent(ctUser.getPlayer()));
			}
			return flag;
		});
		updateHitters();
	}

	@Override
	public void onCt(Player attacker, Player target) {
		CtUser attackerCtUser = getCtUser(attacker);
		if (attackerCtUser == null) {
			attackerCtUser = createCtUser(attacker);
		} else {
			attackerCtUser.onHit();
		}

		CtUser targetCtUser = getCtUser(target);
		if (targetCtUser == null) {
			targetCtUser = createCtUser(target);
		} else {
			targetCtUser.onHit();
		}

		LinkedList<CtUser> list = combatTag.get(targetCtUser);
		if (!list.contains(attackerCtUser)) {
			list.add(attackerCtUser);
		}
	}

	@Override
	public void forEach(Consumer<Player> action) {
		combatTag.keySet().forEach(ctUser -> action.accept(ctUser.getPlayer()));
	}

	@Override
	public long getMillisToExitCt(Player player) {
		return getMillisToExitCt(getCtUser(player));
	}

	private long getMillisToExitCt(CtUser ctUser) {
		if (ctUser == null) {
			return 0;
		}
		return getCtMillis(ctUser);
	}

	@Override
	public boolean isInCt(Player player) {
		CtUser ctUser = getCtUser(player);
		if (ctUser == null) {
			return false;
		}
		return isInCt(ctUser);
	}

	private boolean isInCt(CtUser ctUser) {
		return getMillisToExitCt(ctUser) > 0;
	}

	@Override
	public Collection<Player> getAssistPlayers(Player player) {
		Collection<Player> result = new HashSet<>();
		CtUser ctUser = getCtUser(player);
		if (ctUser == null) {
			return result;
		}
		List<CtUser> list = combatTag.get(ctUser);
		if (list.size() == 0) {
			return result;
		}
		ListIterator<CtUser> assistingPlayers = list.listIterator(list.size() - 1);
		if (assistingPlayers.hasPrevious()) {
			Player previous = assistingPlayers.previous().getPlayer();
			result.add(previous);
		}
		while (assistingPlayers.hasPrevious()) {
			result.add(assistingPlayers.previous().getPlayer());
		}
		return result;
	}

	@Override
	public Player getLastAttacker(Player player) {
		CtUser ctUser = getCtUser(player);
		if (ctUser == null) {
			return null;
		}
		LinkedList<CtUser> attackers = combatTag.get(ctUser);
		if (attackers.size() == 0) {
			return null;
		}
		return attackers.getLast().getPlayer();
	}

	@Override
	public void remove(Player player) {
		CtUser tmp = new CtUser(player);
		combatTag.remove(tmp);
		combatTag.values().forEach(list -> list.remove(tmp));
	}

	private long getCtMillis(CtUser ctUser) {
		return getCtMillis(ctUser, settings.getCombatTagDuration());
	}

	private long getCtMillis(CtUser ctUser, long timeout) {
		return Math.max(ctUser.getLastHitMillis() + timeout - System.currentTimeMillis(), 0);
	}

	private void updateHitters() {
		combatTag.keySet().forEach(this::updateHitters);
	}

	private void updateHitters(CtUser ctUser) {
		LinkedList<CtUser> attackers = combatTag.get(ctUser);
		while (attackers.size() > 1) {
			if (isInCt(attackers.getFirst())) {
				break;
			}
			attackers.removeFirst();
		}
	}

	private CtUser createCtUser(Player player) {
		CtUser ctUser = new CtUser(player);
		combatTag.put(ctUser, new LinkedList<>());
		return ctUser;
	}

	private CtUser getCtUser(Player player) {
		for (CtUser ctPlayer : combatTag.keySet()) {
			if (ctPlayer.getPlayer() == player) {
				return ctPlayer;
			}
		}
		return null;
	}
}