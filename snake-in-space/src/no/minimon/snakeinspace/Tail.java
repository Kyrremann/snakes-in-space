package no.minimon.snakeinspace;

import com.badlogic.gdx.math.Vector2;

public class Tail {

	public Vector2 direction;
	public Vector2 position;
	public boolean goingThroughWall;

	public Tail() {
		this.position = new Vector2();
		this.direction = new Vector2();
		this.goingThroughWall = false;
	}

	public Tail(Vector2 position, Vector2 direction) {
		this.position = position;
		this.direction = direction;
		this.goingThroughWall = false;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	@Override
	public String toString() {
		return String.format("(%f,%f) %f", position.x, position.y,
				direction);
	}
}