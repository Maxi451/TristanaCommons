package it.tristana.commons.combat;

import org.bukkit.entity.Player;

class CtUser {

	private final Player player;
	private long lastHitMillis;
	
	CtUser(Player player) {
		this.player = player;
		onHit();
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof CtUser && ((CtUser) other).player == player;
	}
	
	@Override
	public int hashCode() {
		return player.hashCode();
	}
	
	public long getLastHitMillis() {
		return lastHitMillis;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	void onHit() {
		lastHitMillis = System.currentTimeMillis();
	}
}
