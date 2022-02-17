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
	private Vector origin, direction;

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
		AABB aabb = AABB.from(target);
		return aabb.collides(Ray.from(player), 0, max) || aabb.collides(Ray.from2(player), 0, max);
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

	public List<Vector> traverse(double precision) {
		return traverse(origin, direction, precision);
	}
	
	public static Location firstCollisionPoint(Location start, Location end, Set<Material> transparent, double precision) {
		return firstCollisionPoint(start.getWorld(), transparent, traverse(start.toVector(), end.toVector(), precision), precision);
	}
	
	public static Location firstCollisionPoint(World world, Set<Material> transparent, List<Vector> steps, double precision) {
		Location collisionPoint = null;
		int x, y, z, oldX, oldY, oldZ;
		oldX = oldY = oldZ = Integer.MIN_VALUE;
		for (Vector step : steps) {
			x = step.getBlockX();
			y = step.getBlockY();
			z = step.getBlockZ();
			if (x != oldX || y != oldY || z != oldZ) {
				Block block = world.getBlockAt(x, y, z);
				if (!transparent.contains(block.getType())) {
					collisionPoint = new Location(world, step.getX(), step.getY(), step.getZ());
					break;
				}
				oldX = x;
				oldY = y;
				oldZ = z;
			}
		}
		return collisionPoint;
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