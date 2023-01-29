package it.tristana.commons.math;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SphereHelper {

	private SphereHelper() {}

	private static List<Vector> makeSphere(double radiusX, double radiusY, double radiusZ, double dotsDistance, boolean filled) {
		List<Vector> pos = new ArrayList<Vector>();

		final double invRadiusX = 1 / radiusX;
		final double invRadiusY = 1 / radiusY;
		final double invRadiusZ = 1 / radiusZ;

		final double ceilRadiusX = Math.ceil(radiusX);
		final double ceilRadiusY = Math.ceil(radiusY);
		final double ceilRadiusZ = Math.ceil(radiusZ);

		double nextXn = 0;
		forX: for (double x = 0; x <= ceilRadiusX; x += dotsDistance) {
			final double xn = nextXn;
			nextXn = (x + dotsDistance) * invRadiusX;
			double nextYn = 0;
			forY: for (double y = 0; y <= ceilRadiusY; y += dotsDistance) {
				final double yn = nextYn;
				nextYn = (y + dotsDistance) * invRadiusY;
				double nextZn = 0;
				forZ: for (double z = 0; z <= ceilRadiusZ; z += dotsDistance) {
					final double zn = nextZn;
					nextZn = (z + dotsDistance) * invRadiusZ;
					double distanceSq = lengthSq(xn, yn, zn);
					if (distanceSq > 1) {
						if (z == 0) {
							if (y == 0) {
								break forX;
							}
							break forY;
						}
						break forZ;
					}

					if (!filled) {
						if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
							continue;
						}
					}
					pos.add(new Vector(x, y, z));
					pos.add(new Vector(-x, y, z));
					pos.add(new Vector(x, -y, z));
					pos.add(new Vector(x, y, -z));
					pos.add(new Vector(-x, -y, z));
					pos.add(new Vector(x, -y, -z));
					pos.add(new Vector(-x, y, -z));
					pos.add(new Vector(-x, -y, -z));
				}
			}
		}
		return pos;
	}

	private static List<Vector> makeSphere2(double radius, int samples, Vector offset) {
		List<Vector> points = new ArrayList<>();
		double phi = Math.PI * (3 - Math.sqrt(5));

		for (int i = 0; i < samples; i ++) {
			double y = 1 - (i / (double)(samples - 1)) * 2;
			double yRadius = Math.sqrt(1 - y * y);

			double theta = phi * i;

			double x = Math.cos(theta) * yRadius;
			double z = Math.sin(theta) * yRadius;

			points.add(new Vector(x, y, z).multiply(radius).add(offset));
		}
		return points;
	}

	private static double lengthSq(double x, double y, double z) {
		return (x * x) + (y * y) + (z * z);
	}

	public static CachedSphere getSphere(Location center, double radius, double dotsDistance) {
		return getSphere(center, radius, dotsDistance, true);
	}

	public static CachedSphere getSphere(Location center, double radius, double dotsDistance, boolean filled) {
		return new CachedSphere(getSphere(radius, dotsDistance, filled), center, radius);
	}

	public static List<Vector> getSphere(double radius, double dotsDistance) {
		return getSphere(radius, dotsDistance, true);
	}

	public static List<Vector> getSphere(double radius, double dotsDistance, boolean filled) {
		return makeSphere(radius, radius, radius, dotsDistance, filled);
	}

	public static CachedSphere getSphere2(Location center, double radius, int samples) {
		return new CachedSphere(makeSphere2(radius, samples, center.toVector()), center, radius);
	}
}
