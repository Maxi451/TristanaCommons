package it.tristana.commons.math;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

// Just a ray class I made with useful methods I needed.

public class Ray {

	private static final int X = 0;
	private static final int Y = 1;
	private static final int Z = 2;
	
	private Vector origin;
	private Vector direction;

	// Create a ray at the origin pointing in a direction.
	public Ray(Vector origin, Vector direction) {
		this.origin = origin.clone();
		this.direction = direction.normalize().clone();
	}

	// Create a ray based on where the player is looking.
	// Origin: Player Eye Location
	// Direction: Player-looking direction
	public static Ray from(Player player) {
		return new Ray(player.getEyeLocation().toVector(), player.getLocation().getDirection());
	}

	public static Ray from2(Player player) {
		return new Ray(player.getLocation().toVector(), player.getLocation().getDirection());
	}

	// (Used for rotating vectors) Creates a vector in the horizontal plane (y=0) perpendicular to a vector.
	public static Vector right(Vector vector) {
		Vector n = vector.clone().setY(0).normalize();
		n.setX(n.getZ());
		n.setZ(-n.getX());
		return n;
	}

	// Returns a normalized version of this Ray with the Y component set to 0
	public Ray level() {
		return new Ray(origin, direction.setY(0).normalize());
	}

	public Vector getOrigin() {
		return origin.clone();
	}

	public Vector getDirection() {
		return direction.clone();
	}

	public double origin(int i) {
		switch (i) {
		case X:
			return origin.getX();
		case Y:
			return origin.getY();
		case Z:
			return origin.getZ();
		default:
			return 0;
		}
	}

	public double direction(int i) {
		switch (i) {
		case X:
			return direction.getX();
		case Y:
			return direction.getY();
		case Z:
			return direction.getZ();
		default:
			return 0;
		}
	}

	// Get a point x distance away from this ray.
	// Can be used to get a point 2 blocks in front of a player's face.
	public Vector getPoint(double distance) {
		return direction.clone().multiply(distance).add(origin);
	}

	// Same as above, but no need to construct object.
	public static Location getPoint(Player player, double distance) {
		Vector point = Ray.from(player).getPoint(distance);
		return new Location(player.getWorld(), point.getX(), point.getY(), point.getZ());
	}
}