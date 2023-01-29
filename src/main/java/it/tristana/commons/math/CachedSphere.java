package it.tristana.commons.math;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CachedSphere {

	private List<Vector> points;
	private Location center;
	private double radius;

	public CachedSphere(List<Vector> points, Location center, double radius) {
		this.points = points;
		this.center = center;
		this.radius = radius;
	}

	public List<Vector> getPoints() {
		return points;
	}

	public Location getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	public double distance(Vector start, Vector end) {
		Vector center = this.center.toVector();
		Vector ab = end.clone().subtract(start);
		Vector av = center.clone().subtract(start);
		double distance;
		if (av.dot(ab) <= 0) {
			distance = modulus(av);
		}
		else if (center.subtract(end).dot(ab) >= 0) {
			distance = modulus(center);
		}
		else {
			distance = modulus(cross(ab, av)) / modulus(ab);
		}
		return distance;
	}

	private static Vector cross(final Vector first, final Vector second) {
		double firstX = first.getX();
		double firstY = first.getY();
		double firstZ = first.getZ();
		double secondX = second.getX();
		double secondY = second.getY();
		double secondZ = second.getZ();
		return new Vector(firstY * secondZ - firstZ * secondY, firstZ * secondX - firstX * secondZ, firstX * secondY - firstY * secondX);
	}

	private static double modulus(Vector vector) {
		return Math.sqrt(vector.dot(vector));
	}

	public Vector[] intersection(Vector start, Vector end) {
		// http://www.codeproject.com/Articles/19799/Simple-Ray-Tracing-in-C-Part-II-Triangles-Intersec
		double cx = center.getX();
		double cy = center.getY();
		double cz = center.getZ();

		double px = start.getX();
		double py = start.getY();
		double pz = start.getZ();

		double vx = end.getX() - px;
		double vy = end.getY() - py;
		double vz = end.getZ() - pz;

		double A = vx * vx + vy * vy + vz * vz;
		double B = 2 * (px * vx + py * vy + pz * vz - vx * cx - vy * cy - vz * cz);
		double C = px * px - 2 * px * cx + cx * cx + py * py - 2 * py * cy + cy * cy +
				pz * pz - 2 * pz * cz + cz * cz - radius * radius;
		// discriminant
		double discriminant = B * B - 4 * A * C;
		if (discriminant < 0) {
			return new Vector[0];
		}
		double t1 = (-B - Math.sqrt(discriminant)) / (2 * A);
		Vector solution1 = new Vector(start.getX() * (1 - t1) + t1 * end.getX(), start.getY() * (1 - t1) + t1 * end.getY(), start.getZ() * (1 - t1) + t1 * end.getZ());
		if (discriminant == 0) {
			return new Vector[] { solution1 };
		}
		double t2 = (-B + Math.sqrt(discriminant)) / (2 * A);
		Vector solution2 = new Vector(start.getX() * (1 - t2) + t2 * end.getX(), start.getY() * (1 - t2) + t2 * end.getY(), start.getZ() * (1 - t2) + t2 * end.getZ());
		// prefer a solution that's on the line segment itself
		if (Math.abs(t1 - 0.5) < Math.abs(t2 - 0.5)) {
			return new Vector[] { solution1, solution2 };
		}
		return new Vector[] { solution2, solution1 };
	}
}
