package no.minimon.snakeinspace;

import com.badlogic.gdx.math.Vector2;

public class Tail {

	public float angle;
	public Vector2 position;
	public boolean goingThroughWall;

	public Tail() {
		this.position = new Vector2(0f, 0f);
		this.angle = 0;
		this.goingThroughWall = false;
	}

	public Tail(Vector2 position, float angle) {
		this.position = position;
		this.angle = angle;
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
				angle);
	}
}