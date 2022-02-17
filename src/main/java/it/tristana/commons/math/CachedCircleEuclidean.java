package it.tristana.commons.math;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class CachedCircleEuclidean {

	private final Vector[] points;
	private final Location center;
	private final double rotation;
	private final double radius;

	public CachedCircleEuclidean(final Vector[] points, final Location center, final double rotation) {
		if (points.length % 4 != 0 || points.length == 0) {
			throw new IllegalArgumentException("Points length must be multiple of four and greater than zero");
		}
		this.points = points;
		this.center = center;
		this.rotation = rotation % 360;
		this.radius = center.toVector().distance(points[0]);
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
	
	public boolean intersects(double minX, double minZ, double maxX, double maxZ) {
		boolean result;
		try {
			result = intersection(new Point2D.Double(minX, minZ), new Point2D.Double(maxX, maxZ), new Point2D.Double(center.getX(), center.getZ()), radius).size() > 0;
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	private static List<Point2D> intersection(Point2D p1, Point2D p2, Point2D center, double radius) throws NoninvertibleTransformException {
		List<Point2D> result = new ArrayList<>();
		double dx = p2.getX() - p1.getX();
		double dy = p2.getY() - p1.getY();
		AffineTransform trans = AffineTransform.getRotateInstance(dx, dy);
		trans.invert();
		trans.translate(-center.getX(), -center.getY());
		Point2D p1a = trans.transform(p1, null);
		Point2D p2a = trans.transform(p2, null);
		double y = p1a.getY();
		double minX = Math.min(p1a.getX(), p2a.getX());
		double maxX = Math.max(p1a.getX(), p2a.getX());
		if (y == radius || y == -radius) {
			if (0 <= maxX && 0 >= minX) {
				p1a.setLocation(0, y);
				trans.inverseTransform(p1a, p1a);
				result.add(p1a);
			}
		} else if (y < radius && y > -radius) {
			double x = Math.sqrt(radius * radius - y * y);
			if (-x <= maxX && -x >= minX) {
				p1a.setLocation(-x, y);
				trans.inverseTransform(p1a, p1a);
				result.add(p1a);
			}
			if (x <= maxX && x >= minX) {
				p2a.setLocation(x, y);
				trans.inverseTransform(p2a, p2a);
				result.add(p2a);
			}
		}
		return result;
	}
}
