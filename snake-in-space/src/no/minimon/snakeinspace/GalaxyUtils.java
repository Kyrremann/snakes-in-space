package no.minimon.snakeinspace;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class GalaxyUtils {

	public static boolean circlesIntersect(Vector2 pos1, Vector2 pos2) {
		return circlesIntersect(pos1, 10, pos2, 10);
	}

	public static boolean circlesIntersect(Vector2 pos1, float c1Radius,
			Vector2 pos2, float c2Radius) {
		return pos1.dst(pos2) < c1Radius + c2Radius;
	}

	public static boolean isIntersectionWith(Vector2 position,
			List<Snake> snakes) {
		for (Snake snake : snakes) {
			if (circlesIntersect(position, snake.getPosition())) {
				return true;
			}
		}
		return false;
	}
}
