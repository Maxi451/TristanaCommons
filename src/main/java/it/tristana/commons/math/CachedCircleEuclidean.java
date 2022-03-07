package it.tristana.commons.math;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class CachedCircleEuclidean {

	private static final int POINTS_MULTIPLIER = 8;
	
	private final Vector[] points;
	private final Location center;
	private final double rotation;
	private final double radius;
	
	private final Ray[] rays;

	public CachedCircleEuclidean(final Vector[] points, final Location center, final double rotation) {
		if (points.length % POINTS_MULTIPLIER != 0 || points.length == 0) {
			throw new IllegalArgumentException("Points length must be multiple of " + POINTS_MULTIPLIER + " and greater than zero");
		}
		this.points = points;
		this.center = center;
		this.rotation = rotation % 360;
		this.radius = center.toVector().distance(points[0]);
		rays = new Ray[points.length / (POINTS_MULTIPLIER * 2)];
		for (int i = 0; i < rays.length; i ++) {
			int pointIndex = i * POINTS_MULTIPLIER;
			rays[i] = new Ray(points[pointIndex], points[(pointIndex + points.length / 2) % points.length].clone().subtract(points[pointIndex]).normalize());
		}
	}

	public Ray[] getRays() {
		return rays;
	}
	
	public Vector[] getPoints() {
		return points;
	}

	public Location getCenter() {
		return center;
	}

	public double getRotation() {
		return rotation;
	}
	
	public boolean intersects(Player player, double hitboxOffset, boolean useElytraHitboxes) {
		for (int i = 0; i < rays.length; i ++) {
			if (RayTrace.canHit(player, hitboxOffset, useElytraHitboxes, rays[i], radius)) {
				return true;
			}
		}
		return false;
	}
}
