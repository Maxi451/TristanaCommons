package it.tristana.commons.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RayTrace {

	//origin = start position
	//direction = direction in which the raytrace will go
	private Vector origin;
	private Vector direction;

	public RayTrace(Vector origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
	}

	//get a point on the raytrace at X blocks away
	public Vector getPostion(double blocksAway) {
		return origin.clone().add(direction.clone().multiply(blocksAway));
	}

	//checks if a position is on contained within the position
	public boolean isOnLine(Vector position) {
		double t = (position.getX() - origin.getX()) / direction.getX();
		if (Double.compare(position.getBlockY(), origin.getY() + (t * direction.getY())) == 0 && Double.compare(position.getBlockZ(), origin.getZ() + (t * direction.getZ())) == 0) {
			return true;
		}
		return false;
	}

	public static boolean canHit(Player player, Player target, double max) {
		return canHit(player, target, max, 0);
	}

	public static boolean canHit(Player player, Player target, double max, double hitboxOffset) {
		return canHit(player, target, max, 0, false);
	}

	public static boolean canHit(Player player, Player target, double max, double hitboxOffset, boolean useElytraHitboxes) {
		AABB aabb = AABB.from(target, hitboxOffset, useElytraHitboxes);
		return canHit(aabb, Ray.from(player), max) || canHit(aabb, Ray.from2(player), max);
	}
	
	public static boolean canHit(Player player, double hitboxOffset, boolean useElytraHitboxes, Ray ray, double max) {
		return canHit(AABB.from(player, hitboxOffset, useElytraHitboxes), ray, max);
	}
	
	public static boolean canHit(AABB aabb, Ray ray, double max) {
		return aabb.collides(ray, 0, max);
	}

	//general intersection detection
	public static boolean intersects(Vector position, Vector min, Vector max) {
		if (position.getX() < min.getX() || position.getX() > max.getX()) {
			return false;
		} else if (position.getY() < min.getY() || position.getY() > max.getY()) {
			return false;
		} else if (position.getZ() < min.getZ() || position.getZ() > max.getZ()) {
			return false;
		}
		return true;
	}
	
	public static Location firstCollisionPoint(Location start, Location end, Set<Material> transparent, double precision) {
		return firstCollisionPoint(start.getWorld(), transparent, traverse(start.toVector(), end.toVector(), precision));
	}
	
	public static Location firstCollisionPoint(World world, Set<Material> transparent, List<Vector> steps) {
		int x, y, z, oldX, oldY, oldZ;
		oldX = oldY = oldZ = Integer.MIN_VALUE;
		for (Vector step : steps) {
			x = step.getBlockX();
			y = step.getBlockY();
			z = step.getBlockZ();
			if (x != oldX || y != oldY || z != oldZ) {
				Block block = world.getBlockAt(x, y, z);
				if (!transparent.contains(block.getType())) {
					return new Location(world, step.getX(), step.getY(), step.getZ());
				}
				oldX = x;
				oldY = y;
				oldZ = z;
			}
		}
		return null;
	}

	public List<Vector> traverse(double precision) {
		return traverse(origin, direction, precision);
	}
	
	public static List<Vector> traverse(Vector v1, Vector v2, double precision) {
		Vector step = v2.clone().subtract(v1).normalize().multiply(precision);
		int steps = (int) (v2.distance(v1) / precision);
		List<Vector> result = new LinkedList<Vector>();
		for (int i = 0; i < steps; i ++) {
			result.add(v1.add(step).clone());
		}
		return result;
	}
}