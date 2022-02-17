package it.tristana.commons.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class CircleEuclideanHelper {
	
	private static final double TWO_PI = Math.PI * 2;
	
	private double radius;
	private int particles;
	private double[] cachedSinuses;
	private double[] cachedCosines;
	
	public CircleEuclideanHelper(final double radius, final int particles) {
		this.radius = radius;
		this.particles = particles;
		cacheTrigonometry();
	}
	
	private void cacheTrigonometry() {
		final double angleStep = TWO_PI / particles;
		double currentAngle = 0;
		cachedSinuses = new double[particles];
		cachedCosines = new double[particles];
		for (int i = 0; i < particles; i ++) {
			cachedSinuses[i] = Math.sin(currentAngle);
			cachedCosines[i] = Math.cos(currentAngle);
			currentAngle += angleStep;
		}
	}
	
	public CachedCircleEuclidean getParticlesLocations(final Location center, final double rotation) {
		final Vector[] points = new Vector[particles];
		final double radians = Math.toRadians(rotation);
		final double sinX = Math.sin(radians);
		final double cosX = Math.cos(radians);
		for (int i = 0; i < particles; i ++) {
			double x = radius * cachedCosines[i] * cosX;
			double y = radius * cachedSinuses[i];
			double z = radius * cachedCosines[i] * sinX;
			points[i] = new Vector(x + center.getX(), y + center.getY(), z + center.getZ());
		}
		return new CachedCircleEuclidean(points, center, rotation);
	}
}