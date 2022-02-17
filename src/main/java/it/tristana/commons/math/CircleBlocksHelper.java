package it.tristana.commons.math;

import java.util.HashMap;
import java.util.Map;

public final class CircleBlocksHelper {

	private CircleBlocksHelper() {}

	private static final Map<Integer, int[][]> CACHE_MATRIX = new HashMap<Integer, int[][]>(20);

	public static int[][] getCircleMatrix(final int radius) {
		return getCircleMatrix(radius, false);
	}

	private static int[][] getCircleMatrix(int radius, final boolean force) {
		radius = radius <  0 ? -radius : radius;
		int[][] matrix = new int[radius * 2 + 1][radius * 2 + 1];
		if (radius == 0) {
			matrix[0][0] = 1;
		}
		else if (!force && CACHE_MATRIX.containsKey(radius)) {
			matrix = CACHE_MATRIX.get(radius);
		}
		else {
			int maxBlocksX;
			int maxBlocksY;
			if ((radius * 2) % 2 == 0) {
				maxBlocksX = (int)(Math.ceil(radius - 0.5) * 2) + 1;
			} else {
				maxBlocksX = (int)(radius * 2);
			}

			if ((radius * 2) % 2 == 0) {
				maxBlocksY = (int)(Math.ceil(radius - 0.5) * 2) + 1;
			} else {
				maxBlocksY = (int)(radius * 2);
			}
			for (int y = -maxBlocksY / 2 + 1; y <= maxBlocksY / 2 - 1; y ++) {
				for(int x = -maxBlocksX / 2 + 1; x <= maxBlocksX / 2 - 1; x ++) {
					matrix[x + radius - 1][y + radius - 1] = filled(x, y, radius) ? 1 : 0;
				}
			}
			CACHE_MATRIX.put(radius, matrix);
		}
		return matrix;
	}

	private static double distance(int x, int y) {
		return Math.sqrt((Math.pow(y , 2)) + Math.pow(x, 2));
	}

	private static boolean filled(int x, int y, int radius) {
		return distance(x, y) <= radius;
	}
}
