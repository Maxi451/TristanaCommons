package it.tristana.commons.math;

import org.bukkit.entity.Player;

public class TargetResult {
	
	private Player player;
	private double distance;
	
	public TargetResult(Player player, double distance) {
		this.player = player;
		this.distance = distance;
	}

	public Player getPlayer() {
		return player;
	}

	public double getDistance() {
		return distance;
	}
}
