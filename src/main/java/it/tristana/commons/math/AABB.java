package it.tristana.commons.math;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

// Just an AABB class I made with some useful methods I needed.

public class AABB {

	private static final double PLAYER_HEIGHT = 1.8;
	private static final double HALF_PLAYER_HEIGHT = PLAYER_HEIGHT / 2;
	private static final double EYES_HEIGHT = 1.62;
	private static final double HALF_HITBOX_WIDTH = 0.3;
	
	private Vector min, max; // min/max locations

	// Create Bounding Box from min/max locations.
	public AABB(Vector min, Vector max) {
		this(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
	}

	// Main constructor for AABB
	public AABB(double x1, double y1, double z1, double x2, double y2, double z2) {
		this.min = new Vector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
		this.max = new Vector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
	}

	private AABB(Player player, double hitboxOffset, boolean useElytraHitboxes) {
		this.min = getMin(player, hitboxOffset, useElytraHitboxes);
		this.max = getMax(player, hitboxOffset, useElytraHitboxes);
	}

	public Vector getMin() {
		return min;
	}
	
	private Vector getMin(Player player, double hitboxOffset, boolean useElytraHitboxes) {
		double offset = -HALF_HITBOX_WIDTH - hitboxOffset;
		Vector playerPos;
		double height;
		if (useElytraHitboxes) {
			playerPos = getElytraCenterPlayerPos(player);
			height = offset;
		} else {
			playerPos = player.getLocation().toVector();
			height = -hitboxOffset;
		}
		return playerPos.add(new Vector(offset, height, offset));
	}

	public Vector getMax() {
		return max;
	}

	private Vector getMax(Player player, double hitboxOffset, boolean useElytraHitboxes) {
		double offset = HALF_HITBOX_WIDTH + hitboxOffset;
		Vector playerPos;
		double height;
		if (useElytraHitboxes) {
			playerPos = getElytraCenterPlayerPos(player);
			height = offset;
		} else {
			playerPos = player.getLocation().toVector();
			height = PLAYER_HEIGHT + hitboxOffset;
		}
		return playerPos.add(new Vector(offset, height, offset));
	}

	private static Vector getElytraCenterPlayerPos(Player player) {
		Location pos = player.getLocation();
		return pos.toVector().add(pos.getDirection().multiply(HALF_PLAYER_HEIGHT));
	}

	public static Location getElytraPlayerEyesPos(Player player) {
		return player.getLocation().getDirection().multiply(EYES_HEIGHT).toLocation(player.getWorld());
	}
	
	// Create an AABB based on a player's hitbox
	public static AABB from(Player player) {
		return from(player, 0);
	}
	
	public static AABB from(Player player, double hitboxOffset) {
		return from(player, hitboxOffset, false);
	}
	
	public static AABB from(Player player, double hitboxOffset, boolean useElytraHitboxes) {
		return new AABB(player, hitboxOffset, useElytraHitboxes);
	}

	// Returns minimum x, y, or z point from inputs 0, 1, or 2.
	public double min(int i) {
		switch (i) {
		case 0:
			return min.getX();
		case 1:
			return min.getY();
		case 2:
			return min.getZ();
		default:
			return 0;
		}
	}

	// Returns maximum x, y, or z point from inputs 0, 1, or 2.
	public double max(int i) {
		switch (i) {
		case 0:
			return max.getX();
		case 1:
			return max.getY();
		case 2:
			return max.getZ();
		default:
			return 0;
		}
	}

	// Check if a Ray passes through this box. tmin and tmax are the bounds.
	// Example: If you wanted to see if the Ray collides anywhere from its
	// origin to 5 units away, the values would be 0 and 5.
	public boolean collides(Ray ray, double tmin, double tmax) {
		for (int i = 0; i < 3; i++) {
			double d = 1 / ray.direction(i);
			double t0 = (min(i) - ray.origin(i)) * d;
			double t1 = (max(i) - ray.origin(i)) * d;
			if (d < 0) {
				double t = t0;
				t0 = t1;
				t1 = t;
			}
			tmin = t0 > tmin ? t0 : tmin;
			tmax = t1 < tmax ? t1 : tmax;
			if (tmax <= tmin) return false;
		}
		return true;
	}

	// Same as other collides method, but returns the distance of the nearest
	// point of collision of the ray and box, or -1 if no collision.
	public double collidesD(Ray ray, double tmin, double tmax) {
		for (int i = 0; i < 3; i++) {
			double d = 1 / ray.direction(i);
			double t0 = (min(i) - ray.origin(i)) * d;
			double t1 = (max(i) - ray.origin(i)) * d;
			if (d < 0) {
				double t = t0;
				t0 = t1;
				t1 = t;
			}
			tmin = t0 > tmin ? t0 : tmin;
			tmax = t1 < tmax ? t1 : tmax;
			if (tmax <= tmin) return -1;
		}
		return tmin;
	}

	// Check if the location is in this box.
	public boolean contains(Location location) {
		if (location.getX() > max.getX()) return false;
		if (location.getY() > max.getY()) return false;
		if (location.getZ() > max.getZ()) return false;
		if (location.getX() < min.getX()) return false;
		if (location.getY() < min.getY()) return false;
		if (location.getZ() < min.getZ()) return false;
		return true;
	}
}