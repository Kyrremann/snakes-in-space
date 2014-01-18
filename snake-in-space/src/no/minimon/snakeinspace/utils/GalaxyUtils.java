package no.minimon.snakeinspace.utils;

import java.util.List;
import java.util.Random;

import no.minimon.snakeinspace.Movable;

import com.badlogic.gdx.math.Vector2;

public class GalaxyUtils {

	private static Random random = new Random();

	public static float getRandomFloat(int min, int max) {
		return Math.abs((random.nextFloat() * (min - max)) + min);
	}

	public static int getRandomInt(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}

	public static boolean circlesIntersect(Vector2 pos1, Vector2 pos2) {
		return circlesIntersect(pos1, 10, pos2, 10);
	}

	public static boolean circlesIntersect(Vector2 pos1, float c1Radius,
			Vector2 pos2, float c2Radius) {
		if (pos1 == null || pos2 == null)
			return false;
		return pos1.dst(pos2) < c1Radius + c2Radius;
	}

	public static boolean isIntersectionWith(Vector2 position, int clearRadius,
			List<? extends Movable> movables) {
		for (Movable movable : movables) {
			if (circlesIntersect(position, clearRadius, movable.getPosition(),
					movable.getRadius())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isIntersectionWith(Vector2 position,
			List<? extends Movable> movables) {
		return isIntersectionWith(position, 10, movables);
	}
}
