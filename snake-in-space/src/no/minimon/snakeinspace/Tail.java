package no.minimon.snakeinspace;

import com.badlogic.gdx.math.Vector2;

public class Tail {

	public Vector2 direction;
	public Vector2 position;
	public boolean isHead;

	public Tail() {
		this.position = new Vector2();
		this.direction = new Vector2();
	}

	public Tail(Vector2 position, Vector2 direction) {
		this.position = position;
		this.direction = direction;
	}

	@Override
	public String toString() {
		return String.format("(%f,%f) %f", position.x, position.y, direction);
	}
}